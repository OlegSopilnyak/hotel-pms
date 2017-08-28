package oleg.sopilnyak.common.model.business;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Business Model type for manage agreements with guests
 */
public interface HotelAgreement extends HaveId, Serializable {

    /**
     * To get exact date-time of start communication
     *
     * @return date and time
     */
    LocalDateTime getCreatedAt();

    /**
     * To get guests involved to agreement
     *
     * @return guests set
     */
    Set<Guest> getGuestSet();

    /**
     * To get reservation part of agreement
     *
     * @return part of agreement
     */
    Reservation getReservation();

    /**
     * To get habitation part of agreement
     *
     * @return part of agreement
     */
    Habitation getHabitation();

    /**
     * To get card for reservation and habitation
     *
     * @return credit card
     */
    CreditCard getCard();

}
