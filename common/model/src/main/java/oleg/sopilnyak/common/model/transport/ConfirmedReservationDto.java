package oleg.sopilnyak.common.model.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.ConfirmReservation;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Transport Model type for manage confirmed reservation
 */
@Data
@EqualsAndHashCode(exclude = {"creditCard"})
@ToString(exclude = {"creditCard"})
public class ConfirmedReservationDto implements ConfirmReservation {
    private String agreementId;
    private CreditCardDto creditCard = new CreditCardDto();
    @JsonProperty(value = "dedicatedRoom")
    @JsonDeserialize(using = ConfirmedRoomSetDeserializer.class)
    private Set bookedRooms = new LinkedHashSet();

    private static class ConfirmedRoomSetDeserializer extends JsonDeserializer<Set<ConfirmedRoomDto>> {
        @Override
        public Set<ConfirmedRoomDto> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonParser);
            return mapper.convertValue(node, new TypeReference<Set<ConfirmedRoomDto>>() {});
        }
    }
}
