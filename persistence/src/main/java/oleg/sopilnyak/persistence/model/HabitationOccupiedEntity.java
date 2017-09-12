package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Habitation;

import javax.persistence.*;
import java.util.Set;

/**
 * Persistence Model Type for occupied rooms
 */
@Data
@Entity
@Table(name = "room_occupied")
@EqualsAndHashCode(callSuper = true)
public class HabitationOccupiedEntity extends AbstractEntity implements Habitation.OccupiedRoom{
    @OneToOne(fetch = FetchType.EAGER, targetEntity = RoomEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "ho_room", joinColumns = @JoinColumn(name = "ho_id"), inverseJoinColumns = @JoinColumn(name = "room_id"))
    private RoomEntity room;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = GuestEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "ho_guests", joinColumns = @JoinColumn(name = "ho_id"), inverseJoinColumns = @JoinColumn(name = "guest_id"))
    private Set guests;
    //        private Set<RoomDto.FeatureDto> activeFeatures = new LinkedHashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoomFeatureEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "ho_features", joinColumns = @JoinColumn(name = "ho_id"), inverseJoinColumns = @JoinColumn(name = "feature_id"))
    private Set activeFeatures;
}
