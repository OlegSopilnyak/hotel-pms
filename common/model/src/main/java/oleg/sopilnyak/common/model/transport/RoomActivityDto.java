package oleg.sopilnyak.common.model.transport;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import oleg.sopilnyak.common.model.business.Room;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Transport Model type for manage hotel room actions
 */
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"type", "createdAt"})
public class RoomActivityDto implements Room.Activity, Serializable{
    private String id;
    private Type type;
    private String roomId;
    private String hotelAgreementId;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

}
