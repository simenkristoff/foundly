package foundly.restapi.service;

import foundly.core.model.Item;
import java.util.List;

/**
 * The Interface ItemService. Interface for handling requests for Item-objects.
 */
public interface ItemService {

  /**
   * Get all items.
   *
   * @return the all items
   */
  public List<Item> getAllItems();
  
  /**
   * Get an item by id.
   *
   * @param id the id
   * @return the item by id
   */
  public Item getItemById(Long id);
  
  /**
   * Insert an item.
   *
   * @param item the item
   */
  public void insertItem(Item item);
  
  /**
   * Delete an item by id.
   *
   * @param id the id
   */
  public void deleteItem(Long id);
  
  /**
   * Update an item by id.
   *
   * @param id the id
   * @param item the item to be updated
   */
  public void updateItem(Long id, Item item);
}
