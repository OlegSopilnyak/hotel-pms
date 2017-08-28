package oleg.sopilnyak.common.service.impl;

import lombok.extern.slf4j.Slf4j;
import oleg.sopilnyak.common.model.business.*;
import oleg.sopilnyak.common.model.transport.*;
import oleg.sopilnyak.common.service.IdGeneratorService;
import oleg.sopilnyak.common.service.PersistenceService;
import oleg.sopilnyak.common.service.ReservationService;
import oleg.sopilnyak.common.service.TimeService;
import oleg.sopilnyak.common.service.exception.CannotChangeReservationException;
import oleg.sopilnyak.common.service.exception.CannotReserveException;
import oleg.sopilnyak.common.service.exception.ResourceNotFoundException;
import org.dozer.Mapper;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toMap;

/**
 * Realization of reservation service
 *
 * @see oleg.sopilnyak.common.service.ReservationService
 */
@Slf4j
public class ReservationServiceImpl implements ReservationService {

    private volatile boolean active = false;
    private ScheduledFuture future;
    @Value("${config.core.scan.reservation:5}")
    private Long scanDelay;
    @Value("${config.core.dedicated.room.reservation.expired:30}")
    private Long expiredAfterMinutes;


    @Autowired
    private PersistenceService persistence;
    @Autowired
    private IdGeneratorService idGenerator;
    @Autowired
    private TimeService timer;
    @Autowired
    @Qualifier("coreServiceMapper")
    private Mapper mapper;
    @Autowired
    @Qualifier("coreServiceRunner")
    private ScheduledExecutorService runner;

    @Autowired
    private ObjectFactory<GuestDto> guestsFactory;
    @Autowired
    private ObjectFactory<HotelAgreementDto> agreementsFactory;
    @Autowired
    private ObjectFactory<HabitationOccupiedDto> occupiedFactory;
    @Autowired
    private ObjectFactory<PreBookingResultDto> resultFactory;
    @Autowired
    private ObjectFactory<RoomActivityDto> activityFactory;

    public void init() throws Exception {
        if (active) return;
        active = true;
        future = runner.scheduleWithFixedDelay(()->checkDedicatedRooms(), 10, scanDelay, TimeUnit.MINUTES);
        log.info("Run reservation service");
    }

    public void destroy(){
        if (!active) return;
        active = false;
        future.cancel(true);
        future = null;
        log.info("Stop reservation service");
    }

