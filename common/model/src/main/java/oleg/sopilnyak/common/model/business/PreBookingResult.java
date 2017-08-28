package oleg.sopilnyak.common.model.business;

import java.io.Serializable;
import java.util.Set;

/**
 * Business Model type for manage credit booking result phase 1
 */
public interface PreBookingResult extends Serializable{
    /**
     * To get id of hotel agreement document
     *
     * @return document id
     */
    String getHotelAgreementId();

    /**
     * To get set of rooms dedicated for agreement
     *
     * @return set of rooms
     */
    Set<Room> getDedicatedRoom();

    /**
     * To get set of guests taking part in agreement
     *
     * @return set of guests
     */
    Set<Guest> getGuests();
}
