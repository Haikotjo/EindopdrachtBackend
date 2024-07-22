package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class IngredientTest {

    @Test
    public void testIngredientGettersAndSetters() {
        Ingredient ingredient = new Ingredient();

        ingredient.setName("Sugar");
        assertEquals("Sugar", ingredient.getName());

        ingredient.setQuantity(10);
        assertEquals(10, ingredient.getQuantity());

        ingredient.setUnit("kg");
        assertEquals("kg", ingredient.getUnit());

        ingredient.setCost(5.0);
        assertEquals(5.0, ingredient.getCost(), 0.01);

        ingredient.setSupplier("Supplier1");
        assertEquals("Supplier1", ingredient.getSupplier());

        ingredient.setExpirationDate(LocalDate.of(2024, 12, 31));
        assertEquals(LocalDate.of(2024, 12, 31), ingredient.getExpirationDate());

        ingredient.setDescription("Sweetener");
        assertEquals("Sweetener", ingredient.getDescription());

        Set<MenuItem> menuItems = new HashSet<>();
        ingredient.setMenuItems(menuItems);
        assertEquals(menuItems, ingredient.getMenuItems());

        User owner = new User();
        ingredient.setOwner(owner);
        assertEquals(owner, ingredient.getOwner());
    }

    @Test
    public void testAddMenuItem() {
        Ingredient ingredient = new Ingredient();
        MenuItem menuItem = new MenuItem();

        ingredient.addMenuItem(menuItem);

        assertTrue(ingredient.getMenuItems().contains(menuItem));
        assertTrue(menuItem.getIngredients().contains(ingredient));
    }
}
