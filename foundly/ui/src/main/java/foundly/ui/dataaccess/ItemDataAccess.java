package foundly.ui.dataaccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import foundly.core.model.Item;
import java.util.Collection;

/**
 * The Interface ItemDataAccess. Handles requests to rest-api for getting, deleting and updating
 * Items.
 */
public interface ItemDataAccess {

  /**
   * Get all Items.
   *
   * @return the all
   */
  Collection<Item> getAll();

  /**
   * Gets an Item by it's id.
   *
   * @param id the id
   * @return the item
   */
  Item get(long id);

  /**
   * Delete an Item.
   *
   * @param id the id
   */
  void delete(long id);

  /**
   * Insert an Item.
   *
   * @param item the item
   * @return true, if successful
   * @throws JsonProcessingException the json processing exception
   */
  boolean insert(Item item);

}