    public boolean isActive(){return active;}

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PreBookingResult book(LocalDate from, LocalDate to, Set<Guest> guests, int rooms, Set<Room.Feature> features) {
        if (!isActive()){
            log.error("Reservation Service is not works.");
            throw new IllegalStateException("Reservation Service is not works.");
        }
        log.debug("Prepare reservation for guests {} in {} rooms, form {} to {}", guests, rooms, from, to);
        register(guests);
        checkHotelAgreement(guests, from, to, rooms);
        final Set<Room> freeRooms = getAvailableRooms(from, to, rooms, features);
        if (freeRooms.size() < rooms) {
            log.error("Lack of free rooms. Need : {} / Available : {}", rooms, freeRooms.size());
            throw new CannotReserveException("Not enough free rooms to book");
        }
        final HotelAgreement agreement = buildAgreement(guests, from, to, rooms);
        dedicateFreeRooms(freeRooms, agreement);

        final PreBookingResultDto result = resultFactory.getObject();
        result.setHotelAgreementId(agreement.getId());
        result.getGuests().addAll(guests);
        result.getDedicatedRoom().addAll(freeRooms);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelBooking(String id) {
        if (!isActive()){
            log.error("Reservation Service is not works.");
            throw new IllegalStateException("Reservation Service is not works.");
        }
        log.debug("Canceling reservation id:{}", id);
        HotelAgreement agreement = persistence.getById(id);
        if (agreement == null) {
            log.error("Not found hotel agreement with id:{}", id);
            throw new ResourceNotFoundException("Not found hotel agreement with id:{}" + id);
        }
        if (agreement.getReservation().getState() != Reservation.State.CONFIRMED) {
            log.error("Hotel agreement is in inappropriate state : {}", agreement.getReservation().getState());
            throw new IllegalStateException("Wrong state of agreement:" + id + " for changes");
        }
        removeHotelAgreement(agreement);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public PreBookingResult change(String id, LocalDate from, LocalDate to, Set<Guest> guests, int rooms) {
        if (!isActive()){
            log.error("Reservation Service is not works.");
            throw new IllegalStateException("Reservation Service is not works.");
        }
        log.debug("Updating reservation id:{} for guests {} in {} rooms, form {} to {}", id, guests, rooms, from, to);
        HotelAgreement agreement = persistence.getById(id);
        if (agreement == null) {
            log.error("Not found hotel agreement with id:{}", id);
            throw new ResourceNotFoundException("Not found hotel agreement with id:{}" + id);
        }
        if (agreement.getReservation().getState() != Reservation.State.CONFIRMED) {
            log.error("Hotel agreement is in inappropriate state : {}", agreement.getReservation().getState());
            throw new IllegalStateException("Wrong state of agreement:" + id + " for changes");
        }
        // calculate features from exists agreement
        final Set<Room.Feature> features = new LinkedHashSet<>(persistence.allRoomFeatures());
        agreement.getHabitation().getOccupied().forEach(o -> features.retainAll(o.getActiveFeatures()));

        try {
            register(guests);
        }catch (CannotReserveException e){
            throw new CannotChangeReservationException("Wrong guest.",e, id);
        }
        removeHotelAgreement(agreement);

        final Set<Room> freeRooms = getAvailableRooms(from, to, rooms, features);
        if (freeRooms.size() < rooms) {
            log.error("Lack of free rooms. Need : {} / Available : {}", rooms, freeRooms.size());
            throw new CannotChangeReservationException("Not enough free rooms to book");
        }

        agreement = buildAgreement(guests, from, to, rooms);
        dedicateFreeRooms(freeRooms, agreement);

        final PreBookingResultDto result = resultFactory.getObject();
        result.setHotelAgreementId(agreement.getId());
        result.getGuests().addAll(guests);
        result.getDedicatedRoom().addAll(freeRooms);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void confirmReservation(String id, Set<ConfirmReservation.BookedRoom> rooms, CreditCard card) {
        if (!isActive()){
            log.error("Reservation Service is not works.");
            throw new IllegalStateException("Reservation Service is not works.");
        }
        log.debug("Confirming reservation id:{}", id);
        HotelAgreement agreement = persistence.getById(id);
        if (agreement == null) {
            log.error("Not found hotel agreement with id:{}", id);
            throw new ResourceNotFoundException("Not found hotel agreement with id:{}" + id);
        }
        if (agreement.getReservation().getState() != Reservation.State.NEW) {
            log.error("Hotel agreement is in inappropriate state : {}", agreement.getReservation().getState());
            throw new IllegalStateException("Wrong state of agreement:" + id + " for confirmation");
        }
        ((HotelAgreementDto) agreement).setCard((CreditCardDto) card);

        final Map<String, GuestDto> guests = agreement.getGuestSet().stream()
                .map(g -> (GuestDto) g)
                .collect(toMap(g -> g.getId(), g -> g));
        final Map<String, HabitationOccupiedDto> oRooms = agreement.getHabitation().getOccupied()
                .stream().map(o -> (HabitationOccupiedDto) o)
                .collect(toMap(o -> o.getRoom().getId(), o -> o));

        for (ConfirmReservation.BookedRoom b : rooms) {
            final String roomCode = b.getRoomCode();
            final HabitationOccupiedDto occupied = oRooms.get(roomCode);
            if (occupied == null) {
                log.error("Not dedicated room with id:{}", roomCode);
                throw new ResourceNotFoundException("Not dedicated room with id:" + roomCode);
            }
            final Map<String, RoomFeatureDto> features = occupied.getRoom().getAvailableFeatures()
                    .stream().map(fe -> (RoomFeatureDto) fe).collect(toMap(f -> f.getCode(), f -> f));
            occupied.setGuests(b.getGuestIds().stream()
                    .map(gid -> guests.get(gid)).filter(g -> g != null)
                    .collect(Collectors.toSet())
            );
            occupied.setActiveFeatures(b.getFeatureIds().stream()
                    .map(fid -> features.get(fid)).filter(f -> f != null)
                    .collect(Collectors.toSet())
            );
        }
        ((HotelAgreementDto) agreement).getReservation().setState(Reservation.State.CONFIRMED);
        persistence.persist(agreement);
    }

    // private methods
    void removeHotelAgreement(HotelAgreement agreement) {
        persistence.deleteRoomActions(agreement);
        persistence.delete(agreement);
    }

    void dedicateFreeRooms(Set<Room> freeRooms, HotelAgreement agreement) {
        final Set<HabitationOccupiedDto> roomSet = new LinkedHashSet<>();
        freeRooms.forEach(r -> {
            final RoomActivityDto activity = activityFactory.getObject();
            //Make room dedicated activity
            activity.setId(idGenerator.generate());
            activity.setCreatedAt(timer.now());
            activity.setType(Room.Activity.Type.DEDICATE);
            activity.setHotelAgreementId(agreement.getId());
            activity.setRoomId(r.getId());
            persistence.persist(activity);
            // adjust occupied room
            final HabitationOccupiedDto occupied = occupiedFactory.getObject();
            occupied.setRoom((RoomDto) r);
            occupied.setActiveFeatures(r.getAvailableFeatures());
            // no associated guests
            occupied.setGuests(new HashSet());
            roomSet.add(occupied);
        });
        persistence.persist(agreement);
    }

    Set<Room> getAvailableRooms(LocalDate from, LocalDate to, int rooms, Set<Room.Feature> features) {
        final Set<Room> freeRooms = persistence.findFreeRooms(from, to);
        return freeRooms.stream()
                .filter(r -> r.getAvailableFeatures().containsAll(features))
                .limit(rooms)
                .collect(Collectors.toSet());
    }

    HotelAgreement buildAgreement(Set<Guest> guests, LocalDate from, LocalDate to, int rooms) {
        final HotelAgreementDto agreement = agreementsFactory.getObject();
        // assign guests
        agreement.setId(idGenerator.generate());
        agreement.getGuestSet().addAll(guests);

        // prepare reservation
        final ReservationDto reservation = agreement.getReservation();
        reservation.setId(agreement.getId());
        reservation.setFrom(from);
        reservation.setTo(to);
        reservation.setState(Reservation.State.NEW);
        reservation.setReservedBy((EmployeeDto) persistence.getActiveBooker());

        // prepare rooms set
        final HabitationDto habitation = agreement.getHabitation();
        habitation.setId(agreement.getId());
        final Set<HabitationOccupiedDto> roomSet = new LinkedHashSet<>();
        IntStream.range(1, rooms).forEach(i -> roomSet.add(occupiedFactory.getObject()));
        habitation.setOccupied(roomSet);

        // store changes
        persistence.persist(agreement);
        return agreement;
    }

    void checkHotelAgreement(Set<Guest> guests, LocalDate from, LocalDate to, int rooms) {
        final Set<HotelAgreement> agreements = persistence.searchHotelAgreements(from, to);
        final Optional<HotelAgreement> wrapper = agreements.stream()
                .filter(a -> a.getReservation().getState() == Reservation.State.CONFIRMED)
                .filter(a -> a.getHabitation().getOccupied().size() == rooms)
                .filter(a -> a.getGuestSet().equals(guests))
                .findFirst();
        wrapper.ifPresent(hotelAgreement -> {
            log.error("Reservation already exists");
            throw new CannotReserveException("It seems there is reservation id:" + hotelAgreement.getId());
        });
    }

    void register(Set<Guest> guests) {
        if (guests == null || guests.isEmpty()) {
            log.error("Empty guests set");
            throw new CannotReserveException("Empty guest set");
        }
        guests.forEach(g -> {
            final Guest db = persistence.getGuest(g.getDocument());
            if (Objects.isNull(db)) {
                final GuestDto dto = guestsFactory.getObject();
                dto.setId(idGenerator.generate());
                mapper.map(g, dto);
                persistence.persist(dto);
            } else if (!db.equals(g)) {
                log.error("Invalid data about guest {}", g);
                throw new CannotReserveException("Invalid data about guest " + g);
            }
        });
    }

    void checkDedicatedRooms() {
        try {
            persistence.freeDedicatedRooms(timer.now().minusMinutes(expiredAfterMinutes));
        }catch (Throwable t){
            log.error("Something went wrong during dedicated rooms scanning.", t);
        }
    }

}
