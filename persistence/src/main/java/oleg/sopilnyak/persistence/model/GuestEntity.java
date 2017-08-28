package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Guest;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Persistence Model Type for Guests
 */
@Data
@Entity
@DiscriminatorValue("Guest")
@EqualsAndHashCode(callSuper = true)
public class GuestEntity extends PersonEntity implements Guest, Serializable {
    @Column(name = "login")
    private String login;
    @Column(name = "position_title")
    private String title;
    @Column(name = "is_active")
    private Boolean livesHere;
    @Column(name = "start_date")
    private LocalDate loginActiveFrom;
    @Column(name = "end_date")
    private LocalDate loginActiveTo;

}
