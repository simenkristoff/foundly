package foundly.restapi.persistence;

import foundly.core.model.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * The Interface ItemRepository. Extension of CrudRepository which is managed by Springboot. Allows
 * for CREATE, READ, UPDATE, and DELETE operations with regards to the H2-database.
 */
public interface ItemRepository extends CrudRepository<Item, Long> {

}
