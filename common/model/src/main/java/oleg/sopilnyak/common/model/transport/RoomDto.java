package oleg.sopilnyak.common.model.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import oleg.sopilnyak.common.model.business.Room;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Transport Model type for manage hotel rooms
 */
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "capacity", "type", "floor"})
@JsonRootName(value = "room")
public class RoomDto implements Room {
    private static final long serialVersionUID = 5685278283101545165L;
    private String id;
    private Integer capacity;
    private Type type;
    private Integer floor;
    private Integer windows;
    private BigDecimal dailyCost;

    @JsonProperty(value = "availableFeatures")
    @JsonDeserialize(using = FeaturesSetDeserializer.class)
    @Getter(AccessLevel.NONE)
    private Set availableFeatures = new LinkedHashSet<>();
//    private Set<RoomFeatureDto> availableFeatures = new LinkedHashSet<>();

    public Set<Room.Feature>getAvailableFeatures(){
        return availableFeatures;
    }
    // inner classes
    private static class FeaturesSetDeserializer extends JsonDeserializer<Set<RoomFeatureDto>> {
        @Override
        public Set<RoomFeatureDto> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonParser);
            return mapper.convertValue(node, new TypeReference<Set<RoomFeatureDto>>() {});
        }
    }

}
