package oleg.sopilnyak.common.service.impl;

import oleg.sopilnyak.common.model.business.*;
import oleg.sopilnyak.common.model.transport.*;
import oleg.sopilnyak.common.service.IdGeneratorService;
import oleg.sopilnyak.common.service.PersistenceService;
import oleg.sopilnyak.common.service.TimeService;
import oleg.sopilnyak.common.service.exception.CannotChangeReservationException;
import oleg.sopilnyak.common.service.exception.CannotReserveException;
import oleg.sopilnyak.common.service.exception.ResourceNotFoundException;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static oleg.sopilnyak.common.util.Utility.makeOneGuest;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {
    @Spy
    private Mapper mapper = new DozerBeanMapper(Collections.singletonList("dozerJdk8Converters.xml"));
    @Spy
    private TimeService timer = new TimeServiceImpl();
    @Spy
    private IdGeneratorService idGenerator = new IdGeneratorServiceImpl();

    @Mock
    private ObjectFactory<GuestDto> guestsFactory;
    @Mock
    private ObjectFactory<HotelAgreementDto> agreementsFactory;
    @Mock
    private ObjectFactory<HabitationOccupiedDto> occupiedFactory;
    @Mock
    private ObjectFactory<PreBookingResultDto> resultFactory;
    @Mock
    private ObjectFactory<RoomActivityDto> activityFactory;

    @Mock
    private PersistenceService persistence;
    @Mock
    private ScheduledExecutorService runner;

    @InjectMocks
    private ReservationServiceImpl service = new ReservationServiceImpl();

    @Before
    public void setUp() throws Exception {
        ReflectionTestUtils.setField(service, "scanDelay", 10L);
        ReflectionTestUtils.setField(service, "expiredAfterMinutes", 30L);
        ScheduledFuture future = mock(ScheduledFuture.class);
        //noinspection unchecked
        when(runner.scheduleWithFixedDelay(any(), anyLong(), anyLong(), any(TimeUnit.class))).thenReturn(future);
        when(guestsFactory.getObject()).thenReturn(new GuestDto());
        when(agreementsFactory.getObject()).thenReturn(new HotelAgreementDto());
        when(occupiedFactory.getObject()).thenReturn(new HabitationOccupiedDto());
        when(resultFactory.getObject()).thenReturn(new PreBookingResultDto());
        when(activityFactory.getObject()).thenReturn(new RoomActivityDto());
        service.init();
    }

    @After
    public void tearDown() throws Exception {
        service.destroy();
        reset(persistence, runner);
    }

    @Test
    public void init() throws Exception {
        assertTrue(service.isActive());
        service.destroy();
        service.init();
        assertTrue(service.isActive());
        verify(runner, times(2)).scheduleWithFixedDelay(any(), anyLong(), anyLong(), eq(TimeUnit.MINUTES));
    }

    @Test
    public void destroy() throws Exception {
        assertTrue(service.isActive());
        service.destroy();
        assertFalse(service.isActive());
        verify(runner, times(1)).scheduleWithFixedDelay(any(), anyLong(), anyLong(), eq(TimeUnit.MINUTES));
    }

    @Test
    public void book() throws Exception {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = makeOneGuest();
        Room room = new RoomDto();
        when(persistence.findFreeRooms(from, to)).thenReturn(Stream.of(room).collect(Collectors.toSet()));

        PreBookingResult result = service.book(from, to, guests, 1, new HashSet<>());

        assertNotNull(result);
        assertEquals(result.getDedicatedRoom().size() , 1);
        assertEquals(result.getDedicatedRoom().iterator().next(), room);
        assertEquals(result.getGuests().size(), 1);
        assertEquals(result.getGuests(), guests);
        assertFalse(StringUtils.isEmpty(result.getHotelAgreementId()));
    }
    @Test(expected = IllegalStateException.class)
    public void bookNotActive() throws Exception {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = makeOneGuest();
        Room room = new RoomDto();
        when(persistence.findFreeRooms(from, to)).thenReturn(Stream.of(room).collect(Collectors.toSet()));

        service.destroy();
        PreBookingResult result = service.book(from, to, guests, 1, new HashSet<>());

        fail("Here we are waiting exception");
    }

    @Test(expected = CannotReserveException.class)
    public void bookWrongGuest() throws Exception {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = makeOneGuest();
        Room room = new RoomDto();
        when(persistence.findFreeRooms(from, to)).thenReturn(Stream.of(room).collect(Collectors.toSet()));
        when(persistence.getGuest(any(PersonDto.ID.class))).thenReturn(new GuestDto());

        PreBookingResult result = service.book(from, to, guests, 1, new HashSet<>());

        fail("Here we are waiting exception");
    }

    @Test(expected = CannotReserveException.class)
    public void bookNoRooms() throws Exception {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = makeOneGuest();
        Room room = new RoomDto();

        PreBookingResult result = service.book(from, to, guests, 1, new HashSet<>());

        fail("Here we are waiting exception");
    }

    @Test
    public void cancelBooking() throws Exception {
        String id = "12345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.CONFIRMED);
        when(persistence.getById(id)).thenReturn(agreementDto);

        service.cancelBooking(id);

        verify(persistence, times(1)).getById(eq(id));
        verify(persistence, times(1)).delete(eq(agreementDto));
        verify(persistence, times(1)).deleteRoomActions(eq(agreementDto));
    }

    @Test(expected = IllegalStateException.class)
    public void cancelBookingNotActive() throws Exception {
        String id = "12345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.CONFIRMED);
        when(persistence.getById(id)).thenReturn(agreementDto);

        service.destroy();
        service.cancelBooking(id);

        fail("Here we are waiting exception");
    }

    @Test(expected = IllegalStateException.class)
    public void cancelBookingWrongState() throws Exception {
        String id = "12345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.ACTIVE);
        when(persistence.getById(id)).thenReturn(agreementDto);

        service.cancelBooking(id);

        fail("Here we are waiting exception");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void cancelBookingNotFound() throws Exception {
        String id = "12345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.CONFIRMED);

        service.cancelBooking(id);

        fail("Here we are waiting exception");
    }

    @Test
    public void change() throws Exception {
        String id = "12345678";
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = makeOneGuest();
        Room room = new RoomDto();
        when(persistence.findFreeRooms(from, to)).thenReturn(Stream.of(room).collect(Collectors.toSet()));
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.CONFIRMED);
        when(persistence.getById(id)).thenReturn(agreementDto);

        PreBookingResult result = service.change(id, from, to, guests, 1);

        assertNotNull(result);
        assertEquals(result.getDedicatedRoom().size() , 1);
        assertEquals(result.getDedicatedRoom().iterator().next(), room);
        assertEquals(result.getGuests().size(), 1);
        assertEquals(result.getGuests(), guests);
        assertFalse(StringUtils.isEmpty(result.getHotelAgreementId()));
    }

    @Test(expected = IllegalStateException.class)
    public void changeNotActive() throws Exception {
        String id = "12345678";
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = makeOneGuest();
        Room room = new RoomDto();
        when(persistence.findFreeRooms(from, to)).thenReturn(Stream.of(room).collect(Collectors.toSet()));
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.CONFIRMED);
        when(persistence.getById(id)).thenReturn(agreementDto);

        service.destroy();
        PreBookingResult result = service.change(id, from, to, guests, 1);

        fail("Here we are waiting exception");
    }

    @Test(expected = CannotChangeReservationException.class)
    public void changeWrongGuest() throws Exception {
        String id = "12345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.CONFIRMED);
        when(persistence.getById(id)).thenReturn(agreementDto);
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = makeOneGuest();
        Room room = new RoomDto();
        when(persistence.findFreeRooms(from, to)).thenReturn(Stream.of(room).collect(Collectors.toSet()));
        when(persistence.getGuest(any(PersonDto.ID.class))).thenReturn(new GuestDto());

        PreBookingResult result = service.change(id, from, to, guests, 1);

        fail("Here we are waiting exception");
    }

    @Test(expected = CannotChangeReservationException.class)
    public void changeNoRooms() throws Exception {
        String id = "12345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.CONFIRMED);
        when(persistence.getById(id)).thenReturn(agreementDto);
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = makeOneGuest();
        when(persistence.getGuest(any(PersonDto.ID.class))).thenReturn(new GuestDto());

        PreBookingResult result = service.change(id, from, to, guests, 1);

        fail("Here we are waiting exception");
    }

    @Test
    public void confirmReservation() throws Exception {
        String id = "345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.NEW);
        when(persistence.getById(id)).thenReturn(agreementDto);

        CreditCard card = new CreditCardDto();
        ConfirmedRoomDto confirmedRoom = new ConfirmedRoomDto();
        confirmedRoom.setRoomCode("34");
        Set<ConfirmReservation.BookedRoom> rooms = Stream.of(confirmedRoom).collect(Collectors.toSet());
        Guest guest = makeOneGuest().iterator().next();
        confirmedRoom.setGuestIds(Stream.of(guest.getId()).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setId("34");
        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setRoom(room);
        occupied.setGuests(new LinkedHashSet());
        agreementDto.getHabitation().setOccupied(Stream.of(occupied).collect(Collectors.toSet()));

        service.confirmReservation(id, rooms, card);

        assertEquals(Reservation.State.CONFIRMED, agreementDto.getReservation().getState());
        verify(persistence, times(1)).getById(eq(id));
        verify(persistence,times((1))).persist(eq(agreementDto));

    }

    @Test(expected = IllegalStateException.class)
    public void confirmReservationNotActive() throws Exception {
        String id = "345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.NEW);
        when(persistence.getById(id)).thenReturn(agreementDto);

        CreditCard card = new CreditCardDto();
        ConfirmedRoomDto confirmedRoom = new ConfirmedRoomDto();
        confirmedRoom.setRoomCode("34");
        Set<ConfirmReservation.BookedRoom> rooms = Stream.of(confirmedRoom).collect(Collectors.toSet());
        Guest guest = makeOneGuest().iterator().next();
        confirmedRoom.setGuestIds(Stream.of(guest.getId()).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setId("34");
        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setRoom(room);
        occupied.setGuests(new LinkedHashSet());
        agreementDto.getHabitation().setOccupied(Stream.of(occupied).collect(Collectors.toSet()));

        service.destroy();
        service.confirmReservation(id, rooms, card);

        fail("Here we are waiting exception");

    }

    @Test(expected = ResourceNotFoundException.class)
    public void confirmReservationNoAgreement() throws Exception {
        String id = "345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.NEW);


        CreditCard card = new CreditCardDto();
        ConfirmedRoomDto confirmedRoom = new ConfirmedRoomDto();
        confirmedRoom.setRoomCode("34");
        Set<ConfirmReservation.BookedRoom> rooms = Stream.of(confirmedRoom).collect(Collectors.toSet());
        Guest guest = makeOneGuest().iterator().next();
        confirmedRoom.setGuestIds(Stream.of(guest.getId()).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setId("34");
        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setRoom(room);
        occupied.setGuests(new LinkedHashSet());
        agreementDto.getHabitation().setOccupied(Stream.of(occupied).collect(Collectors.toSet()));

        service.confirmReservation(id, rooms, card);

        fail("Here we are waiting exception");

    }

    @Test(expected = ResourceNotFoundException.class)
    public void confirmReservationNoRoomByRoomCode() throws Exception {
        String id = "345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.NEW);


        CreditCard card = new CreditCardDto();
        ConfirmedRoomDto confirmedRoom = new ConfirmedRoomDto();
        confirmedRoom.setRoomCode("34");
        Set<ConfirmReservation.BookedRoom> rooms = Stream.of(confirmedRoom).collect(Collectors.toSet());
        Guest guest = makeOneGuest().iterator().next();
        confirmedRoom.setGuestIds(Stream.of(guest.getId()).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setId("35");
        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setRoom(room);
        occupied.setGuests(new LinkedHashSet());
        agreementDto.getHabitation().setOccupied(Stream.of(occupied).collect(Collectors.toSet()));

        service.confirmReservation(id, rooms, card);

        fail("Here we are waiting exception");

    }

    @Test(expected = IllegalStateException.class)
    public void confirmReservationWrongState() throws Exception {
        String id = "345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.CONFIRMED);
        when(persistence.getById(id)).thenReturn(agreementDto);


        CreditCard card = new CreditCardDto();
        ConfirmedRoomDto confirmedRoom = new ConfirmedRoomDto();
        confirmedRoom.setRoomCode("34");
        Set<ConfirmReservation.BookedRoom> rooms = Stream.of(confirmedRoom).collect(Collectors.toSet());
        Guest guest = makeOneGuest().iterator().next();
        confirmedRoom.setGuestIds(Stream.of(guest.getId()).collect(Collectors.toSet()));
        RoomDto room = new RoomDto();
        room.setId("34");
        agreementDto.setGuestSet(Stream.of(guest).collect(Collectors.toSet()));
        HabitationOccupiedDto occupied = new HabitationOccupiedDto();
        occupied.setRoom(room);
        occupied.setGuests(new LinkedHashSet());
        agreementDto.getHabitation().setOccupied(Stream.of(occupied).collect(Collectors.toSet()));

        service.confirmReservation(id, rooms, card);

        fail("Here we are waiting exception");

    }

    @Test
    public void removeHotelAgreement() throws Exception {
        String id = "345678";
        HotelAgreementDto agreementDto = agreementsFactory.getObject();
        agreementDto.getReservation().setState(Reservation.State.CONFIRMED);
        when(persistence.getById(id)).thenReturn(agreementDto);


        service.removeHotelAgreement(agreementDto);

        verify(persistence, times(1)).deleteRoomActions(eq(agreementDto));
        verify(persistence, times(1)).delete(eq(agreementDto));
    }

    @Test
    public void dedicateFreeRooms() throws Exception {
        final Set<HabitationOccupiedDto> roomSet = new LinkedHashSet<>();
        HotelAgreement agreement = new HotelAgreementDto();
        RoomDto room = new RoomDto();
        room.setId("34");
        Set<Room> rooms = new LinkedHashSet<>();
        rooms.add(room);


        service.dedicateFreeRooms(rooms, agreement);

        verify(idGenerator, times(1)).generate();
        verify(timer, times(1)).now();
        verify(persistence,times((1))).persist(eq(agreement));
    }

    @Test
    public void getAvailableRooms() throws Exception {
        final LocalDate from = timer.today().minusDays(20);
        final LocalDate to = timer.today();
        int rooms = 1;
        RoomDto room = new RoomDto();
        Set<Room.Feature> features = Stream.of(new RoomFeatureDto()).collect(Collectors.toSet());
        room.setAvailableFeatures(features);

        Set<Room> roomSet = Stream.of(room).collect(Collectors.toSet());
        when(persistence.findFreeRooms(from, to)).thenReturn(roomSet);

        Set<Room> free = service.getAvailableRooms(from, to, rooms, features);

        assertNotNull(free);
        verify(persistence, times(1)).findFreeRooms(eq(from), eq(to));
        assertEquals(1, free.size());
    }

    @Test
    public void buildAgreement() throws Exception {
        final LocalDate from = timer.today().minusDays(20);
        final LocalDate to = timer.today();
        int rooms = 1;
        Set<Guest> guests = makeOneGuest();

        HotelAgreement agreement = service.buildAgreement(guests, from, to, rooms);

        assertNotNull(agreement);
        verify(idGenerator, times(1)).generate();
        verify(persistence, times(1)).getActiveBooker();
        verify(persistence, times(1)).persist(any(HotelAgreement.class));
    }

    @Test
    public void checkHotelAgreement() throws Exception {
        final LocalDate from = timer.today().minusDays(20);
        final LocalDate to = timer.today();
        int rooms = 1;
        Set<Guest> guests = makeOneGuest();

        HotelAgreementDto agreement = new HotelAgreementDto();
        agreement.setGuestSet(guests);
        when(persistence.searchHotelAgreements(from, to)).thenReturn(Stream.of(agreement).collect(Collectors.toSet()));

        service.checkHotelAgreement(guests, from, to, rooms);

        verify(persistence, times(0)).findReserved(eq(from), eq(to));
    }

    @Test(expected = CannotReserveException.class)
    public void checkHotelAgreementFailReserved() throws Exception {
        final LocalDate from = timer.today().minusDays(20);
        final LocalDate to = timer.today();
        int rooms = 1;
        Set<Guest> guests = makeOneGuest();

        HotelAgreementDto agreement = new HotelAgreementDto();
        agreement.setGuestSet(guests);
        agreement.getReservation().setState(Reservation.State.CONFIRMED);
        agreement.getHabitation().getOccupied().add(new Object());

        when(persistence.searchHotelAgreements(from, to)).thenReturn(Stream.of(agreement).collect(Collectors.toSet()));


        service.checkHotelAgreement(guests, from, to, rooms);

        fail("Here we are waiting exception");
    }

    @Test
    public void register() throws Exception {
        Set<Guest> guests = makeOneGuest();

        service.register(guests);

        verify(persistence, times(1)).getGuest(any(PersonDto.ID.class));
        verify(persistence, times(1)).persist(any(GuestDto.class));

        when(persistence.getGuest(any(PersonDto.ID.class))).thenReturn(guests.iterator().next());

        service.register(guests);

        verify(persistence, times(2)).getGuest(any(PersonDto.ID.class));
        verify(persistence, times(1)).persist(any(GuestDto.class));
    }

    @Test(expected = CannotReserveException.class)
    public void registerWrongGuest() throws Exception {
        Set<Guest> guests = makeOneGuest();
        when(persistence.getGuest(any(PersonDto.ID.class))).thenReturn(new GuestDto());

        service.register(guests);

        fail("Here we are waiting exception");
    }

    @Test
    public void checkDedicatedRooms() throws Exception {
        service.checkDedicatedRooms();

        verify(persistence, times(1)).freeDedicatedRooms(any(LocalDateTime.class));
        verify(timer, times(1)).now();
    }

    // private methods

}