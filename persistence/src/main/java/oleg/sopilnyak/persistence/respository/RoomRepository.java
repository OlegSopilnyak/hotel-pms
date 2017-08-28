package oleg.sopilnyak.persistence.respository;

import oleg.sopilnyak.persistence.model.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Persistence Model type to manage rooms
 */
public interface RoomRepository extends JpaRepository<RoomEntity,Long> {
    /**
     * To get free rooms
     * @param roomIds ids of reserved rooms
     * @return list
     */
    List<RoomEntity> findByIdNotIn(List<String> roomIds);
}
