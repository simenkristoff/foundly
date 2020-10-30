package foundly.core.json;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.boot.jackson.JsonComponent;

import foundly.core.model.Item;
import foundly.core.model.Item.State;

@JsonComponent
public class ItemDeserializer extends JSONDeserializer<Item> {

    // Metode som lager Item-objekt fra JsonParser
    @Override
    public Item deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = p.getCodec();
		JsonNode node = oc.readTree(p);

		LocalDateTime dateTime = node.get("date").asText().isEmpty() ? LocalDateTime.now()
				: LocalDateTime.parse(node.get("date").asText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		return new Item(node.get("title").asText(), node.get("description").asText(),
				State.fromString(node.get("state").asText()), node.get("email").asText(), node.get("phone").asText(),
				node.get("image").asText(), dateTime);
    }
}
