package oleg.sopilnyak.persistence.respository;

import oleg.sopilnyak.persistence.model.RoomFeatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Persistence Model repository to manage room features
 */
public interface RoomFeatureRepository extends JpaRepository<RoomFeatureEntity, Long> {
}
