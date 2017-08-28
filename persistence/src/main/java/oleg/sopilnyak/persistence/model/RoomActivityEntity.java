package oleg.sopilnyak.persistence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import oleg.sopilnyak.common.model.business.Room;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Persistence Model type for room booking activity
 */
@Data
@Entity
@Table(name = "room_schedule")
@EqualsAndHashCode(callSuper = false)
public class RoomActivityEntity extends AbstractEntity implements Room.Activity {
    private String id;
    private Type type;
    private String roomId;
    private String hotelAgreementId;
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;

}
