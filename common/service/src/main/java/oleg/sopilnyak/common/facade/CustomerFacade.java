package oleg.sopilnyak.common.facade;

import oleg.sopilnyak.common.model.business.*;

import java.time.LocalDate;
import java.util.Set;

/**
 * Facade to interact with REST controllers
 */
public interface CustomerFacade {

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
     * @param id id of created agrrement
     * @param from arrival date
     * @param to department date
     * @param guests guests set
     * @param rooms quantity of rooms
     * @return agreement with hotel
     */
    PreBookingResult change(String id, LocalDate from, LocalDate to, Set<Guest> guests, int rooms);

    /**
     * To confirm reservation and clarify rooms arrangement
     *
     * @param confirmation details of confirmation
     * @return id of hotel agreement
     */
    String confirm(ConfirmReservation confirmation);

    /**
     * To get created agreement by id
     *
     * @param id agreement id
     * @return instance or null if not exists
     */
    HotelAgreement retrieve(String id);

    /**
     * Cancel previous booking
     *
     * @param id id of HotelAgreement
     */
    void cancelBooking(String id);

    /**
     * To get reserved agreements for the date period
     *
     * @param from date of start period
     * @param till date of end period
     * @return set of agreements with booked rooms
     */
    Set<HotelAgreement> findReserved(LocalDate from, LocalDate till);

}
