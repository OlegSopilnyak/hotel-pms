package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Room;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Persistence Model type for rooms
 */
@Data
@Entity
@Table(name = "rooms")
@EqualsAndHashCode(callSuper = true)
public class RoomEntity extends AbstractEntity implements Room {
    private String id;
    private Integer capacity;
    private Type type;
    private Integer floor;
    private Integer windows;
    private BigDecimal dailyCost;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoomFeatureEntity.class, cascade = CascadeType.ALL)
    @JoinTable(name = "rooms_features",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set availableFeatures = new LinkedHashSet<>();

}
