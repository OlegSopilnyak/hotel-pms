package oleg.sopilnyak.common.model.transport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import oleg.sopilnyak.common.model.business.CreditCard;

import java.time.LocalDate;

/**
 * Transport Model type for manage credit cards of guests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
@ToString(of = {"owner"})
@Builder
public class CreditCardDto implements CreditCard{
    private static final long serialVersionUID = -3601903113462003673L;
    private String id;
    private String number;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate expiredFrom;
    private String owner;
}
