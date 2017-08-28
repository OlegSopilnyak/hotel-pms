package oleg.sopilnyak.persistence.respository;

import oleg.sopilnyak.common.model.business.Person;
import oleg.sopilnyak.persistence.model.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository to manage guest types
 */
public interface GuestRepository extends JpaRepository<GuestEntity,Long>{

    /**
     * To get the guest by document
     * @param document person document
     * @return guest if exists of null
     */
    GuestEntity getByDocument(Person.ID document);

    /**
     * To get guest by id
     * @param id id of the guest
     * @return instance or null if not found
     */
    GuestEntity getById(String id);
}
