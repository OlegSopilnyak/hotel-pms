package oleg.sopilnyak.common.model.business;

import java.time.LocalDate;

/**
 * Business Model type for manage hotel guests
 */
public interface Guest extends Person {
    /**
     * To get login to system for guest
     *
     * @return login name
     */
    String getLogin();

    /**
     * To get the title of guest
     *
     * @return title
     */
    String getTitle();

    /**
     * Flag is guest lives here now
     *
     * @return true if lives
     */
    Boolean getLivesHere();

    /**
     * To get the date since the login of guest is active
     *
     * @return the date
     */
    LocalDate getLoginActiveFrom();

    /**
     * To get the date till login of guest is active
     *
     * @return the date
     */
    LocalDate getLoginActiveTo();

}
