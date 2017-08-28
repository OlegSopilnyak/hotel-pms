package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Employee;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

/**
 * Persistence Model type of employee
 */
@Data
@Entity
@DiscriminatorValue("Employee")
@EqualsAndHashCode(callSuper = true)
public class EmployeeEntity extends PersonEntity implements Employee{
    @Column(name = "login")
    private String login;
    @Column(name = "position_title")
    private String positionId;
    @Column(name = "is_active")
    private Boolean active;
    @Column(name = "start_date")
    private LocalDate hiredAt;
    @Column(name = "end_date")
    private LocalDate dismissedAt;
}
