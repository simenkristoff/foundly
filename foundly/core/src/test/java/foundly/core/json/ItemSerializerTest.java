package foundly.core.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import foundly.core.model.Item;
import foundly.core.model.Item.State;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Class ItemSerializer.
 */
public class ItemSerializerTest {

  private ItemSerializer itemSerializer;
  private ObjectMapper mapper;

  /**
   * Setup before each test.
   */
  @BeforeEach
  public void setup() {
    itemSerializer = new ItemSerializer();
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  /**
   * Check if ItemSerialzier serializes an Item-object properly.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Test serializer")
  public void serializeTest() throws IOException {
    LocalDateTime dateTime =
        LocalDateTime.parse("2020-10-31T09:33:20.168159", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    Item item = new Item("Mistet nøkler", "Mistet nøklene mine på gløs i går", State.LOST,
        "simen.kristoffersen98@gmail.com", "90360922", "default.png", dateTime);
    String template =
        "{\"title\":\"Mistet nøkler\",\"description\":\"Mistet nøklene mine på gløs i går\",\"state\":\"LOST\",\"email\":\"simen.kristoffersen98@gmail.com\",\"phone\":\"90360922\",\"image\":\"default.png\",\"date\":\"2020-10-31T09:33:20.168159\"}";

    Writer jsonWriter = new StringWriter();
    JsonGenerator gen = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializers = mapper.getSerializerProvider();
    itemSerializer.serialize(item, gen, serializers);
    gen.flush();

    assertEquals(jsonWriter.toString(), template);
  }

  /**
   * Serialize without image test. If image-field is empty the serializer should set the image-field
   * to "default.png".
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Test serialization of an Item with empty image-field")
  public void serializeWithoutImageTest() throws IOException {
    LocalDateTime dateTime =
        LocalDateTime.parse("2020-10-31T09:33:20.168159", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    Item item = new Item("Mistet nøkler", "Mistet nøklene mine på gløs i går", State.LOST,
        "simen.kristoffersen98@gmail.com", "90360922", "", dateTime);

    Writer jsonWriter = new StringWriter();
    JsonGenerator gen = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializers = mapper.getSerializerProvider();
    itemSerializer.serialize(item, gen, serializers);
    gen.flush();

    JsonNode node = mapper.readTree(jsonWriter.toString());
    assertEquals(node.get("image").asText(), "default.png");
  }

  /**
   * Serialize without datetime. If date-field is empty the serializer should set a new timestamp.
   *
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Test
  @DisplayName("Test serialization of an Item with empty date-field")
  public void serializeWithoutDatetime() throws IOException {
    Item item = new Item("Mistet nøkler", "Mistet nøklene mine på gløs i går", State.LOST,
        "simen.kristoffersen98@gmail.com", "90360922", "default.png", null);

    Writer jsonWriter = new StringWriter();
    JsonGenerator gen = new JsonFactory().createGenerator(jsonWriter);
    SerializerProvider serializers = mapper.getSerializerProvider();
    itemSerializer.serialize(item, gen, serializers);
    gen.flush();

    JsonNode node = mapper.readTree(jsonWriter.toString());
    assertThat(node.get("date").asText()).isNotEmpty();
  }
}
