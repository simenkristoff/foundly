package foundly.restapi.persistence;

import org.springframework.data.repository.CrudRepository;

import foundly.core.model.Item;
 
public interface ItemRepository extends CrudRepository<Item, Long> {

}