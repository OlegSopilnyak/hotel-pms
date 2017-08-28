package oleg.sopilnyak.common.model.business;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Business Model type for manage hotel rooms
 */
public interface Room extends HaveId,Serializable {

    /**
     * The quantity of quest which can live in the room
     *
     * @return capacity
     */
    Integer getCapacity();

    /**
     * The type of hotel room
     *
     * @return type
     */
    Type getType();

    /**
     * To get the floor where room is located
     *
     * @return floor
     */
    Integer getFloor();

    /**
     * To get quantity of windows in the room
     *
     * @return windows quantity
     */
    Integer getWindows();

    /**
     * a features set available for the room
     *
     * @return features set
     */
    Set<Feature> getAvailableFeatures();

    /**
     * Daily cost of room without features
     *
     * @return daily cost
     */
    BigDecimal getDailyCost();

    /**
     * The type of room
     */
    enum Type {
        STANDARD, BUSINESS, SUITE
    }

    /**
     * A feature in the room like TV Set, AirCondition, Sauna, Mini-bar etc
     */
    interface Feature{
        /**
         * The code of feature
         *
         * @return code
         */
        String getCode();

        /**
         * The name of feature
         *
         * @return the name
         */
        String getName();

        /**
         * A description of the feature
         *
         * @return description
         */
        String getDescription();

        /**
         * Daily cost of the feature
         *
         * @return daily cost (maybe null)
         */
        BigDecimal getDailyCost();

        /**
         * One time payment for the feature
         *
         * @return one time cost (maybe null)
         */
        BigDecimal getTotalCost();
    }

    /**
     * Type of room activity
     */
    interface Activity extends HaveId {
        /**
         * To get type of activity of room
         *
         * @return value
         */
        Type getType();

        /**
         * To get activity room id
         *
         * @return id of room
         */
        String getRoomId();
        /**
         * To get id of hotel agreement
         *
         * @return id of agreement
         */
        String getHotelAgreementId();

        /**
         * To get exact time of action begin
         *
         * @return time
         */
        LocalDateTime getCreatedAt();

        /**
         * To get time of start activity
         *
         * @return exact time
         */
        LocalDateTime getStartedAt();

        /**
         * To get time of finish action
         *
         * @return exact time
         */
        LocalDateTime getFinishedAt();

        /**
         * enumeration of room activity
         */
        enum Type{
            DEDICATE, BOOKED, OCCUPIED, USED
        }
    }
}
