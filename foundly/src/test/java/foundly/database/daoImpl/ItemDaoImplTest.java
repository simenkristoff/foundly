package foundly.database.daoImpl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.List;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.sql.rowset.serial.SerialBlob;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foundly.core.model.Item;

public class ItemDaoImplTest {
	
	private Blob image;
	private ItemDaoImpl itemDao;
	private HashMap<String, Object> map;
	private Item item;

	@BeforeEach
    public void setUp() {
		itemDao = new ItemDaoImpl();
		try {
			image = new SerialBlob(getClass().getResourceAsStream("test.jpg").readAllBytes());
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		item = new Item(null, "Nøkkel", "Funnet i Hangaren", image);
    }
	
	@Test
	public void testMapper() {
		HashMap<String, Object> expected = new LinkedHashMap<String, Object>();
		expected.put("id", null);
		expected.put("name", "Nøkkel");
		expected.put("description", "Funnet i Hangaren");
		expected.put("image", image);
		
		map = itemDao.mapper(item);
		assertTrue(expected.equals(map));
	}
	
	@Test
	public void testParseInsertParameters() {
		
		// First test
		Object[] valueData = {null, "Nøkkel", "Funnet i hangaren", image};
		ArrayList<Object> values = new ArrayList<Object>(Arrays.asList(valueData));		
		String params = itemDao.parseInsertParameters(values);
		String expected = "null, ?, ?, ?";
		assertEquals(expected, params);
		
		// Second test
		Object[] valueData2 = {null, "Nøkkel", "Funnet i hangaren", null};
		ArrayList<Object> values2 = new ArrayList<Object>(Arrays.asList(valueData2));		
		String params2 = itemDao.parseInsertParameters(values2);
		String expected2 = "null, ?, ?, null";
		assertEquals(expected2, params2);
	}
	
	@Test
	public void testParseUpdateParameters() {
		
		// First test
		String[] indexData = {"id", "name", "description", "image"};
		ArrayList<String> index = new ArrayList(Arrays.asList(indexData));	
		
		Object[] valueData = {null, "Nøkkel", "Funnet i hangaren", image};
		ArrayList<Object> values = new ArrayList<Object>(Arrays.asList(valueData));	
		
		String params = itemDao.parseUpdateParameters(index, values);
		String expected = "name = ?, description = ?, image = ?";
		assertEquals(expected, params);
		
		// Second test
		String[] indexData2 = {"id", "name", "description", "image"};
		ArrayList<String> index2 = new ArrayList(Arrays.asList(indexData));	
		
		Object[] valueData2 = {null, "Nøkkel", "Funnet i hangaren", null};
		ArrayList<Object> values2 = new ArrayList<Object>(Arrays.asList(valueData2));	
		
		String params2 = itemDao.parseUpdateParameters(index2, values2);
		String expected2 = "name = ?, description = ?, image = null";
		assertEquals(expected2, params2);
	}
	
	
}
