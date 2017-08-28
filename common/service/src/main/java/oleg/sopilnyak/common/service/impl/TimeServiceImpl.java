package oleg.sopilnyak.common.service.impl;

import oleg.sopilnyak.common.service.TimeService;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Realization of time service
 * @see oleg.sopilnyak.common.service.TimeService
 */
public class TimeServiceImpl implements TimeService {
    @Override
    public LocalDate today() {
        return LocalDate.now();
    }

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
