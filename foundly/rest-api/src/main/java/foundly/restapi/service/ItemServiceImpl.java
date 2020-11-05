package foundly.restapi.service;

import foundly.core.model.Item;
import foundly.restapi.exception.ItemNotFoundException;
import foundly.restapi.persistence.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class ItemServiceImpl. Implementation of interface ItemService. This class handles all
 * requests for Item-objects. ItemServiceImpl will read, write, delete and update Items stored in
 * the item repository.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;
  
  @Autowired
  public ItemServiceImpl(ItemRepository itemRepository) {
      this.itemRepository = itemRepository;
  }

  /**
   * Gets all items from the item repository and returns them as a list.
   *
   * @return the all items
   */
  @Override
  public List<Item> getAllItems() {
    List<Item> items = new ArrayList<>();
    itemRepository.findAll().forEach(items::add);
    return items;
  }

  /**
   * Gets an item from the item repository by it's id. Throws an ItemNotFoundException if the item
   * can't be found.
   *
   * @param id the id
   * @return the item by id
   * @throws ItemNotFoundException signals that an item with the given id can't be found
   */
  @Override
  public Item getItemById(Long id) {
    Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    return item;
  }

  /**
   * Inserts an item into the item repository.
   *
   * @param item the item
   */
  @Override
  public Item insertItem(Item item) {
    return itemRepository.save(item);
  }

  /**
   * Deletes an item from the item repository by it's id. Throws an ItemNotFoundException if the
   * item can't be found.
   *
   * @param id the id
   * @throws ItemNotFoundException signals that an item with the given id can't be found
   */
  @Override
  public void deleteItem(Long id) {
    itemRepository.deleteById(id);
  }

}
