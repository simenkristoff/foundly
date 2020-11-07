package foundly.restapi.presentation;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import foundly.core.model.Item;
import foundly.core.model.Item.State;
import foundly.restapi.RestApi;
import foundly.restapi.service.FileStorageService;
import foundly.restapi.service.ItemService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Test for class ApiController
 */
@WebMvcTest(ApiController.class)
@ContextConfiguration(classes = RestApi.class)
@ActiveProfiles("test")
public class ApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private ItemService itemService;

  @MockBean
  private FileStorageService fileStorageService;

  private static List<Item> items;

  private ObjectMapper objectMapper = new ObjectMapper();


  /**
   * Initializes the items used in the tests.
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
   * Tests get all items
   */
  @Test
  public void getAllItemsTest() throws Exception {

    when(itemService.getAllItems()).thenReturn(items);

    this.mockMvc.perform(get("/api/items")).andExpect(status().isOk())
        .andExpect(jsonPath("$.size()", is(items.size())));

  }

  /**
   * Test get item by id
   */
  @Test
  public void getItemTest() throws Exception {

    when(itemService.getItemById(1L)).thenReturn(items.get(1));

    this.mockMvc.perform(get("/api/items/1")).andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is(items.get(1).getTitle())))
        .andExpect(jsonPath("$.description", is(items.get(1).getDescription())));
  }

  /**
   * Test insert item
   */
  @Test
  public void insertItemTest() throws Exception {
    Item item = new Item("Gul frosk", "Gul og prikkete frosk mistet", State.LOST, "test@test.no",
        "12345678", "default.png", LocalDateTime.now());

    when(itemService.insertItem(item)).thenReturn(item);

    this.mockMvc
        .perform(post("/api/items").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(item)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.message", is("Item inserted")));
  }

  /**
   * Test delete item
   */
  @Test
  public void deleteItemTest() throws Exception {

    given(itemService.getItemById(1L)).willReturn(items.get(1));
    doNothing().when(itemService).deleteItem(1L);

    this.mockMvc.perform(delete("/api/items/1")).andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("Item deleted at id = 1")));

  }

  /**
   * Test successful file upload
   */
  @Test
  public void uploadFileSuccessTest() throws Exception {
    MockMultipartFile file = new MockMultipartFile("file", "default.png".getBytes());

    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    this.mockMvc.perform(multipart("/api/upload").file(file)).andExpect(status().isOk());
  }

  /**
   * Test unsuccessful file upload
   */
  @Test
  public void uploadFileUnsuccessTest() throws Exception {
    MockMultipartFile file = new MockMultipartFile("file", "default.png".getBytes());

    doThrow(new RuntimeException()).when(fileStorageService).save(file);

    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    this.mockMvc.perform(multipart("/api/upload").file(file))
        .andExpect(status().isExpectationFailed());
  }


}
