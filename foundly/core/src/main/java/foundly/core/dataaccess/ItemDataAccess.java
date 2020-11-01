package foundly.core.dataaccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import foundly.core.model.Item;
import java.io.IOException;
import java.util.Collection;

public interface ItemDataAccess {

  Collection<Item> getAll();

  Item get(long id) throws IOException, InterruptedException;

  void update(long id, Item item);

  void delete(long id);

  boolean insert(Item item) throws JsonProcessingException;

}
