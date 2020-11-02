package foundly.restapi.service;

import foundly.core.model.Item;
import foundly.restapi.exception.ItemNotFoundException;
import foundly.restapi.persistence.ItemRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ItemServiceImpl. Implementation of interface ItemService. This class handles all
 * requests for Item-objects. ItemServiceImpl will read, write, delete and update Items stored in
 * the item repository.
 */
@Service
public class ItemServiceImpl implements ItemService {

  @Autowired
  ItemRepository itemRepository;

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
  public void insertItem(Item item) {
    itemRepository.save(new Item(item.getTitle(), item.getDescription(), item.getState(),
        item.getEmail(), item.getPhone(), item.getImage(), item.getDate()));
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
    Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
    itemRepository.delete(item);
  }

  /**
   * Updates an item in the item repository by it's id. Throws an ItemNotFoundException if the item
   * can't be found.
   *
   * @param id the id
   * @param item the item
   * @throws ItemNotFoundException signals that an item with the given id can't be found
   */
  @Override
  public void updateItem(Long id, Item item) {
    Item itemData = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));

    itemData.setTitle(item.getTitle());
    itemData.setDescription(item.getDescription());
    itemData.setState(item.getState());
    itemData.setEmail(item.getEmail());
    itemData.setPhone(item.getPhone());
    itemData.setImage(item.getImage());
    itemRepository.save(itemData);
  }

}
