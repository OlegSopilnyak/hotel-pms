package oleg.sopilnyak.persistence.respository;

import oleg.sopilnyak.persistence.model.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Persistence Model type for hotels
 */
public interface HotelRepository extends JpaRepository<HotelEntity,Long>{
}
