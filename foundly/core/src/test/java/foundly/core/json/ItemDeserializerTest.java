package foundly.core.json;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import foundly.core.model.Item;
import foundly.core.model.Item.State;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Class ItemDeserializer.
 */
public class ItemDeserializerTest {

  private ItemDeserializer itemDeserializer;
  private ObjectMapper mapper;

  /**
   * Setup before each test.
   */
  @BeforeEach
  public void setup() {
    itemDeserializer = new ItemDeserializer();
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  /**
   * Check if ItemDeserializer deserializes a json-object properly.
   *
   * @throws JsonParseException the json parse exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Test deserializer")
  public void deserializeTest() throws JsonParseException, IOException {
    LocalDateTime dateTime =
        LocalDateTime.parse("2020-10-31T09:33:20.168159", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    Item template = new Item("Mistet nøkler", "Mistet nøklene mine på gløs i går", State.LOST,
        "simen.kristoffersen98@gmail.com", "90360922", "default.png", dateTime);
    String json =
        "{\"title\":\"Mistet nøkler\",\"description\":\"Mistet nøklene mine på gløs i går\",\"state\":\"LOST\",\"email\":\"simen.kristoffersen98@gmail.com\",\"phone\":\"90360922\",\"image\":\"default.png\",\"date\":\"2020-10-31T09:33:20.168159\"}";

    JsonParser parser = mapper.getFactory().createParser(json);
    DeserializationContext ctxt = mapper.getDeserializationContext();
    Item item = itemDeserializer.deserialize(parser, ctxt);

    assertThat(template).isEqualToComparingFieldByField(item);
  }

  /**
   * Deserialize without datetime. If date-field is empty the deserializer should set a new
   * timestamp.
   *
   * @throws JsonParseException the json parse exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Test deserialization of a json-object with empty date-field")
  public void deserializeWithoutDateTimeTest() throws JsonParseException, IOException {
    String json =
        "{\"title\":\"Mistet nøkler\",\"description\":\"Mistet nøklene mine på gløs i går\",\"state\":\"LOST\",\"email\":\"simen.kristoffersen98@gmail.com\",\"phone\":\"90360922\",\"image\":\"default.png\",\"date\":\"\"}";
    JsonParser parser = mapper.getFactory().createParser(json);
    DeserializationContext ctxt = mapper.getDeserializationContext();
    Item item = itemDeserializer.deserialize(parser, ctxt);
    assertThat(item.getDate()).isNotNull();
  }

}
