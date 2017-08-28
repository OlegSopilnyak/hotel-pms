package oleg.sopilnyak.persistence.respository;

import oleg.sopilnyak.common.model.business.Room;
import oleg.sopilnyak.persistence.model.RoomActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Persistence Model repository to manage room activities related to reservation
 */
public interface RoomActivityRepository extends JpaRepository<RoomActivityEntity, Long> {

    /**
     * Delete all activities related to agreement
     * @param id agreement id
     */
    void deleteByHotelAgreementId(String id);

    /**
     * To get schedule to period
     *
     * @param from start period
     * @param to end period
     * @return list
     */
    List<RoomActivityEntity> findByStartedAtBetween(LocalDateTime from, LocalDateTime to);

    /**
     * Delete expired dedicated room activities
     *
     * @param mark time mark
     * @param dedicate type of activity to delete
     */
    void deleteByStartedAtBeforeAndType(LocalDateTime mark, Room.Activity.Type dedicate);

    RoomActivityEntity getById(String id);
}
