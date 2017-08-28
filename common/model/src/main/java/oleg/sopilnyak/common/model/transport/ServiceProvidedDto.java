package oleg.sopilnyak.common.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.ServiceProvided;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Transport Model type for manage service provided by host to guest
 */
@Data
@EqualsAndHashCode(of = {"name", "state", "inChargeOf"})
@ToString(of = {"name", "state", "inChargeOf", "startTime", "finishTime", "cost"})
public class ServiceProvidedDto implements ServiceProvided {
    private static final long serialVersionUID = -1693927980294617380L;
    private String name;
    private String description;
    private State state;
    private EmployeeDto inChargeOf = new EmployeeDto();
    private LocalDateTime startTime;
    private LocalDateTime finishTime;
    private Duration duration;
    private BigDecimal cost;
}
