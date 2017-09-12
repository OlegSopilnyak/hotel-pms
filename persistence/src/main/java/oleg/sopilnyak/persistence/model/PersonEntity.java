package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Persistence Model type for any person
 */
@Data
@Entity
@Table(name = "person_table")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
@EqualsAndHashCode(callSuper = true)
class PersonEntity extends AbstractEntity implements Person, Serializable {
    @Column(name = "person_id", unique = true)
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthday;
    private Gender gender;
    @Embedded
    private PersonIdEntity document = new PersonIdEntity();
    @Embedded
    private PhotoEntity photo = new PhotoEntity();

    private String language;
    private String phone;
    private String email;
    private String about;
    private String tags;
}
