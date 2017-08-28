package oleg.sopilnyak.common.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.Employee;

import java.time.LocalDate;

/**
 * Transport Model type for manage hotel employees
 */
@Data
@EqualsAndHashCode(of = {"login", "positionId"}, callSuper = true)
@ToString(of = {"positionId"}, callSuper = true)
public class EmployeeDto extends PersonDto implements Employee {
    private String login;
    private String positionId;
    private Boolean active;
    private LocalDate hiredAt;
    private LocalDate dismissedAt;
}
