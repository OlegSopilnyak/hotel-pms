package oleg.sopilnyak.common.model.business;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Business Model root type of people involved in
 */
public interface Person extends HaveId,Serializable {
    /**
     * To get first name of the person
     *
     * @return first name
     */
    String getFirstName();

    /**
     * To get middle name of the person (mandatory for russian guests)
     *
     * @return middle name
     */
    String getMiddleName();

    /**
     * To get last name of the person
     *
     * @return last name
     */
    String getLastName();

    /**
     * To get the day of person birth
     *
     * @return day of birth
     */
    LocalDate getBirthday();

    /**
     * To get gender of person
     *
     * @return the value
     */
    Gender getGender();

    /**
     * To get person ID document
     *
     * @return ID
     */
    ID getDocument();

    /**
     * To get photo of person
     *
     * @return the photo
     */
    Photo getPhoto();

    /**
     * To get preferred language for communication
     *
     * @return language id
     */
    String getLanguage();

    /**
     * To get gender of person
     *
     * @return the value
     */
    String getPhone();

    /**
     * To get e-mail address of person
     *
     * @return e-mail address
     */
    String getEmail();

    /**
     * To get optional information about person
     *
     * @return information
     */
    String getAbout();

    /**
     * To get tags associated with person
     *
     * @return tags
     */
    String getTags();

    /**
     * Enumeration of person's gender
     */
    enum Gender{
        MALE,FEMALE
    }

    /**
     * The document of person
     */
    interface ID {
        /**
         * To get citizenship of the person
         *
         * @return value
         */
        String getCitizenship();

        /**
         * To get type of person's ID
         *
         * @return document type
         */
        String getType();

        /**
         * To get a document number
         *
         * @return number
         */
        String getNumber();

        /**
         * To get date of document expiration
         *
         * @return last date of valid document
         */
        LocalDate getExpired();

    }

}
