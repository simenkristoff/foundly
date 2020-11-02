package foundly.restapi.presentation;

import foundly.core.model.Item;
import foundly.core.model.ResponseMessage;
import foundly.restapi.persistence.ItemRepository;
import foundly.restapi.service.FileStorageService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * The Class ApiController. Handles all endpoints requested to the rest-api.
 */
@RestController()
@RequestMapping("/api")
public class ApiController {

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  FileStorageService storageService;

  private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

  /**
   * Gets all Items from the Item-repository and returns them.
   *
   * @return all items
   */
  @GetMapping("/items")
  public List<Item> getAllItems() {
    List<Item> items = new ArrayList<>();
    itemRepository.findAll().forEach(items::add);
    return items;
  }

  /**
   * Gets an Item from the Item-repository with an id corresponding to the id-parameter. Throws an
   * exception if the id doesn't exist.
   *
   * @param id the id
   * @return the item
   * @throws IllegalArgumentException the illegal argument exception
   */
  @GetMapping("/items/{id}")
  public ResponseEntity<Item> getItem(@PathVariable Long id) throws IllegalArgumentException {
    Item item = itemRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Item could not be found for id: " + id));
    return ResponseEntity.ok().body(item);
  }

  /**
   * Add an Item to the Item-repository.
   *
   * @param item the item
   * @return the item
   */
  @PostMapping("/items")
  public Item postItem(@RequestBody Item item) {
    Item updatedItem = itemRepository.save(new Item(item.getTitle(), item.getDescription(),
        item.getState(), item.getEmail(), item.getPhone(), item.getImage(), item.getDate()));
    return updatedItem;
  }

  /**
   * Delete an Item with id corresponding to the id-parameter. Throws an exception if the id doesn't
   * exist.
   *
   * @param id the id
   * @return the response
   * @throws IllegalArgumentException the illegal argument exception
   */
  @DeleteMapping("/items/{id}")
  public ResponseEntity<Item> deleteItem(@PathVariable("id") long id)
      throws IllegalArgumentException {
    System.out.println("Delete Item with ID = " + id + "...");

    Item item = itemRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Item could not be found for id: " + id));

    itemRepository.delete(item);

    return ResponseEntity.ok().body(item);
  }

  /**
   * Update an Item in the Item-repository. The new Item will override the old Item with same id as
   * the id-parameter in the Item-repository. Throws an exception if the id doesn't exist.
   *
   * @param id the id
   * @param item the item
   * @return the response
   * @throws IllegalArgumentException the illegal argument exception
   */
  @PutMapping("/items/{id}")
  public ResponseEntity<Item> updateItem(@PathVariable("id") long id, @RequestBody Item item)
      throws IllegalArgumentException {
    Item itemData = itemRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Item could not be found for id: " + id));

    itemData.setTitle(item.getTitle());
    itemData.setDescription(item.getDescription());
    itemData.setState(item.getState());
    itemData.setEmail(item.getEmail());
    itemData.setPhone(item.getPhone());
    itemData.setImage(item.getImage());
    final Item updatedItem = itemRepository.save(itemData);
    return ResponseEntity.ok(updatedItem);
  }

  /**
   * Uploads a file to the file storage directory.
   *
   * @param file the file to be uploaded
   * @return the response
   */
  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.save(file);

      message = "Uploaded the file successfully: " + file.getOriginalFilename();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseMessage(message));
    }
  }

  /**
   * Redirect all URls to the web-client frontend except index.html and 'api/..'.
   *
   * @return forward string
   */
  @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
  public String redirectApi() {
    LOG.info("Redirecting to the frontend..");
    return "forward:/";
  }

}
