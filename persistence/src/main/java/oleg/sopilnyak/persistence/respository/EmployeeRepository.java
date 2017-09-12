package oleg.sopilnyak.persistence.respository;

import oleg.sopilnyak.persistence.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Persistence Model type to manage staff
 */
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    /**
     * Find employee by tag and active
     *
     * @param tag    tag of worker
     * @param active true anyway
     * @return list
     */
    List<EmployeeEntity> findByTagsLikeAndActive(String tag, Boolean active);
}
