package oleg.sopilnyak.common.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.ConfirmReservation;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Transport Model type for manage confirmed rooms
 */
@Data
@EqualsAndHashCode
@ToString
public class ConfirmedRoomDto implements ConfirmReservation.BookedRoom, Serializable {
    private static final long serialVersionUID = -4772686139997628931L;
    private String roomCode;
    private Set<String> guestIds = new LinkedHashSet<>();
    private Set<String> featureIds = new LinkedHashSet<>();
}
