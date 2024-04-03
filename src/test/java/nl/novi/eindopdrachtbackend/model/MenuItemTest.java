package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuItemTest {

    @Test
    public void testMenuItemConstructor() {
        // Create a set of Ingredients
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient("Tomato", 100));
        ingredients.add(new Ingredient("Cheese", 50));

        // Create an instance of MenuItem using the constructor
        MenuItem menuItem = new MenuItem("Margherita Pizza", 9.99, "Classic Margherita with tomato and cheese", "imagePath.jpg", ingredients);

        // Verify that the constructor correctly initializes the fields
        assertEquals("Margherita Pizza", menuItem.getName(), "The name does not match");
        assertEquals(9.99, menuItem.getPrice(), 0.001, "The price does not match");
        assertEquals("Classic Margherita with tomato and cheese", menuItem.getDescription(), "The description does not match");
        assertEquals("imagePath.jpg", menuItem.getImage(), "The image path does not match");
        assertEquals(ingredients, menuItem.getIngredients(), "The ingredients do not match");
    }
}
