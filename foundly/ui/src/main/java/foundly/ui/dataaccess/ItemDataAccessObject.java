package foundly.ui.dataaccess;

import foundly.core.model.Item;
import foundly.ui.App;
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

  private String servicePath = "/api/items";

  private RestTemplate restTemplate;

  /**
   * Instantiates a new ItemDataAccess-object. This instance will build a URL String based on the
   * settings in the configuration file.
   *
   */
  public ItemDataAccessObject() {
    this.baseUrlString = buildBaseUrl();
    this.restTemplate = new RestTemplate();
  }

  /**
   * Instantiates a new ItemDataAccess-object with a predefined URL String.
   *
   * @param baseUrlString the URL to the REST Api
   */
  public ItemDataAccessObject(String baseUrlString) {
    this.baseUrlString = baseUrlString + servicePath;
    this.restTemplate = new RestTemplate();
  }

  /**
   * Gets the restTemplate-client from Spring.
   *
   * @return the restTemplate
   */
  public RestTemplate getRestTemplate() {
    return restTemplate;
  }

  /**
   * Builds the URL to REST Api based on settings in the configuration file.
   * 
   * @return String baseUrlString
   */
  private String buildBaseUrl() {
    StringBuilder sb = new StringBuilder();
    sb.append(App.getProperty("api.protocol") + "://");
    sb.append(App.getProperty("api.hostname") + ":");
    sb.append(App.getProperty("api.port"));
    sb.append(servicePath);
    return sb.toString();
  }

  /**
   * Gets the request URL.
   *
   * @param path the path
   * @return the request URL
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
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      ResponseEntity<Item[]> response =
          getRestTemplate().getForEntity(getRequestUrl(""), Item[].class);

      System.out.println(response.getStatusCode());

      if (response.getStatusCode() == HttpStatus.OK) {
        return Arrays.asList(response.getBody());
      }

    } catch (NullPointerException e) {
      System.err.println("[WARNING] Could not find requested item \n" + e.getMessage());
    } catch (Exception e) {
      System.err.println("[WARNING] An error occured during the request \n" + e.getMessage());
    }

    return Collections.emptyList();
  }

  /**
   * Gets an Item. Sends a GET-request to rest-api requesting an Item-object in the database by it's
   * id.
   *
   * @param id the id
   * @return the item
   */
  public Item get(long id) {
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      ResponseEntity<Item> response =
          getRestTemplate().getForEntity(getRequestUrl("/" + id), Item.class);

      System.out.println(response.getStatusCode());

      if (response.getStatusCode() == HttpStatus.OK) {
        return response.getBody();
      }

    } catch (NullPointerException e) {
      System.err.println("[WARNING] Could not find requested item \n" + e.getMessage());
    } catch (Exception e) {
      System.err.println("[WARNING] An error occured during the request \n" + e.getMessage());
    }

    return null;
  }

  /**
   * Delete an Item. Sends a DELETE-request to the rest-api requesting a DELETE for the Item with an
   * id corresponding to the id-parameter.
   *
   * @param id the id
   */
  public void delete(long id) {
    try {
      HttpHeaders headers = new HttpHeaders();
      Map<String, Long> params = new HashMap<String, Long>();
      params.put("id", id);
      headers.setContentType(MediaType.APPLICATION_JSON);
      getRestTemplate().delete(getRequestUrl("/" + id), params);

    } catch (Exception e) {
      System.err.println("[WARNING] An error occured during the request \n" + e.getMessage());
    }

  }

  /**
   * Insert a new Item. Sends a POST-request to the rest-api requesting to POST a new Item in the
   * database.
   *
   * @param item the item
   * @return true, if successful
   */
  public boolean insert(Item item) {
    try {
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

    } catch (Exception e) {
      System.err.println("[WARNING] An error occured during the request \n" + e.getMessage());
    }

    return false;
  }

}
