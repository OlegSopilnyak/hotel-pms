package oleg.sopilnyak.rest.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import oleg.sopilnyak.common.model.business.Guest;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * Transport Model Type for change confirmed reservation
 */
@Data
public class ChangeBookingRequest implements Serializable {
    private static final long serialVersionUID = 7087054892291737411L;
    private String id;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate from;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate to;
    @Getter(AccessLevel.NONE)
//    private Set<GuestDto> guests;
    private Set guests;
    private int rooms;

    @SuppressWarnings("unchecked")
    public Set<Guest> getGuests(){
        return guests;
    }
}
