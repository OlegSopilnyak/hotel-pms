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
import oleg.sopilnyak.common.model.business.Room;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * Transport Model Type for init rooms reservation process
 */
@Data
public class InitBookingRequest implements Serializable {
    private static final long serialVersionUID = -7353605085868955331L;
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
    @Getter(AccessLevel.NONE)
//    private Set<RoomFeatureDto> features;
    private Set features;

    public Set<Guest> getGuests(){
        return guests;
    }
    public Set<Room.Feature> getFeatures(){
        return features;
    }
}
