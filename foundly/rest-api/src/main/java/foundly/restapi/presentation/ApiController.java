package foundly.restapi.presentation;

import foundly.core.model.Item;
import foundly.core.model.ResponseMessage;
import foundly.restapi.service.FileStorageService;
import foundly.restapi.service.ItemService;
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
  ItemService itemService;

  @Autowired
  FileStorageService storageService;

  private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

  /**
   * Gets all Items from the Item-repository and returns them as a response.
   *
   * @return all items
   */
  @GetMapping("/items")
  public ResponseEntity<List<Item>> getAllItems() {
    ResponseEntity<List<Item>> response = ResponseEntity.ok().body(itemService.getAllItems());
    return response;
  }

  /**
   * Gets an Item from the Item-repository with an id corresponding to the id-parameter and returns
   * the Item as a response.
   *
   * @param id the id
   * @return the item
   */
  @GetMapping("/items/{id}")
  public ResponseEntity<Item> getItem(@PathVariable Long id) {
    Item item = itemService.getItemById(id);
    ResponseEntity<Item> response = ResponseEntity.ok().body(item);
    System.out.println(response.getStatusCode());
    return response;
  }

  /**
   * Adds an Item to the Item-repository and returns a response message.
   *
   * @param item the item
   * @return the response message
   */
  @PostMapping("/items")
  public ResponseEntity<ResponseMessage> postItem(@RequestBody Item item) {
    itemService.insertItem(item);
    String message = "Item inserted";
    ResponseEntity<ResponseMessage> response =
        ResponseEntity.ok().body(new ResponseMessage(message));
    return response;
  }

  /**
   * Delete an Item with id corresponding to the id-parameter and returns a response message.
   *
   * @param id the id
   * @return the response message
   */
  @DeleteMapping("/items/{id}")
  public ResponseEntity<ResponseMessage> deleteItem(@PathVariable("id") long id) {
    itemService.deleteItem(id);
    String message = "Item deleted at id = " + id;
    ResponseEntity<ResponseMessage> response =
        ResponseEntity.ok().body(new ResponseMessage(message));
    return response;
  }

  /**
   * Updates an Item in the Item-repository and returns a response message. The new Item will
   * override the old Item with same id as the id-parameter in the Item-repository.
   *
   * @param id the id
   * @param item the updated item
   * @return the response message
   */
  @PutMapping("/items/{id}")
  public ResponseEntity<ResponseMessage> updateItem(@PathVariable("id") long id,
      @RequestBody Item item) {
    itemService.updateItem(id, item);
    String message = "Item updated at id = " + id;
    ResponseEntity<ResponseMessage> response =
        ResponseEntity.ok().body(new ResponseMessage(message));
    return response;
  }

  /**
   * Uploads a file to the file storage directory.
   *
   * @param file the file to be uploaded
   * @return the response message
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
