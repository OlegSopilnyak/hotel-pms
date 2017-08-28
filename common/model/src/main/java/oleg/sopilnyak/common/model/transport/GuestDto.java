package oleg.sopilnyak.common.model.transport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.Guest;

import java.time.LocalDate;

/**
 * Transport Model type for manage hotel guests
 */
@Data
@EqualsAndHashCode(of = {"login", "title"}, callSuper = true)
@ToString(of = {"title"}, callSuper = true)
@JsonRootName(value = "guest")
public class GuestDto extends PersonDto implements Guest {
    private String login;
    private Boolean livesHere;
    private String title;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate loginActiveFrom;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate loginActiveTo;

}
