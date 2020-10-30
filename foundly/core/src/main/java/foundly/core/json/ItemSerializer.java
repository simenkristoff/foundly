package foundly.core.json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.boot.jackson.JsonComponent;

import foundly.core.model.Item;

@JsonComponent
public class ItemSerializer extends JsonSerializer<Item> {

    // Method to write json-object from given Item-object
    @Override
    public void serialize(Item item, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Generate json-object from fields in Item
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
			gen.writeStringField("date", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
		}

		gen.writeEndObject();
    }

}