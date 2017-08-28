package oleg.sopilnyak.persistence.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import oleg.sopilnyak.common.model.business.Guest;
import oleg.sopilnyak.common.model.business.HotelAgreement;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Persistence Model type for reservation agreement
 */
@Data
@Entity
@Table(name = "agreement")
@EqualsAndHashCode(callSuper = false)
public class HotelAgreementEntity extends AbstractEntity implements HotelAgreement{
    private String id;
    private LocalDateTime createdAt;
    @OneToOne(fetch = FetchType.EAGER, targetEntity = ReservationEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;
    @OneToOne(fetch = FetchType.EAGER, targetEntity = HabitationEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "habitation_id")
    private HabitationEntity habitation;
    @Embedded
    private CreditCardEntity card;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = GuestEntity.class, cascade = CascadeType.ALL)
    @JoinTable(name = "agreements_guests", joinColumns = @JoinColumn(name = "agreement_id"), inverseJoinColumns = @JoinColumn(name = "guest_id"))
    @Getter(AccessLevel.NONE)
    private Set guestSet;

    public Set<Guest> getGuestSet(){
        return guestSet;
    }
}
