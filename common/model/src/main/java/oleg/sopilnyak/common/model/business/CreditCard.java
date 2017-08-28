package oleg.sopilnyak.common.model.business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Business Model type for manage credit cards of guests
 */
public interface CreditCard extends HaveId, Serializable {
    /**
     * The number of card
     *
     * @return number
     */
    String getNumber();

    /**
     * The date when card will expired
     *
     * @return expiration date
     */
    LocalDate getExpiredFrom();

    /**
     * To get name of owner
     *
     * @return card owner
     */
    String getOwner();
}
