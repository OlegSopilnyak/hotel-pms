package oleg.sopilnyak.common.service;

import oleg.sopilnyak.common.model.business.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Service to manage persistence layer of system
 */
public interface PersistenceService {

    /**
     * Persist agreement
     *
     * @param agreement bean to persist
     */
    void persist(HotelAgreement agreement);

    /**
     * Get agreement by id
     *
     * @param id id of agreement
     * @return instance or null if not exists
     */
    HotelAgreement getById(String id);

    /**
     * To get the guest by person document
     *
     * @param document person document
     * @return instance or null if not exists
     */
    Guest getGuest(Person.ID document);

    /**
     * Persist Guest information
     *
     * @param guest item to store
     */
    void persist(Guest guest);

    /**
     * Search agreements reserved to appropriate date
     *
     * @param from planed start date
     * @param to   planed finish date
     * @return set of agreements
     */
    Set<HotelAgreement> searchHotelAgreements(LocalDate from, LocalDate to);

    /**
     * To get active employee who in charge of rooms booking
     *
     * @return employee (receptionist or manager)
     */
    Employee getActiveBooker();

    /**
     * To get set of rooms base on room activities
     *
     * @param from start period
     * @param to end period
     * @return set of free rooms
     */
    Set<Room> findFreeRooms(LocalDate from, LocalDate to);

    /**
     * Save changes of room activity
     *
     * @param activity instance to save
     */
    void persist(Room.Activity activity);

    /**
     * To delete all actions associated with agreement in state DEDICATED or CONFIRMED
     *
     * @param agreement the owner of actions
     */
    void deleteRoomActions(HotelAgreement agreement);

    /**
     * To delete the agreement
     *
     * @param agreement instance to delete
     */
    void delete(HotelAgreement agreement);

    /**
     * To get all room features available there
     *
     * @return set of features
     */
    Set<Room.Feature> allRoomFeatures();

    /**
     * To get guest by bean id
     *
     * @param id id of bean
     * @return Guest or null if not exists
     */
    Guest getGuest(String id);

    /**
     * To remove all room activities with expired
     *
     * @param mark time mark
     */
    void freeDedicatedRooms(LocalDateTime mark);

    /**
     * To get reserved agreements for the date period
     *
     * @param from date of start period
     * @param till date of end period
     * @return set of agreements with booked rooms
     */
    Set<HotelAgreement> findReserved(LocalDate from, LocalDate till);

    /**
     * To get all rooms
     *
     * @return set of rooms
     */
    Set<Room> findAllRooms();

    Set<Hotel> allHotels();

    Room.Activity getActivityById(String id);
}
