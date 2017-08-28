package oleg.sopilnyak.common.model.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Business Model type for manage habitation
 */
public interface Habitation extends HaveId,Serializable {
    /**
     * To get id of building with rooms
     *
     * @return building id
     */
    String getHotelId();

    /**
     * To get rooms occupied by guests
     *
     * @return set of occupied rooms
     */
    Set<OccupiedRoom> getOccupied();

    /**
     * To get exact time of guest is checking in
     *
     * @return actual value
     */
    LocalDateTime getCheckIn();

    /**
     * To get employee who made check in for guest
     *
     * @return the employee
     */
    Employee getCheckedInBy();

    /**
     * To get exact time of guest is checking in
     *
     * @return actual value
     */
    LocalDateTime getCheckOut();

    /**
     * To get employee who made check out for guest
     *
     * @return the employee
     */
    Employee getCheckOutBy();

    /**
     * To get services provider by hotel to guest during habitation
     *
     * @return set of service
     */
    Set<ServiceProvided> getServices();

    /**
     * To get total fee of habitation (without services)
     *
     * @return the cost
     */
    BigDecimal getTotalFee();

    /**
     * Occupied room type
     *
     */
    interface OccupiedRoom {
        /**
         * The room
         *
         * @return room
         */
        Room getRoom();

        /**
         * Guests living int the room
         *
         * @return guests
         */
        Set<Guest> getGuests();

        /**
         * Set of room features that are activated for the room
         *
         * @return active features
         */
        Set<Room.Feature> getActiveFeatures();
    }
}
