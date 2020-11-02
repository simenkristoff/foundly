package foundly.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import foundly.core.model.Item;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.jackson.JsonComponent;

/**
 * The Class ItemSerializer. Handles serialization of Item-objects.
 */
@JsonComponent
public class ItemSerializer extends JsonSerializer<Item> {

  /**
   * Serializes an Item-object to a json-object.
   *
   * @param item the Item to be serialized
   * @param gen the JsonGenerator
   * @param serializers the Serializer
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public void serialize(Item item, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField("title", item.getTitle());
    gen.writeStringField("description", item.getDescription());
    gen.writeStringField("state", item.getState().toString());
    gen.writeStringField("email", item.getEmail());
    gen.writeStringField("phone", item.getPhone());

    if (item.getImage().isEmpty()) {
      gen.writeStringField("image", "default.png");
    } else {
      gen.writeStringField("image", item.getImage());
    }

    if (item.getDate() != null) {
      gen.writeStringField("date", item.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    } else {
      gen.writeStringField("date",
          LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    gen.writeEndObject();
  }

}
