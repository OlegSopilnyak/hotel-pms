package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.CreditCard;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDate;

/**
 * Persistence Model type for credit-cards
 */
@Data
@Embeddable
@EqualsAndHashCode
class CreditCardEntity implements CreditCard {
    @Column(name = "creditcard_id")
    private String id;
    @Column(name = "creditcard_number")
    private String number;
    @Column(name = "creditcard_expired")
    private LocalDate expiredFrom;
    @Column(name = "creditcard_owner")
    private String owner;
}
