package oleg.sopilnyak.common.model.business;

import java.util.Set;

/**
 * Business Model type for manage confirm reservation phase 2
 */
public interface ConfirmReservation {
    /**
     * To get id of agreement
     *
     * @return agreement id
     */
    String getAgreementId();

    /**
     * CreditCard for pay
     *
     * @return card
     */
    CreditCard getCreditCard();

    /**
     * To get the set of arranged rooms
     *
     * @return arranged rooms
     */
    Set<BookedRoom> getBookedRooms();

    /**
     * Wrapper for arranged and adjusted room
     */
    interface BookedRoom {
        /**
         * The room code
         *
         * @return room code
         */
        String getRoomCode();

        /**
         * Ids of guests who will live in the room
         *
         * @return guest ids set
         */
        Set<String> getGuestIds();

        /**
         * Ids of room-features choose by guest
         *
         * @return features ids set
         */
        Set<String> getFeatureIds();
    }
}
