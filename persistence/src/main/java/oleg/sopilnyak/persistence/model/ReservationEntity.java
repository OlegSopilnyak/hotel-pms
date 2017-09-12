package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Reservation;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Persistence Model type for reservation storage
 */
@Data
@Entity
@Table(name = "reservation_part")
@EqualsAndHashCode(callSuper = true)
public class ReservationEntity extends AbstractEntity implements Reservation {
    private String id;
    @Column(name = "start_period")
    private LocalDate from;
    @Column(name = "end_period")
    private LocalDate to;
    @Column(name = "reserv_state")
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToOne(fetch = FetchType.EAGER, targetEntity = EmployeeEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "reservations_employees",
            joinColumns = @JoinColumn(name = "reservation_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private EmployeeEntity reservedBy;
}
