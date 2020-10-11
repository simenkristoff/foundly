package foundly.database.daoImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.sql.rowset.serial.SerialBlob;
import java.util.Arrays;

// import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foundly.daoImpl.GenericDaoImpl;
import foundly.daoImpl.ItemDaoImpl;
import foundly.model.Item;

public class ItemDaoImplTest {
	
	private Blob image;
	private ItemDaoImpl itemDao;
	private HashMap<String, Object> mapFound, mapLost;
	private Item foundItem, lostItem;

	@BeforeEach
    public void setup() {
		itemDao = new ItemDaoImpl();
		try {
			image = new SerialBlob(getClass().getResourceAsStream("test.jpg").readAllBytes());
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		foundItem = new Item(null, Item.State.FOUND, "Nøkkel", "Funnet i Hangaren", image);
		lostItem = new Item(null, Item.State.LOST, "Nøkkel", "Mistet i Hangaren");
    }
	
	@Test
	public void testMapper() {
		
		/** FOUND ITEM **/
		
		HashMap<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("id", null);
		expected.put("state", "FOUND");
		expected.put("title", "Nøkkel");
		expected.put("description", "Funnet i Hangaren");
		expected.put("image", image);
		expected.put("created_at", foundItem.getDateCreated());
		
		mapFound = itemDao.mapper(foundItem);
		assertTrue(expected.equals(mapFound));
		
		/** LOST ITEM **/
		
		HashMap<String, Object> expected2 = new LinkedHashMap<String, Object>();
		expected.put("state", "LOST");
		expected.put("description", "Mistet i Hangaren");
		expected.put("image", null);
		expected.put("created_at", lostItem.getDateCreated());
		
		mapLost = itemDao.mapper(lostItem);
		assertTrue(expected.equals(mapLost));
	}
	
	@Test
	public void testParseInsertParameters() {
		
		/** FOUND ITEM **/
		
		// First test
		Object[] valueData = {null, "FOUND", "Nøkkel", "Funnet i hangaren", image, foundItem.getDateCreated()};
		ArrayList<Object> values = new ArrayList<Object>(Arrays.asList(valueData));		
		String params = itemDao.parseInsertParameters(values);
		String expected = "null, ?, ?, ?, ?, ?";
		assertEquals(expected, params);
		
		// Second test
		Object[] valueData2 = {null, "FOUND", "Nøkkel", "Funnet i hangaren", null, foundItem.getDateCreated()};
		values = new ArrayList<Object>(Arrays.asList(valueData2));		
		params = itemDao.parseInsertParameters(values);
		expected = "null, ?, ?, ?, null, ?";
		assertEquals(expected, params);
		
		/** LOST ITEM **/
		
		// First test
		Object[] valueData3 = {null, "FOUND", "Nøkkel", "Mistet i hangaren", image, lostItem.getDateCreated()};
		values = new ArrayList<Object>(Arrays.asList(valueData3));		
		params = itemDao.parseInsertParameters(values);
		expected = "null, ?, ?, ?, ?, ?";
		assertEquals(expected, params);
		
		// Second test
		Object[] valueData4 = {null, "FOUND", "Nøkkel", "Mistet i hangaren", null, lostItem.getDateCreated()};
		values = new ArrayList<Object>(Arrays.asList(valueData4));		
		params = itemDao.parseInsertParameters(values);
		expected = "null, ?, ?, ?, null, ?";
		assertEquals(expected, params);
		
	}
	
	@Test
	public void testParseUpdateParameters() {
		
		/** FOUND ITEM **/
		
		// First test
		String[] indexData = {"id", "state", "title", "description", "image", "created_at"};
		ArrayList<String> index = new ArrayList(Arrays.asList(indexData));	
		
		Object[] valueData = {null, "FOUND", "Nøkkel", "Funnet i hangaren", image, foundItem.getDateCreated()};
		ArrayList<Object> values = new ArrayList<Object>(Arrays.asList(valueData));	
		
		String params = itemDao.parseUpdateParameters(index, values);
		String expected = "state = ?, title = ?, description = ?, image = ?, created_at = ?";
		assertEquals(expected, params);
		
		// Second test
		String[] indexData2 = {"id", "state", "title", "description", "image", "created_at"};
		index = new ArrayList(Arrays.asList(indexData2));	
		
		Object[] valueData2 = {null, "FOUND", "Nøkkel", "Funnet i hangaren", null, foundItem.getDateCreated()};
		values = new ArrayList<Object>(Arrays.asList(valueData2));	
		
		params = itemDao.parseUpdateParameters(index, values);
		expected = "state = ?, title = ?, description = ?, image = null, created_at = ?";
		assertEquals(expected, params);
		
		/** LOST ITEM **/
		
		// First test
		String[] indexData3 = {"id", "state", "title", "description", "image", "created_at"};
		index = new ArrayList(Arrays.asList(indexData3));	
		
		Object[] valueData3 = {null, "LOST", "Nøkkel", "Mistet i hangaren", image, lostItem.getDateCreated()};
		values = new ArrayList<Object>(Arrays.asList(valueData3));	
		
		params = itemDao.parseUpdateParameters(index, values);
		expected = "state = ?, title = ?, description = ?, image = ?, created_at = ?";
		assertEquals(expected, params);
		
		// Second test
		String[] indexData4 = {"id", "state", "title", "description", "image", "created_at"};
		index = new ArrayList(Arrays.asList(indexData4));	
		
		Object[] valueData4 = {null, "LOST", "Nøkkel", "Mistet i hangaren", null, lostItem.getDateCreated()};
		values = new ArrayList<Object>(Arrays.asList(valueData4));	
		
		params = itemDao.parseUpdateParameters(index, values);
		expected = "state = ?, title = ?, description = ?, image = null, created_at = ?";
		assertEquals(expected, params);
	}
	
}
