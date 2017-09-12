package oleg.sopilnyak.common.facade.impl;

import oleg.sopilnyak.common.model.business.*;
import oleg.sopilnyak.common.model.transport.*;
import oleg.sopilnyak.common.service.PersistenceService;
import oleg.sopilnyak.common.service.ReservationService;
import oleg.sopilnyak.common.util.Utility;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerFacadeImplTest {

    @Mock
    private PersistenceService persistence;
    @Mock
    private ReservationService service;
    @InjectMocks
    private CustomerFacadeImpl facade = new CustomerFacadeImpl();

    @After
    public void tearDown() throws Exception {
        reset(persistence, service);
    }

    @Test
    public void book() throws Exception {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = Utility.makeOneGuest();
        Room room = new RoomDto();
        PreBookingResultDto resultDto = new PreBookingResultDto();
        resultDto.setGuests(guests);
        resultDto.setHotelAgreementId("1234");
        resultDto.setDedicatedRoom(null);
        when(service.book(from,to,guests,1, new HashSet<>())).thenReturn(resultDto);

        PreBookingResult result = facade.book(from, to, guests, 1, new HashSet<>());

        assertNotNull(result);
        assertEquals(result, resultDto);
        verify(service, times(1)).book(eq(from), eq(to), eq(guests), eq(1), anySetOf(Room.Feature.class));
    }

    @Test
    public void change() throws Exception {
        String id="12345";
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate to = LocalDate.now().plusDays(2);
        Set<Guest> guests = Utility.makeOneGuest();
        Room room = new RoomDto();
        PreBookingResultDto resultDto = new PreBookingResultDto();
        resultDto.setGuests(guests);
        resultDto.setHotelAgreementId("1234");
        resultDto.setDedicatedRoom(null);
        when(service.change(id, from,to,guests,1)).thenReturn(resultDto);

        PreBookingResult result = facade.change(id, from, to, guests, 1);

        assertNotNull(result);
        assertEquals(result, resultDto);
        verify(service, times(1)).change(eq(id), eq(from), eq(to), eq(guests), eq(1));
    }

    @Test
    public void confirm() throws Exception {
        String id="12345";
        CreditCardDto card = new CreditCardDto();
        ConfirmedReservationDto confirmation = new ConfirmedReservationDto();
        confirmation.setAgreementId(id);
        confirmation.setCreditCard(card);


        String agreementId = facade.confirm(confirmation);

        assertEquals(id, agreementId);
        verify(service, times(1)).confirmReservation(eq(id), anySetOf(ConfirmReservation.BookedRoom.class), eq(card));
    }

    @Test
    public void retrieve() throws Exception {
        String id="12345";
        HotelAgreementDto stored = new HotelAgreementDto();
        stored.setId(id);
        when(persistence.getById(id)).thenReturn(stored);

        HotelAgreement agreement = facade.retrieve(id);

        assertNotNull(agreement);
        assertEquals(id, agreement.getId());
        verify(persistence, times(1)).getById(eq(id));
    }

    @Test
    public void cancelBooking() throws Exception {
        String id="12345";

        facade.cancelBooking(id);

        verify(service, times(1)).cancelBooking(eq(id));
    }

    @Test
    public void findReserved() throws Exception {
        LocalDate from = LocalDate.now().plusDays(1);
        LocalDate till = LocalDate.now().plusDays(2);

        Set<HotelAgreement>reserved = facade.findReserved(from, till);

        assertNotNull(reserved);
        assertEquals(0, reserved.size());
        verify(persistence, times(1)).findReserved(eq(from), eq(till));
    }
    // private methods

}