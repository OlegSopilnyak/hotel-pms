package oleg.sopilnyak.common.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.Reservation;

import java.time.LocalDate;

/**
 * Transport Model type for manage reservations
 */
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"id"})
public class ReservationDto implements Reservation {
    private static final long serialVersionUID = 5004512816535184460L;
    private String id;
    private LocalDate from;
    private LocalDate to;
    private State state;
    private EmployeeDto reservedBy = new EmployeeDto();
}
