package oleg.sopilnyak.common.model.transport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import oleg.sopilnyak.common.model.business.Guest;
import oleg.sopilnyak.common.model.business.Person;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Transport Model root type of people involved in
 */
@Data
@EqualsAndHashCode(of = {"id", "firstName", "middleName", "lastName", "birthday", "gender"})
@ToString(exclude = {"id", "document"})
public abstract class PersonDto implements Person {

    private static final long serialVersionUID = 2217349020891183596L;
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private Gender gender;
    private PersonDto.ID document = new PersonDto.ID();
    private PhotoDto photo = new PhotoDto();
    private String language;
    private String phone;
    private String email;
    private String about;
    private String tags;

    // inner classes
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ID implements Guest.ID, Serializable {
        private static final long serialVersionUID = 7693233288442675035L;
        private String citizenship;
        private String type;
        private String number;
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate expired;
    }
}
