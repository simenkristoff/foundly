package foundly.ui.dataaccess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

/**
 * Tests for the Class ImageDataAccessObjectTest.
 */
@SpringBootTest(classes = SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ImageDataAccessObjectTest {

  private RestTemplate restTemplate;
  private ImageDataAccessObject imageDao;
  private MockRestServiceServer mockServer;

  private File file;

  /**
   * Setup before each test.
   */
  @BeforeEach
  public void setup() {
    imageDao = new ImageDataAccessObject("http://localhost:8098");
    restTemplate = imageDao.getRestTemplate();
    mockServer = MockRestServiceServer.createServer(restTemplate);

  }

  /**
   * Test getter for the object mapper.
   */
  @Test
  public void getObjectMapperTest() {
    assertThat(imageDao.getObjectMapper()).isNotNull();
  }

  /**
   * Test if upload() method sends the correct request.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws URISyntaxException the URI syntax exception
   */
  @Test
  @DisplayName("Testing file upload request")
  public void uploadTest() throws IOException, URISyntaxException {
    mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8098/api/upload")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess("{message : 'Image uploaded!'}", MediaType.MULTIPART_FORM_DATA));

    file = new File(getClass().getResource("default.png").getPath());
    imageDao.upload(file);
    mockServer.verify();
  }
}
