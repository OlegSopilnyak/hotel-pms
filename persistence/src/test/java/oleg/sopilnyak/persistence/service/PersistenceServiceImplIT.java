package oleg.sopilnyak.persistence.service;

import oleg.sopilnyak.common.model.business.*;
import oleg.sopilnyak.common.model.configuration.ModelConfiguration;
import oleg.sopilnyak.common.model.transport.*;
import oleg.sopilnyak.common.service.IdGeneratorService;
import oleg.sopilnyak.common.service.PersistenceService;
import oleg.sopilnyak.common.service.TimeService;
import oleg.sopilnyak.common.service.configuration.ServiceConfiguration;
import oleg.sopilnyak.persistence.configuration.PersistenceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Integration Test for entities
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        PersistenceConfiguration.class,
        ModelConfiguration.class, ServiceConfiguration.class
})
@Configuration
@Rollback()
public class PersistenceServiceImplIT {

    @Autowired
    private PersistenceService service;
    @Autowired
    private IdGeneratorService generator;
    @Autowired
    private TimeService timer;

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void persist() throws Exception {
        HotelAgreementDto agreementDto = new HotelAgreementDto();
        agreementDto.setId(generator.generate());
        agreementDto.setCreatedAt(timer.now().minusDays(2));
        agreementDto.setCard(CreditCardDto.builder()
                .expiredFrom(timer.today().plusYears(2)).id(generator.generate()).number("4111-1111-1111-1111")
                .owner("John Dow").build());
        GuestDto guest = makeTestGuest();

        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));

        ReservationDto reservation = new ReservationDto();
        reservation.setId(agreementDto.getId());
        reservation.setState(Reservation.State.NEW);
        EmployeeDto receptionist = makeTestEmployee();
        reservation.setReservedBy(receptionist);
        reservation.setFrom(timer.today().plusDays(1));
        reservation.setTo(timer.today().plusDays(2));
        agreementDto.setReservation(reservation);

        HabitationDto habitation = new HabitationDto();
        habitation.setId(agreementDto.getId());
        habitation.setCheckedInBy(receptionist);
        habitation.setCheckOutBy(receptionist);
        habitation.setCheckOut(timer.now().minusHours(1));
        habitation.setCheckIn(timer.now().plusDays(2));
        habitation.setHotelId("HO-1");
        habitation.setTotalFee(BigDecimal.valueOf(300));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setDailyCost(BigDecimal.valueOf(100));
        room.setWindows(1);
        room.setType(Room.Type.STANDARD);
        room.setFloor(2);
        room.setCapacity(1);
        room.setId("HO-1-202");
        RoomFeatureDto feature = new RoomFeatureDto();
        feature.setTotalCost(BigDecimal.ZERO);
        feature.setDailyCost(BigDecimal.valueOf(5));
        feature.setCode("R-TV");
        feature.setName("TV");
        feature.setDescription("In room TV set.");
        room.setAvailableFeatures(Stream.of(feature).collect(Collectors.toSet()));
        occupied.setRoom(room);
        occupied.setActiveFeatures(Stream.of(feature).collect(Collectors.toSet()));
        habitation.setOccupied(Stream.of(occupied).collect(Collectors.toSet()));
        ServiceProvidedDto provided = new ServiceProvidedDto();
        provided.setCost(BigDecimal.TEN);
        provided.setDescription("Order table in restaurant");
        provided.setDuration(Duration.ofMinutes(40));
        provided.setFinishTime(timer.now().minusHours(3));
        provided.setStartTime(timer.now().minusHours(2));
        provided.setState(ServiceProvided.State.DONE);
        provided.setInChargeOf(receptionist);
        habitation.setServices(Stream.of(provided).collect(Collectors.toSet()));

        agreementDto.setHabitation(habitation);

        service.persist(agreementDto);

        HotelAgreement agreement = service.getById(agreementDto.getId());
        assertNotNull(agreement);
        assertEquals(agreement, agreementDto);
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void getById() throws Exception {
        HotelAgreement agreement = service.getById("AGR-01-01/12");
        assertNotNull(agreement);
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void persistAndGetGuest() throws Exception {
        GuestDto guest = makeTestGuest();
        service.persist(guest);
        Guest saved = service.getGuest(guest.getDocument());
        assertNotNull(saved);
        assertEquals(guest, saved);
        assertEquals(saved, service.getGuest(guest.getId()));
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void searchHotelAgreements() throws Exception {
        HotelAgreementDto agreementDto = new HotelAgreementDto();
        agreementDto.setId(generator.generate());
        agreementDto.setCreatedAt(timer.now().minusDays(2));
        agreementDto.setCard(CreditCardDto.builder()
                .expiredFrom(timer.today().plusYears(2)).id(generator.generate()).number("4111-1111-1111-1111")
                .owner("John Dow").build());
        GuestDto guest = makeTestGuest();

        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));

        ReservationDto reservation = new ReservationDto();
        reservation.setId(agreementDto.getId());
        reservation.setState(Reservation.State.NEW);
        EmployeeDto receptionist = makeTestEmployee();
        reservation.setReservedBy(receptionist);
        reservation.setFrom(timer.today().plusDays(1));
        reservation.setTo(timer.today().plusDays(2));
        agreementDto.setReservation(reservation);

        HabitationDto habitation = new HabitationDto();
        habitation.setId(agreementDto.getId());
        habitation.setCheckedInBy(receptionist);
        habitation.setCheckOutBy(receptionist);
        habitation.setCheckOut(timer.now().minusHours(1));
        habitation.setCheckIn(timer.now().plusDays(2));
        habitation.setHotelId("HO-1");
        habitation.setTotalFee(BigDecimal.valueOf(300));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setDailyCost(BigDecimal.valueOf(100));
        room.setWindows(1);
        room.setType(Room.Type.STANDARD);
        room.setFloor(2);
        room.setCapacity(1);
        room.setId("HO-1-202");
        RoomFeatureDto feature = new RoomFeatureDto();
        feature.setTotalCost(BigDecimal.ZERO);
        feature.setDailyCost(BigDecimal.valueOf(5));
        feature.setCode("R-TV");
        feature.setName("TV");
        feature.setDescription("In room TV set.");
        room.setAvailableFeatures(Stream.of(feature).collect(Collectors.toSet()));
        occupied.setRoom(room);
        occupied.setActiveFeatures(Stream.of(feature).collect(Collectors.toSet()));
        habitation.setOccupied(Stream.of(occupied).collect(Collectors.toSet()));
        ServiceProvidedDto provided = new ServiceProvidedDto();
        provided.setName("Book table");
        provided.setCost(BigDecimal.TEN);
        provided.setDescription("Order table in restaurant");
        provided.setDuration(Duration.ofMinutes(40));
        provided.setFinishTime(timer.now().minusHours(3));
        provided.setStartTime(timer.now().minusHours(2));
        provided.setState(ServiceProvided.State.DONE);
        provided.setInChargeOf(receptionist);
        habitation.setServices(Stream.of(provided).collect(Collectors.toSet()));

        agreementDto.setHabitation(habitation);

        service.persist(agreementDto);

        LocalDate created = agreementDto.getCreatedAt().toLocalDate();
        Set<HotelAgreement> found = service.searchHotelAgreements(created.minusDays(1), created.plusDays(1));
        assertNotNull(found);
        assertFalse(found.isEmpty());
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void testRoomsAndFeatures() throws Exception {
        Set<Room.Feature> possible = service.allRoomFeatures();
        assertNotNull(possible);
        assertFalse(possible.isEmpty());
        Set<Room> rooms = service.findAllRooms();
        assertNotNull(rooms);
        assertFalse(rooms.isEmpty());

        Set<Hotel> hotels = service.allHotels();
        assertNotNull(hotels);
        assertFalse(hotels.isEmpty());
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void getActiveBooker() throws Exception {
        Employee server = service.getActiveBooker();
        assertNotNull(server);
        HotelAgreementDto agreementDto = new HotelAgreementDto();
        agreementDto.setId(generator.generate());
        agreementDto.setCreatedAt(timer.now().minusDays(2));
        agreementDto.setCard(CreditCardDto.builder()
                .expiredFrom(timer.today().plusYears(2)).id(generator.generate()).number("4111-1111-1111-1111")
                .owner("John Dow").build());
        GuestDto guest = makeTestGuest();

        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));

        ReservationDto reservation = new ReservationDto();
        reservation.setId(agreementDto.getId());
        reservation.setState(Reservation.State.NEW);
        EmployeeDto receptionist = makeTestEmployee();
        reservation.setReservedBy(receptionist);
        reservation.setFrom(timer.today().plusDays(1));
        reservation.setTo(timer.today().plusDays(2));
        agreementDto.setReservation(reservation);

        HabitationDto habitation = new HabitationDto();
        habitation.setId(agreementDto.getId());
        habitation.setCheckedInBy(receptionist);
        habitation.setCheckOutBy(receptionist);
        habitation.setCheckOut(timer.now().minusHours(1));
        habitation.setCheckIn(timer.now().plusDays(2));
        habitation.setHotelId("HO-1");
        habitation.setTotalFee(BigDecimal.valueOf(300));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setDailyCost(BigDecimal.valueOf(100));
        room.setWindows(1);
        room.setType(Room.Type.STANDARD);
        room.setFloor(2);
        room.setCapacity(1);
        room.setId("HO-1-202");
        RoomFeatureDto feature = new RoomFeatureDto();
        feature.setTotalCost(BigDecimal.ZERO);
        feature.setDailyCost(BigDecimal.valueOf(5));
        feature.setCode("R-TV");
        feature.setName("TV");
        feature.setDescription("In room TV set.");
        room.setAvailableFeatures(Stream.of(feature).collect(Collectors.toSet()));
        occupied.setRoom(room);
        occupied.setActiveFeatures(Stream.of(feature).collect(Collectors.toSet()));
        habitation.setOccupied(Stream.of(occupied).collect(Collectors.toSet()));
        ServiceProvidedDto provided = new ServiceProvidedDto();
        provided.setCost(BigDecimal.TEN);
        provided.setDescription("Order table in restaurant");
        provided.setDuration(Duration.ofMinutes(40));
        provided.setFinishTime(timer.now().minusHours(3));
        provided.setStartTime(timer.now().minusHours(2));
        provided.setState(ServiceProvided.State.DONE);
        provided.setInChargeOf(receptionist);
        habitation.setServices(Stream.of(provided).collect(Collectors.toSet()));

        agreementDto.setHabitation(habitation);

        service.persist(agreementDto);

        server = service.getActiveBooker();
        assertNotNull(server);
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void persistActionAndFindFreeRooms() throws Exception {
        RoomActivityDto activity1 = new RoomActivityDto();
        activity1.setRoomId("HO-UK-1-13");
        activity1.setHotelAgreementId("1234");
        activity1.setType(Room.Activity.Type.BOOKED);
        activity1.setId(generator.generate());
        activity1.setCreatedAt(LocalDateTime.now().minusDays(5));
        activity1.setStartedAt(LocalDateTime.now().minusDays(4));
        service.persist(activity1);
        RoomActivityDto activity2 = new RoomActivityDto();
        activity2.setRoomId("HO-UK-1-22");
        activity2.setHotelAgreementId("5678");
        activity2.setType(Room.Activity.Type.BOOKED);
        activity2.setId(generator.generate());
        activity2.setCreatedAt(LocalDateTime.now().minusDays(4));
        activity2.setStartedAt(LocalDateTime.now().minusDays(3));
        service.persist(activity2);

        Set<Room> free = service.findFreeRooms(LocalDate.now().minusDays(6), LocalDate.now().minusDays(2));

        assertNotNull(free);
        assertFalse(free.isEmpty());
        assertEquals(6, free.size());
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRoomActions() throws Exception {
        RoomActivityDto activity1 = new RoomActivityDto();
        activity1.setRoomId("HO-UK-1-13");
        activity1.setHotelAgreementId("1234");
        activity1.setType(Room.Activity.Type.BOOKED);
        activity1.setId(generator.generate());
        activity1.setCreatedAt(LocalDateTime.now().minusDays(5));
        activity1.setStartedAt(LocalDateTime.now().minusDays(4));
        service.persist(activity1);
        RoomActivityDto activity2 = new RoomActivityDto();
        activity2.setRoomId("HO-UK-1-22");
        activity2.setHotelAgreementId("5678");
        activity2.setType(Room.Activity.Type.BOOKED);
        activity2.setId(generator.generate());
        activity2.setCreatedAt(LocalDateTime.now().minusDays(4));
        activity2.setStartedAt(LocalDateTime.now().minusDays(3));
        service.persist(activity2);
        assertEquals(activity2, service.getActivityById(activity2.getId()));

        HotelAgreementDto argeement = new HotelAgreementDto();
        argeement.setId("5678");

        service.deleteRoomActions(argeement);

        assertNull(service.getActivityById(activity2.getId()));
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete() throws Exception {
        HotelAgreementDto agreementDto = new HotelAgreementDto();
        agreementDto.setId(generator.generate());
        agreementDto.setCreatedAt(timer.now().minusDays(2));
        agreementDto.setCard(CreditCardDto.builder()
                .expiredFrom(timer.today().plusYears(2)).id(generator.generate()).number("4111-1111-1111-1111")
                .owner("John Dow").build());
        GuestDto guest = makeTestGuest();

        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));

        ReservationDto reservation = new ReservationDto();
        reservation.setId(agreementDto.getId());
        reservation.setState(Reservation.State.NEW);
        EmployeeDto receptionist = makeTestEmployee();
        reservation.setReservedBy(receptionist);
        reservation.setFrom(timer.today().plusDays(1));
        reservation.setTo(timer.today().plusDays(2));
        agreementDto.setReservation(reservation);

        HabitationDto habitation = new HabitationDto();
        habitation.setId(agreementDto.getId());
        habitation.setCheckedInBy(receptionist);
        habitation.setCheckOutBy(receptionist);
        habitation.setCheckOut(timer.now().minusHours(1));
        habitation.setCheckIn(timer.now().plusDays(2));
        habitation.setHotelId("HO-1");
        habitation.setTotalFee(BigDecimal.valueOf(300));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setDailyCost(BigDecimal.valueOf(100));
        room.setWindows(1);
        room.setType(Room.Type.STANDARD);
        room.setFloor(2);
        room.setCapacity(1);
        room.setId("HO-1-202");
        RoomFeatureDto feature = new RoomFeatureDto();
        feature.setTotalCost(BigDecimal.ZERO);
        feature.setDailyCost(BigDecimal.valueOf(5));
        feature.setCode("R-TV");
        feature.setName("TV");
        feature.setDescription("In room TV set.");
        room.setAvailableFeatures(Stream.of(feature).collect(Collectors.toSet()));
        occupied.setRoom(room);
        occupied.setActiveFeatures(Stream.of(feature).collect(Collectors.toSet()));
        habitation.setOccupied(Stream.of(occupied).collect(Collectors.toSet()));
        ServiceProvidedDto provided = new ServiceProvidedDto();
        provided.setCost(BigDecimal.TEN);
        provided.setDescription("Order table in restaurant");
        provided.setDuration(Duration.ofMinutes(40));
        provided.setFinishTime(timer.now().minusHours(3));
        provided.setStartTime(timer.now().minusHours(2));
        provided.setState(ServiceProvided.State.DONE);
        provided.setInChargeOf(receptionist);
        habitation.setServices(Stream.of(provided).collect(Collectors.toSet()));

        agreementDto.setHabitation(habitation);

        service.persist(agreementDto);
        assertEquals(agreementDto, service.getById(agreementDto.getId()));

        service.delete(agreementDto);
        assertNull(service.getById(agreementDto.getId()));
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void allRoomFeatures() throws Exception {
        Set<Room.Feature> features = service.allRoomFeatures();
        assertNotNull(features);
        assertFalse(features.isEmpty());
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void freeDedicatedRooms() throws Exception {
        RoomActivityDto activity1 = new RoomActivityDto();
        activity1.setRoomId("HO-UK-1-13");
        activity1.setHotelAgreementId("1234");
        activity1.setType(Room.Activity.Type.BOOKED);
        activity1.setId(generator.generate());
        activity1.setCreatedAt(LocalDateTime.now().minusDays(5));
        activity1.setStartedAt(LocalDateTime.now().minusDays(4));
        service.persist(activity1);
        RoomActivityDto activity2 = new RoomActivityDto();
        activity2.setRoomId("HO-UK-1-22");
        activity2.setHotelAgreementId("5678");
        activity2.setType(Room.Activity.Type.DEDICATE);
        activity2.setId(generator.generate());
        activity2.setCreatedAt(LocalDateTime.now().minusDays(4));
        activity2.setStartedAt(LocalDateTime.now().minusDays(3));
        service.persist(activity2);
        assertEquals(activity2, service.getActivityById(activity2.getId()));

        service.freeDedicatedRooms(LocalDateTime.now());

        assertNull(service.getActivityById(activity2.getId()));
    }

    @org.junit.Test
    @Transactional(propagation = Propagation.REQUIRED)
    public void findReserved() throws Exception {
        HotelAgreementDto agreementDto = new HotelAgreementDto();
        agreementDto.setId(generator.generate());
        agreementDto.setCreatedAt(timer.now().minusDays(2));
        agreementDto.setCard(CreditCardDto.builder()
                .expiredFrom(timer.today().plusYears(2)).id(generator.generate()).number("4111-1111-1111-1111")
                .owner("John Dow").build());
        GuestDto guest = makeTestGuest();

        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));

        ReservationDto reservation = new ReservationDto();
        reservation.setId(agreementDto.getId());
        reservation.setState(Reservation.State.NEW);
        EmployeeDto receptionist = makeTestEmployee();
        reservation.setReservedBy(receptionist);
        reservation.setFrom(timer.today().plusDays(1));
        reservation.setTo(timer.today().plusDays(2));
        agreementDto.setReservation(reservation);

        HabitationDto habitation = new HabitationDto();
        habitation.setId(agreementDto.getId());
        habitation.setCheckedInBy(receptionist);
        habitation.setCheckOutBy(receptionist);
        habitation.setCheckOut(timer.now().minusHours(1));
        habitation.setCheckIn(timer.now().plusDays(2));
        habitation.setHotelId("HO-1");
        habitation.setTotalFee(BigDecimal.valueOf(300));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setGuests(Stream.of(guest).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setDailyCost(BigDecimal.valueOf(100));
        room.setWindows(1);
        room.setType(Room.Type.STANDARD);
        room.setFloor(2);
        room.setCapacity(1);
        room.setId("HO-1-202");
        RoomFeatureDto feature = new RoomFeatureDto();
        feature.setTotalCost(BigDecimal.ZERO);
        feature.setDailyCost(BigDecimal.valueOf(5));
        feature.setCode("R-TV");
        feature.setName("TV");
        feature.setDescription("In room TV set.");
        room.setAvailableFeatures(Stream.of(feature).collect(Collectors.toSet()));
        occupied.setRoom(room);
        occupied.setActiveFeatures(Stream.of(feature).collect(Collectors.toSet()));
        habitation.setOccupied(Stream.of(occupied).collect(Collectors.toSet()));
        ServiceProvidedDto provided = new ServiceProvidedDto();
        provided.setCost(BigDecimal.TEN);
        provided.setDescription("Order table in restaurant");
        provided.setDuration(Duration.ofMinutes(40));
        provided.setFinishTime(timer.now().minusHours(3));
        provided.setStartTime(timer.now().minusHours(2));
        provided.setState(ServiceProvided.State.DONE);
        provided.setInChargeOf(receptionist);
        habitation.setServices(Stream.of(provided).collect(Collectors.toSet()));

        agreementDto.setHabitation(habitation);

        service.persist(agreementDto);
        RoomActivityDto activity1 = new RoomActivityDto();
        activity1.setRoomId(room.getId());
        activity1.setHotelAgreementId(agreementDto.getId());
        activity1.setType(Room.Activity.Type.BOOKED);
        activity1.setId(generator.generate());
        activity1.setCreatedAt(agreementDto.getCreatedAt());
        activity1.setStartedAt(agreementDto.getCreatedAt());
        service.persist(activity1);

        Set<HotelAgreement> booked = service.findReserved(LocalDate.now().minusDays(3), LocalDate.now());

        assertNotNull(booked);
        assertFalse(booked.isEmpty());
        assertTrue(booked.contains(agreementDto));
    }

    // private methods
    private GuestDto makeTestGuest() {
        GuestDto guest = new GuestDto();
        guest.setLivesHere(true);
        guest.setId(generator.generate());
        guest.setLastName("Last_name");
        guest.setFirstName("First_name");
        guest.setDocument(PersonDto.ID.builder().citizenship("UA").expired(timer.today().plusYears(10))
                .number("12345678-AB").type("PASSPORT").build());
        guest.setLogin("login");
        guest.setLoginActiveFrom(timer.today());
        guest.setLoginActiveTo(timer.today().plusDays(2));
        guest.setTitle("Mr.");
        guest.setAbout("Test guest");
        guest.setBirthday(timer.today().minusYears(32));
        guest.setEmail("john.dow@nowhere.com");
        guest.setGender(Person.Gender.MALE);
        guest.setLanguage("english");
        guest.setMiddleName("Sam");
        guest.setPhone("+47 566-55-34");
        guest.setPhoto(new PhotoDto("Hello".getBytes(), "application/string"));
        guest.setTags("visitor,guest,test");
        return guest;
    }

    private EmployeeDto makeTestEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setActive(true);
        employeeDto.setId(generator.generate());
        employeeDto.setLastName("Last_name");
        employeeDto.setFirstName("First_name");
        employeeDto.setDocument(PersonDto.ID.builder().citizenship("RU").expired(timer.today().plusYears(10))
                .number("9988877-AB").type("PASS").build());
        employeeDto.setLogin("login");
        employeeDto.setHiredAt(timer.today().minusYears(20));
        employeeDto.setDismissedAt(timer.today().plusYears(2));
        employeeDto.setPositionId("Receptionist");
        employeeDto.setAbout("Test guest");
        employeeDto.setBirthday(timer.today().minusYears(32));
        employeeDto.setEmail("john.dow@nowhere.com");
        employeeDto.setGender(Person.Gender.MALE);
        employeeDto.setLanguage("english");
        employeeDto.setMiddleName("Sam");
        employeeDto.setPhone("+47 566-55-34");
        employeeDto.setPhoto(new PhotoDto("Hello".getBytes(), "application/string"));
        employeeDto.setTags("reserve,booking,test");
        return employeeDto;
    }


}