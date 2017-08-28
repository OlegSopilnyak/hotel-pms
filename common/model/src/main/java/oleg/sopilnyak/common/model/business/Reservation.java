package oleg.sopilnyak.common.model.business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Business Model type for manage reservations
 */
public interface Reservation extends HaveId, Serializable{
    /**
     * To get start date of reservation
     *
     * @return possible start date
     */
    LocalDate getFrom();
    /**
     * To get end date of reservation
     *
     * @return possible end date
     */
    LocalDate getTo();
    /**
     * To get state of reservation
     *
     * @return current value
     */
    State getState();

    /**
     * To get employee who in charge of reservation
     *
     * @return employee
     */
    Employee getReservedBy();

    /**
     * The enumeration of reservation state
     */
    enum State {
        NEW,CONFIRMED,CANCELED,EXPIRED,ACTIVE,DONE
    }
}
