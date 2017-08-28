package oleg.sopilnyak.persistence.respository;

import oleg.sopilnyak.persistence.model.HotelAgreementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository to manage hotel agreement entities
 */
public interface HotelAgreementRepository extends JpaRepository<HotelAgreementEntity, Long>{
    /**
     * To get agreement by non-persistent id
     *
     * @param id string id
     * @return entity or null if not exists
     */
    HotelAgreementEntity getById(String id);

    /**
     * Get agreements created between borders
     *
     * @param from start of range
     * @param to end of range
     * @return list of agreements
     */
    List<HotelAgreementEntity> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);

    /**
     * Find agreements with appropriate ids
     *
     * @param agreementIds list of ids
     * @return list of agreements
     */
    List<HotelAgreementEntity> findByIdIn(List<String> agreementIds);
}
