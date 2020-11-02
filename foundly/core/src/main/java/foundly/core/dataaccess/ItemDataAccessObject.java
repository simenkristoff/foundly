package foundly.core.dataaccess;

import foundly.core.model.Item;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * The Class ItemDataAccessObject. Implementation of interface ItemDataAccess. This class is used to
 * send requests to the rest-api for inserting, deleting and updating Items in the database.
 */
public class ItemDataAccessObject implements ItemDataAccess {

  private final String baseUrlString;

  private RestTemplate restTemplate;

  /**
   * Instantiates a new ItemDataAccess-object.
   *
   * @param baseUrlString the url to the rest-api
   */
  public ItemDataAccessObject(final String baseUrlString) {
    this.baseUrlString = baseUrlString + "/api/items";
    this.restTemplate = new RestTemplate();
  }

  /**
   * Gets the restTemplate-client from Spring.
   *
   * @return the restTemplate
   */
  protected RestTemplate getRestTemplate() {
    return restTemplate;
  }

  /**
   * Gets the request url.
   *
   * @param path the path
   * @return the request url
   */
  private String getRequestUrl(final String path) {
    return baseUrlString + path;
  }

  /**
   * Get all Items. Sends a GET-request to rest-api requesting all Item-objects in the database.
   *
   * @return the items
   */
  public List<Item> getAll() {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    ResponseEntity<Item[]> response =
        getRestTemplate().getForEntity(getRequestUrl(""), Item[].class);

    System.out.println(response.getStatusCode());

    if (response.getStatusCode() == HttpStatus.OK) {
      try {
        return Arrays.asList(response.getBody());
      } catch (NullPointerException e) {
        System.out.println("Error while fetching all items from rest-Server");
        return null;
      }

    }

    return null;
  }

  /**
   * Gets an Item. Sends a GET-request to rest-api requesting an Item-object in the database by it's
   * id.
   *
   * @param id the id
   * @return the item
   */
  public Item get(long id) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    ResponseEntity<Item> response =
        getRestTemplate().getForEntity(getRequestUrl("/" + id), Item.class);

    System.out.println(response.getStatusCode());

    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    }
    return null;
  }

  /**
   * Update an Item. Sends a POST-request to rest-api requesting a POST for an Item in the database.
   * The Item will override the already stored Item in the database corresponding to the
   * id-parameter.
   *
   * @param id the id
   * @param item the updated item
   */
  public void update(long id, Item item) {
    HttpHeaders headers = new HttpHeaders();
    Map<String, Long> params = new HashMap<String, Long>();
    params.put("id", id);
    headers.setContentType(MediaType.APPLICATION_JSON);
    getRestTemplate().put(getRequestUrl("/" + id), item, params);
  }

  /**
   * Delete an Item. Sends a DELETE-request to the rest-api requesting a DELETE for the Item with an
   * id corresponding to the id-parameter.
   *
   * @param id the id
   */
  public void delete(long id) {
    HttpHeaders headers = new HttpHeaders();
    Map<String, Long> params = new HashMap<String, Long>();
    params.put("id", id);
    headers.setContentType(MediaType.APPLICATION_JSON);
    getRestTemplate().delete(getRequestUrl("/" + id), params);
  }

  /**
   * Insert a new Item. Sends a POST-request to the rest-api requesting to POST a new Item in the
   * database.
   *
   * @param item the item
   * @return true, if successful
   */
  public boolean insert(Item item) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<Item> entity = new HttpEntity<>(item, headers);
    ResponseEntity<String> response =
        this.getRestTemplate().postForEntity(this.getRequestUrl(""), entity, String.class);

    System.out.println(response.getStatusCode());

    if (response.getStatusCode() == HttpStatus.OK) {
      return true;
    }
    return false;
  }

}
