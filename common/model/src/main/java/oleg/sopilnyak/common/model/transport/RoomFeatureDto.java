package oleg.sopilnyak.common.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.Room;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(of = {"code", "name", "description", "dailyCost", "totalCost"})
@ToString(of = {"code", "name", "description", "dailyCost", "totalCost"})
public class RoomFeatureDto implements Room.Feature, Serializable {

    private static final long serialVersionUID = 6072242137521404130L;
    private String code;
    private String name;
    private String description;
    private BigDecimal dailyCost;
    private BigDecimal totalCost;
}
