package foundly.ui.dataaccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundly.ui.App;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * The Class ImageDataAccessObject. Implementation of interface ImageDataAccess. This class is used
 * to send requests to the rest-api for uploading new images to the uploads-folder.
 */
public class ImageDataAccessObject implements ImageDataAccess {

  private final String baseUrlString;

  private String servicePath = "/api/upload";

  private RestTemplate restTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Instantiates a new ImageDataAccess-object. This instance will build a URL String based on the
   * settings in the configuration file.
   *
   */
  public ImageDataAccessObject() {
    this.baseUrlString = buildBaseUrl();
    this.objectMapper = new ObjectMapper();
    this.restTemplate = new RestTemplate();
  }

  /**
   * Instantiates a new ImageDataAccess-object with a predefined URL String.
   *
   * @param baseUrlString the URL to the REST Api
   */
  public ImageDataAccessObject(String baseUrlString) {
    this.baseUrlString = baseUrlString + servicePath;
    this.objectMapper = new ObjectMapper();
    this.restTemplate = new RestTemplate();
  }

  /**
   * Gets the object mapper.
   *
   * @return the object mapper
   */
  protected ObjectMapper getObjectMapper() {
    return objectMapper;
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
   * Upload. Sends a POST-request to the rest-api with an image-file as content.
   *
   * @param file the image to be uploaded
   */
  @Override
  public void upload(File file) {
    try {
      Resource resource = new FileSystemResource(file);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.MULTIPART_FORM_DATA);

      MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
      body.add("file", resource);
      HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

      ResponseEntity<String> response =
          restTemplate.postForEntity(this.baseUrlString, requestEntity, String.class);
      System.out.println("Response code: " + response.getStatusCode());
    } catch (Exception e) {
      System.err.println("[WARNING] An error occured during the request \n" + e.getMessage());
    }
  }

}
