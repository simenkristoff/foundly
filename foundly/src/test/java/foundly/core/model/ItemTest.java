package foundly.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foundly.core.model.Item.State;


public class ItemTest {
	
	private Blob image;
	private Integer id;
	private State state;
    private String title;
    private String description;
    private Item item;
	
	@BeforeEach
	public void setup() {
        this.id = 1;
        this.state = Item.State.FOUND;
        this.title = "Nøkkel";
        this.description = "Dette er en nøkkel";
        try {
			this.image = new SerialBlob(getClass().getResourceAsStream("itemTest.jpg").readAllBytes());
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
        this.item = new Item(this.id, this.state, this.title, this.description, this.image);
	}
	
	@Test
	public void createItemTest() {
        assertEquals(item.getId(), this.id);
        assertEquals(item.getState(), this.state);
        assertEquals(item.getTitle(), this.title);
        assertEquals(item.getDescription(), this.description);
        assertEquals(item.getImageBlob(), this.image);
	}
	
	@Test
	public void changeItemTest() {
		this.id = 2;
		this.state = Item.State.LOST;
		this.title = "Fin nøkkel";
		this.description = "Fant en fin nøkkel";
		try {
			this.image = new SerialBlob(getClass().getResourceAsStream("newKeyTest.jpg").readAllBytes());
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		this.item.setId(this.id);
		this.item.setState(this.state);
		this.item.setTitle(this.title);
		this.item.setDescription(this.description);
		this.item.setImageBlob(this.image);
		
		assertEquals(item.getId(), this.id);
        assertEquals(item.getState(), this.state);
        assertEquals(item.getTitle(), this.title);
        assertEquals(item.getDescription(), this.description);
        assertEquals(item.getImageBlob(), this.image);
	}
	
	
}
