package foundly.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import foundly.core.model.Item;
import foundly.core.model.Item.State;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.jackson.JsonComponent;

/**
 * The Class ItemDeserializer. Handles deserialization of Item-objects.
 */
@JsonComponent
public class ItemDeserializer extends JsonDeserializer<Item> {


  /**
   * Deserializes a json-object to an Item-object.
   *
   * @param p the JsonParser to be used.
   * @param ctxt the Context of the deserializer
   * @return the deserialized Item
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws JsonProcessingException the json processing exception
   */
  @Override
  public Item deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    ObjectCodec oc = p.getCodec();
    JsonNode node = oc.readTree(p);

    LocalDateTime dateTime = node.get("date").asText().isEmpty() ? LocalDateTime.now()
        : LocalDateTime.parse(node.get("date").asText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    // Check if image is set or not
    String imagePath = node.get("image").asText().toLowerCase();
    imagePath = (imagePath.equals("null")) ? null : imagePath;

    return new Item(node.get("title").asText(), node.get("description").asText(),
        State.fromString(node.get("state").asText()), node.get("email").asText(),
        node.get("phone").asText(), imagePath, dateTime);
  }
}
