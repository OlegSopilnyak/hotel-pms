package oleg.sopilnyak.common.model.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Business Model type for manage service provided by host to guest
 */
public interface ServiceProvided extends Serializable{
    /**
     * To get name of service
     *
     * @return value
     */
    String getName();

    /**
     * To get description of service
     *
     * @return value
     */
    String getDescription();

    /**
     * To get current state of service
     *
     * @return current state
     */
    State getState();

    /**
     * To get the employee who in charge of service
     *
     * @return the employee
     */
    Employee getInChargeOf();

    /**
     * To get start time of the service
     *
     * @return value
     */
    LocalDateTime getStartTime();

    /**
     * To get finish time of the service
     *
     * @return value
     */
    LocalDateTime getFinishTime();

    /**
     * To get duration of the service
     *
     * @return value
     */
    Duration getDuration();

    /**
     * To get the cost of service
     *
     * @return cost
     */
    BigDecimal getCost();

    /**
     * The enumeration of service provided state
     */
    enum State{
        NEW, ORDERED, CANCELED, IN_PROGRESS, DONE, FAIL
    }
}
