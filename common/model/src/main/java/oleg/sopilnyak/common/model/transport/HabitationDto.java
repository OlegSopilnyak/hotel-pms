package oleg.sopilnyak.common.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.Habitation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Transport Model type for manage habitation
 */
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"hotelId", "checkIn", "checkOut", "totalFee"})
public class HabitationDto implements Habitation{
    private static final long serialVersionUID = -1310801956699406791L;
    private String id;
    private String hotelId;
//    private Set<HabitationOccupiedDto> occupied = new LinkedHashSet<>();
    private Set occupied = new LinkedHashSet<>();
    private LocalDateTime checkIn;
    private EmployeeDto checkedInBy = new EmployeeDto();
    private LocalDateTime checkOut;
    private EmployeeDto checkOutBy = new EmployeeDto();
//    private Set<ServiceProvidedDto> services = new LinkedHashSet<>();
    private Set services = new LinkedHashSet<>();
    private BigDecimal totalFee;

}
