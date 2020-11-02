package foundly.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foundly.core.model.Item.State;

public class ItemTest {

    private String title, description, email, phone, image;
    private LocalDateTime dateTime;
    
    @BeforeEach
    public void setup() {
        this.title = "Mistet nøkkel";
        this.description = "Sist sett på R2";
        this.email = "simen.kristoffersen98@gmail.com";
        this.phone = "90360922";
        this.image = "default.png";
        this.dateTime = LocalDateTime.now();
    }
    
    @Test
    public void constructorTest() {
        Item item = new Item(title, description, State.FOUND, email, phone, image);
        String toString = "Item [id=" + 0 + ", title=" + title + ", description=" + description + ", state=" + State.FOUND + ", email=" + email + ", phone=" + phone + ", image=" + image + ", date=" + null + "]";
        assertEquals(item.toString(), toString);
    }
    
    @Test
    public void constructor2Test() {
        Item item = new Item(title, description, State.FOUND, email, phone, image, dateTime);
        String toString = "Item [id=" + 0 + ", title=" + title + ", description=" + description + ", state=" + State.FOUND + ", email=" + email + ", phone=" + phone + ", image=" + image + ", date=" + dateTime + "]";
        assertEquals(item.toString(), toString);
    }
    
    @Test
    public void gettersSettersTest() {
        Item item = new Item();
        
        assertEquals(item.getId(), 0);
        
        item.setTitle(title);
        assertEquals(item.getTitle(), title);
        
        item.setDescription(description);
        assertEquals(item.getDescription(), description);
        
        item.setState(State.FOUND);
        assertEquals(item.getState(), State.FOUND);
        
        item.setEmail(email);
        assertEquals(item.getEmail(), email);
        
        item.setPhone(phone);
        assertEquals(item.getPhone(), phone);
        
        item.setImage(image);
        assertEquals(item.getImage(), image);
        
        item.setDate(this.dateTime);
        assertEquals(item.getDate(), this.dateTime);
    }
    
    @Test
    public void stateValueTest() {
        State lostState = State.LOST;
        String lostVal = lostState.getValue();
        assertEquals(lostVal, "MISTET");
        
        State foundState = State.LOST;
        String foundVal = foundState.getValue();
        assertEquals(foundVal, "MISTET");
    }
    
    @Test
    public void stateFromStringTest() {
        String foundToState = "FOUND";
        assertEquals(State.fromString(foundToState), State.FOUND);
        
        String lostToState = "LOST";
        assertEquals(State.fromString(lostToState), State.LOST);
        
        String nullToNull = "";
        assertEquals(State.fromString(nullToNull), null);
    }
        
}
