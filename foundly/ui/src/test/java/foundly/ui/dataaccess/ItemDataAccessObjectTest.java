package foundly.ui.dataaccess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import foundly.core.model.Item;
import foundly.core.model.Item.State;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

/**
 * Tests for the Class ItemDataAccessObject.
 */
@SpringBootTest(classes = SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ItemDataAccessObjectTest {

  private RestTemplate restTemplate;
  private ItemDataAccessObject itemDao;
  private MockRestServiceServer mockServer;
  private ObjectMapper mapper = new ObjectMapper();

  public static List<Item> items;

  /**
   * Inits the items used in the tests.
   */
  @BeforeAll
  public static void init() {
    items = new ArrayList<Item>();
    items.add(new Item("Mistet nøkler", "Mistet nøklene mine på gløs i går", State.LOST,
        "simen.kristoffersen98@gmail.com", "90360922", "default.png", LocalDateTime.now()));
    items.add(new Item("Funnet nøkler", "Fant nøkler på glød i dag", State.FOUND,
        "simen.kristoffersen98@gmail.com", "90360922", "default.png", LocalDateTime.now()));
  }

  /**
   * Setup before each test.
   */
  @BeforeEach
  public void setup() {
    itemDao = new ItemDataAccessObject("http://localhost:8098");
    restTemplate = itemDao.getRestTemplate();
    mockServer = MockRestServiceServer.createServer(restTemplate);
  }

  /**
   * Test if getAll() method sends the correct request.
   *
   * @throws JsonProcessingException the json processing exception
   * @throws URISyntaxException the URI syntax exception
   */
  @Test
  @DisplayName("Test getAll()")
  public void getAllTest() throws JsonProcessingException, URISyntaxException {
    mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8098/api/items")))
        .andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(items)));

    List<Item> itemsResult = itemDao.getAll();

    mockServer.verify();
    for (int i = 0; i < itemsResult.size(); i++) {
      assertThat(itemsResult.get(i)).isEqualToComparingFieldByField(items.get(i));
    }
  }

  /**
   * Test if get() method sends the correct request.
   *
   * @throws JsonProcessingException the json processing exception
   * @throws URISyntaxException the URI syntax exception
   */
  @Test
  @DisplayName("Test get(Long id)")
  public void getByIdTest() throws JsonProcessingException, URISyntaxException {
    mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8098/api/items/1")))
        .andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON).body(mapper.writeValueAsString(items.get(0))));

    Item itemResult = itemDao.get(1);

    mockServer.verify();
    assertThat(itemResult).isEqualToComparingFieldByField(items.get(0));
  }

  /**
   * Test if delete() method sends the correct request.
   *
   * @throws JsonProcessingException the json processing exception
   * @throws URISyntaxException the URI syntax exception
   */
  @Test
  @DisplayName("Test delete(Long id)")
  public void deleteTest() throws JsonProcessingException, URISyntaxException {
    mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8098/api/items/1")))
        .andExpect(method(HttpMethod.DELETE))
        .andRespond(withSuccess("{message : 'Item deleted!'}", MediaType.APPLICATION_JSON));

    itemDao.delete(1);

    mockServer.verify();
  }

  /**
   * Test if insert() method sends the correct request.
   *
   * @throws JsonProcessingException the json processing exception
   * @throws URISyntaxException the URI syntax exception
   */
  @Test
  @DisplayName("Test insert(Item item)")
  public void insertTest() throws JsonProcessingException, URISyntaxException {
    mockServer.expect(ExpectedCount.once(), requestTo(new URI("http://localhost:8098/api/items")))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess("{message : 'Item inserted!'}", MediaType.APPLICATION_JSON));

    Item newItem = new Item("Mistet airpodsene min", "Mistet airpodsene mine på SIT i går",
        State.LOST, "simen.kristoffersen98@gmail.com", "90360922", "default.png");
    boolean inserted = itemDao.insert(newItem);

    mockServer.verify();
    assertThat(inserted).isTrue();
  }

}
