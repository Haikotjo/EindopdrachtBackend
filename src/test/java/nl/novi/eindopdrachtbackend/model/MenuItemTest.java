package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

public class MenuItemTest {

    @Test
    public void testMenuItemGettersAndSetters() {
        MenuItem menuItem = new MenuItem();

        menuItem.setName("Pizza");
        assertEquals("Pizza", menuItem.getName());

        menuItem.setPrice(12.5);
        assertEquals(12.5, menuItem.getPrice(), 0.01);

        menuItem.setDescription("Delicious cheese pizza");
        assertEquals("Delicious cheese pizza", menuItem.getDescription());

        menuItem.setImage("image_url");
        assertEquals("image_url", menuItem.getImage());

        Set<Ingredient> ingredients = new HashSet<>();
        menuItem.setIngredients(ingredients);
        assertEquals(ingredients, menuItem.getIngredients());

        Set<Menu> menus = new HashSet<>();
        menuItem.setMenus(menus);
        assertEquals(menus, menuItem.getMenus());

        Set<Order> orders = new HashSet<>();
        menuItem.setOrders(orders);
        assertEquals(orders, menuItem.getOrders());

        Restaurant restaurant = new Restaurant();
        menuItem.setRestaurant(restaurant);
        assertEquals(restaurant, menuItem.getRestaurant());
    }

    @Test
    public void testAddIngredient() {
        MenuItem menuItem = new MenuItem();
        Ingredient ingredient = new Ingredient();

        menuItem.addIngredient(ingredient);

        assertTrue(menuItem.getIngredients().contains(ingredient));
        assertTrue(ingredient.getMenuItems().contains(menuItem));
    }
}
