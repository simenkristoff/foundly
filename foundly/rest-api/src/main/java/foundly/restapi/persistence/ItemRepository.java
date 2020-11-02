package foundly.restapi.persistence;

import foundly.core.model.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface ItemRepository. Extension of CrudRepository which is managed by Springboot. Allows
 * for CREATE, READ, UPDATE, and DELETE operations with regards to the H2-database.
 */
@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
