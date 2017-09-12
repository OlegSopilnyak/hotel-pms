package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Room;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Persistence Model type of room features
 */
@Data
@Entity
@Table(name = "room_feature")
@EqualsAndHashCode(callSuper = true)
public class RoomFeatureEntity extends AbstractEntity implements Room.Feature {
    private String code;
    private String name;
    private String description;
    private BigDecimal dailyCost;
    private BigDecimal totalCost;
}
