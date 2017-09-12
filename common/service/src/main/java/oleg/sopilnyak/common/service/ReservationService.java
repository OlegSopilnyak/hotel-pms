package oleg.sopilnyak.common.service;

import oleg.sopilnyak.common.model.business.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Service to reserve rooms in hotel
 */
public interface ReservationService {
    /**
     * To book rooms in hotel
     *
     * @param from arrival date
     * @param to department date
     * @param guests guests set
     * @param rooms quantity of rooms
     * @param features desirable room features
     * @return agreement with hotel
     */
    PreBookingResult book(LocalDate from, LocalDate to, Set<Guest> guests, int rooms, Set<Room.Feature> features);

    /**
     * To change reservation parameters
     *
     * @param id id of created agreement
     * @param from arrival date
     * @param to department date
     * @param guests guests set
     * @param rooms quantity of rooms
     * @return agreement with hotel
     */
    PreBookingResult change(String id, LocalDate from, LocalDate to, Set<Guest> guests, int rooms);

    /**
     * To confirm pre-booked reservation
     *
     * @param id agreement id
     * @param rooms booked rooms
     * @param card credit card to pay
     */
    void confirmReservation(String id, Set<ConfirmReservation.BookedRoom> rooms, CreditCard card);

    /**
     * Cancel previous booking
     *
     * @param id id of HotelAgreement
     */
    void cancelBooking(String id);
}
