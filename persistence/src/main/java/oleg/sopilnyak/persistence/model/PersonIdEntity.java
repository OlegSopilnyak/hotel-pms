package oleg.sopilnyak.persistence.model;

import lombok.Data;
import oleg.sopilnyak.common.model.business.Person;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Persistence Model type of person document
 */
@Data
@Embeddable
public class PersonIdEntity implements Person.ID, Serializable{
    @Column(name = "document_citizenship")
    private String citizenship;
    @Column(name = "document_type")
    private String type;
    @Column(name = "document_number")
    private String number;
    @Column(name = "document_expired")
    private LocalDate expired;
}
