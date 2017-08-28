package oleg.sopilnyak.common.facade.impl;

import lombok.extern.slf4j.Slf4j;
import oleg.sopilnyak.common.facade.CustomerFacade;
import oleg.sopilnyak.common.model.business.*;
import oleg.sopilnyak.common.service.PersistenceService;
import oleg.sopilnyak.common.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

/**
 * Realization of facade
 *
 * @see oleg.sopilnyak.common.facade.CustomerFacade
 */
@Slf4j
public class CustomerFacadeImpl implements CustomerFacade {

    @Autowired
    private ReservationService reservation;
    @Autowired
    private PersistenceService persistence;


    public PreBookingResult book(LocalDate from, LocalDate to, Set<Guest> guests, int rooms, Set<Room.Feature> features) {
        log.debug("Creating reservation");
        return reservation.book(from, to, guests, rooms, features);
    }

    public PreBookingResult change(String id, LocalDate from, LocalDate to, Set<Guest> guests, int rooms) {
        log.debug("Updating agreement with id:{}", id);
        return reservation.change(id, from, to, guests, rooms);
    }

    @Override
    public String confirm(ConfirmReservation confirmation) {
        final String id = confirmation.getAgreementId();
        reservation.confirmReservation(id, confirmation.getBookedRooms(), confirmation.getCreditCard());
        return id;
    }

    public HotelAgreement retrieve(String id) {
        log.debug("Try to get agreement with id:{}", id);
        return persistence.getById(id);
    }

    public void cancelBooking(String id) {
        log.debug("Canceling agreement with id:{}", id);
        reservation.cancelBooking(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Set<HotelAgreement> findReserved(LocalDate from, LocalDate till) {
        return persistence.findReserved(from, till);
    }

}
