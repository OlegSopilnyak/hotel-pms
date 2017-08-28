package oleg.sopilnyak.common.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Service to manipulate date-time data
 */
public interface TimeService {
    /**
     * To get today as LocalDate
     *
     * @return today
     */
    LocalDate today();

    /**
     * To get now date-time
     *
     * @return now
     */
    LocalDateTime now();
}
