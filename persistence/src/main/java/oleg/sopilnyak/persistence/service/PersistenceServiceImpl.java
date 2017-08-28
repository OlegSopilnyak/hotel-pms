package oleg.sopilnyak.persistence.service;

import lombok.extern.slf4j.Slf4j;
import oleg.sopilnyak.common.model.business.*;
import oleg.sopilnyak.common.model.transport.*;
import oleg.sopilnyak.common.service.PersistenceService;
import oleg.sopilnyak.persistence.model.*;
import oleg.sopilnyak.persistence.respository.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Persistence Model facade for manage entities
 */
@Slf4j
public class PersistenceServiceImpl implements PersistenceService {

    @Autowired
    @Qualifier("persistenceMapper")
    private Mapper mapper;

    @Resource
    private HotelAgreementRepository hotelAgreementRepository;
    @Resource
    private GuestRepository guestRepository;
    @Resource
    private EmployeeRepository employeeRepository;
    @Resource
    private RoomActivityRepository roomActivityRepository;
    @Resource
    private RoomFeatureRepository roomFeatureRepository;
    @Resource
    private RoomRepository roomRepository;
    @Resource
    private HotelRepository hotelRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(HotelAgreement agreement) {
        HotelAgreementEntity entity = mapper.map(agreement, HotelAgreementEntity.class);
        hotelAgreementRepository.save(entity);
    }

    @Override
    public HotelAgreement getById(String id) {
        HotelAgreementEntity entity = hotelAgreementRepository.getById(id);
        return entity == null ? null : mapper.map(entity, HotelAgreementDto.class);
    }

    @Override
    public Guest getGuest(Person.ID document) {
        PersonIdEntity doc = mapper.map(document, PersonIdEntity.class);
        GuestEntity guest = guestRepository.getByDocument(doc);
        return guest == null ? null : mapper.map(guest, GuestDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(Guest guest) {
        GuestEntity guestEntity = mapper.map(guest, GuestEntity.class);
        guestRepository.save(guestEntity);
    }

    @Override
    public Set<HotelAgreement> searchHotelAgreements(LocalDate from, LocalDate to) {
        List<HotelAgreementEntity> suits = hotelAgreementRepository.findByCreatedAtBetween(from.atStartOfDay(),to.atStartOfDay());
        return suits.stream().map(e -> mapper.map(e, HotelAgreementDto.class)).collect(Collectors.toSet());
    }

    @Override
    public Employee getActiveBooker() {
        String tag1 = "%reception%";
        String tag2 = "%booking%";
        List<EmployeeEntity> workers = employeeRepository.findByTagsLikeAndActive(tag1, true);
        if (workers.isEmpty()){
            workers = employeeRepository.findByTagsLikeAndActive(tag2, true);
        }
        return workers.isEmpty() ? null : mapper.map(workers.get(0), EmployeeDto.class);
    }

    @Override
    public Set<Room> findFreeRooms(LocalDate from, LocalDate to) {
        List<RoomActivityEntity> schedule = roomActivityRepository.findByStartedAtBetween(from.atStartOfDay(),to.atStartOfDay());
        List<String> roomIds = schedule.stream()
                .filter(a-> a.getType() == Room.Activity.Type.BOOKED)
                .map(a->a.getRoomId())
                .collect(Collectors.toList());
        List<RoomEntity> rooms = roomIds.isEmpty() ? roomRepository.findAll():roomRepository.findByIdNotIn(roomIds);
        return rooms.stream().map(r-> mapper.map(r, RoomDto.class)).collect(Collectors.toSet());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void persist(Room.Activity activity) {
        RoomActivityEntity entity = mapper.map(activity, RoomActivityEntity.class);
        roomActivityRepository.save(entity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRoomActions(HotelAgreement agreement) {
        roomActivityRepository.deleteByHotelAgreementId(agreement.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(HotelAgreement agreement) {
        HotelAgreementEntity entity = hotelAgreementRepository.getById(agreement.getId());
        if (entity != null){
            hotelAgreementRepository.delete(entity);
        }
    }

    @Override
    public Set<Room.Feature> allRoomFeatures() {
        return roomFeatureRepository.findAll().stream()
                .map(f -> mapper.map(f, RoomFeatureDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Guest getGuest(String id) {
        GuestEntity entity = guestRepository.getById(id);
        return entity == null ? null : mapper.map(entity, GuestDto.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void freeDedicatedRooms(LocalDateTime mark) {
        roomActivityRepository.deleteByStartedAtBeforeAndType(mark, Room.Activity.Type.DEDICATE);
    }

    @Override
    public Set<HotelAgreement> findReserved(LocalDate from, LocalDate till) {
        List<RoomActivityEntity> schedule = roomActivityRepository.findByStartedAtBetween(from.atStartOfDay(),till.atStartOfDay());
        List<String> agreementIds = schedule.stream()
                .filter(a-> a.getType() == Room.Activity.Type.BOOKED)
                .map(a->a.getHotelAgreementId())
                .collect(Collectors.toList());
        List<HotelAgreementEntity> suits = hotelAgreementRepository.findByIdIn(agreementIds);
        return suits.stream().map(a -> mapper.map(a, HotelAgreementDto.class)).collect(Collectors.toSet());
    }

    @Override
    public Set<Room> findAllRooms() {
        return roomRepository.findAll().stream().map(e->mapper.map(e, RoomDto.class)).collect(Collectors.toSet());
    }

    @Override
    public Set<Hotel> allHotels() {
        return hotelRepository.findAll().stream().map(e->mapper.map(e, HotelDto.class)).collect(Collectors.toSet());
    }

    @Override
    public Room.Activity getActivityById(String id) {
        RoomActivityEntity activity = roomActivityRepository.getById(id);
        return activity == null ? null : mapper.map(activity, RoomActivityDto.class);
    }
}
