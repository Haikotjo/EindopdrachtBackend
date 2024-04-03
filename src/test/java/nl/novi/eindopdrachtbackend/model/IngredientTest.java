package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTest {

    @Test
    public void testIngredientConstructor() {
        // Create an instance of DeliveryAddress using the constructor
        Ingredient ingredient = new Ingredient("Tomaat", 100);

        // Verify that the constructor correctly initializes the fields
        assertEquals("Tomaat", ingredient.getName(), "Well done");
        assertEquals(100, ingredient.getQuantity(), "Ok!");
    }
}
