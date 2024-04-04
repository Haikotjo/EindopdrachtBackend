package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuItemTest {

    @Test
    public void testMenuItemConstructor() {
        // Create an instance of MenuItem using the constructor
        MenuItem menuItem = new MenuItem("Margherita Pizza", 9.99, "Classic Margherita with tomato and cheese", "imagePath.jpg");

        // Verify that the constructor correctly initializes the fields
        assertEquals("Margherita Pizza", menuItem.getName(), "The name does not match");
        assertEquals(9.99, menuItem.getPrice(), 0.001, "The price does not match");
        assertEquals("Classic Margherita with tomato and cheese", menuItem.getDescription(), "The description does not match");
        assertEquals("imagePath.jpg", menuItem.getImage(), "The image path does not match");
    }
}
