package oleg.sopilnyak.common.service.impl;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimeServiceImplTest {

    private TimeServiceImpl service = new TimeServiceImpl();
    @Test
    public void today() throws Exception {
        LocalDate today = service.today();
        assertEquals(today, LocalDate.now());
    }

    @Test
    public void now() throws Exception {
        LocalDateTime now = service.now();
        Duration dur = Duration.between(now,LocalDateTime.now());
        assertTrue(dur.toMillis() >= 0);
    }

}