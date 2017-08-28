package oleg.sopilnyak.common.model.business;

import java.time.LocalDate;

/**
 * Business Model type for manage hotel employees
 */
public interface Employee extends Person {
    /**
     * To get login to system for employee
     *
     * @return login name
     */
    String getLogin();

    /**
     * To get the id of position in hotel (id of hotel also included)
     *
     * @return id of position
     */
    String getPositionId();

    /**
     * Flag is employee now in charge
     *
     * @return true if in charge
     */
    Boolean getActive();

    /**
     * To get the date when employee was hired
     *
     * @return the date
     */
    LocalDate getHiredAt();

    /**
     * To get the date when employee was dismissed
     *
     * @return the date
     */
    LocalDate getDismissedAt();
}
