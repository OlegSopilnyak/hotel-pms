package oleg.sopilnyak.common.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.Habitation;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"room", "guests"})
@ToString(of = {"room", "guests", "activeFeatures"})
public class HabitationOccupiedDto implements Habitation.OccupiedRoom, Serializable {
    private static final long serialVersionUID = -8414770450708653313L;
    private RoomDto room = new RoomDto();
    //        private Set<GuestDto> guests = new LinkedHashSet<>();
    private Set guests = new LinkedHashSet<>();
    //        private Set<RoomDto.FeatureDto> activeFeatures = new LinkedHashSet<>();
    private Set activeFeatures = new LinkedHashSet<>();
}
