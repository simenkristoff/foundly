package foundly.core.dataaccess;

import com.fasterxml.jackson.databind.ObjectMapper;
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

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * Instantiates a new ImageDataAccess-object.
   *
   * @param baseUrlString the url to the rest-api
   */
  public ImageDataAccessObject(final String baseUrlString) {
    this.baseUrlString = baseUrlString + "/api/upload";
    this.objectMapper = new ObjectMapper();
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
   * Upload. Sends a http-request to the rest-api with an image-file as content.
   *
   * @param file the image to be uploaded
   */
  @Override
  public void upload(File file) {
    Resource resource = new FileSystemResource(file);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", resource);
    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<String> response =
        restTemplate.postForEntity(this.baseUrlString, requestEntity, String.class);
    System.out.println("Response code: " + response.getStatusCode());
  }

}
