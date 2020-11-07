package foundly.restapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import foundly.core.model.Item;
import foundly.core.model.Item.State;
import foundly.restapi.exception.ItemNotFoundException;
import foundly.restapi.persistence.ItemRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The Class ItemServiceImplTest
 */
@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

  @Mock
  private ItemRepository itemRepository;

  @InjectMocks
  private ItemServiceImpl itemServiceImpl;

  /**
   * Test that item service returns all items in the repository
   */
  @Test
  public void getAllItemsTest() {
    List<Item> items = new ArrayList<Item>();
    items.add(new Item("Test1", "Test1", State.FOUND, "test@test.no", "12345678", "default.png"));
    items.add(new Item("Test2", "Test2", State.FOUND, "test@test.no", "12345678", "default.png"));
    items.add(new Item("Test3", "Test3", State.FOUND, "test@test.no", "12345678", "default.png"));
    items.add(new Item("Test4", "Test4", State.FOUND, "test@test.no", "12345678", "default.png"));

    given(this.itemRepository.findAll()).willReturn(items);

    List<Item> expected = this.itemServiceImpl.getAllItems();

    assertEquals(expected, items);
  }

  /**
   * Test getting an item from repository by the id
   */
  @Test
  public void getItemByIdTest() {
    final Long id = 1L;
    final Long unvalidId = 2L;
    Item item =
        new Item("Test1", "Test1", State.FOUND, "test@test.no", "12345678", "default.png");

    given(this.itemRepository.findById(id)).willReturn(Optional.of(item));

    final Item expected = this.itemServiceImpl.getItemById(id);

    assertEquals(expected.getTitle(), item.getTitle());

    given(this.itemRepository.findById(unvalidId)).willThrow(new IllegalArgumentException());
    
    assertThrows(IllegalArgumentException.class, () -> {
      this.itemServiceImpl.getItemById(unvalidId);
    });

  }

  /**
   * Insert item test.
   */
  @Test
  public void insertItemTest() {
    final Item item = new Item("Test1", "Test1", State.FOUND, "test@test.no", "12345678",
        "default.png", LocalDateTime.now());

    given(this.itemRepository.save(item)).willReturn(item);

    Item inserted = this.itemServiceImpl.insertItem(item);

    assertEquals(item.getTitle(), inserted.getTitle());
  }

  /**
   * Delete item test.
   */
  @Test
  public void deleteItemTest() {
    final long Id = 0L;

    final Item item = new Item("Test1", "Test1", State.FOUND, "test@test.no", "12345678",
        "default.png", LocalDateTime.now());

    this.itemServiceImpl.insertItem(item);

    this.itemServiceImpl.deleteItem(Id);

    verify(this.itemRepository).deleteById(Id);
  }
 
}
