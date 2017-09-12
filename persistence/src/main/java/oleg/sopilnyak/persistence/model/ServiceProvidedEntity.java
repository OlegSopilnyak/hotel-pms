package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.ServiceProvided;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Persistence Model type for service provided by hotel
 */
@Data
@Entity
@Table(name = "provided_services")
@EqualsAndHashCode(callSuper = true)
class ServiceProvidedEntity extends AbstractEntity implements ServiceProvided{
    private String name;
    private String description;
    private State state;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = EmployeeEntity.class)
    @JoinTable(name = "services_done", joinColumns = @JoinColumn(name = "ps_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private EmployeeEntity inChargeOf;

    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private Duration duration;
    private BigDecimal cost;

}
