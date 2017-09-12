package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Habitation;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Persistence Model type for habitation in hotel
 */
@Data
@Entity
@Table(name = "habitation_part")
@EqualsAndHashCode(callSuper = true)
class HabitationEntity extends AbstractEntity implements Habitation {
    private String id;
    private String hotelId;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = HabitationOccupiedEntity.class, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "hp_id")
    private Set occupied;
    private LocalDateTime checkIn;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = EmployeeEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "hp_check_in", joinColumns = @JoinColumn(name = "hp_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private EmployeeEntity checkedInBy;

    private LocalDateTime checkOut;
    @OneToOne(fetch = FetchType.EAGER, targetEntity = EmployeeEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "hp_check_out", joinColumns = @JoinColumn(name = "hp_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private EmployeeEntity checkOutBy;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = ServiceProvidedEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hp_id")
    private Set services;
    private BigDecimal totalFee;
}
