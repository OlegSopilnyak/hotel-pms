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
import lombok.*;
import oleg.sopilnyak.common.model.business.Guest;
import oleg.sopilnyak.common.model.business.PreBookingResult;
import oleg.sopilnyak.common.model.business.Room;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Transport Model type for manage credit booking result phase 1
 */
@Data
@EqualsAndHashCode(of = {"hotelAgreementId"})
@ToString(of = {"hotelAgreementId"})
public class PreBookingResultDto implements PreBookingResult {
    private String hotelAgreementId;

    @JsonProperty(value = "dedicatedRoom")
    @JsonDeserialize(using = RoomSetDeserializer.class)
    @Getter(AccessLevel.NONE)
    private Set dedicatedRoom = new LinkedHashSet();

    @JsonProperty(value = "guests")
    @JsonDeserialize(using = GuestSetDeserializer.class)
    @Getter(AccessLevel.NONE)
    private Set guests = new LinkedHashSet();
//    private Set<RoomDto> dedicatedRoom;
//    private Set<GuestDto> guests;

    public Set<Room> getDedicatedRoom(){
        return dedicatedRoom;
    }

    @Override
    public Set<Guest> getGuests() {
        return guests;
    }

    // inner classes
    private static class RoomSetDeserializer extends JsonDeserializer<Set<RoomDto>> {
        @Override
        public Set<RoomDto> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonParser);
            return mapper.convertValue(node, new TypeReference<Set<RoomDto>>() {});
        }
    }
    private static class GuestSetDeserializer extends JsonDeserializer<Set<GuestDto>> {
        @Override
        public Set<GuestDto> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonParser);
            return mapper.convertValue(node, new TypeReference<Set<GuestDto>>() {});
        }
    }
}
