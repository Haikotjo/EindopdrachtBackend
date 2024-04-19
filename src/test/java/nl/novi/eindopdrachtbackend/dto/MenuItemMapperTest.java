package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MenuItemMapperTest {

    @Test
    void toMenuItemDTOTest() {
        // Arrange
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Cheese");
        ingredient.setQuantity(2);

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredient);

        MenuItem menuItem = new MenuItem();
        menuItem.setName("Pizza");
        menuItem.setPrice(9.99);
        menuItem.setDescription("Delicious pizza");
        menuItem.setImage("pizza.jpg");
        menuItem.setIngredients(ingredients);

        // Act
        MenuItemDTO menuItemDTO = MenuItemMapper.toMenuItemDTO(menuItem);

        // Assert
        assertNotNull(menuItemDTO);
        assertEquals(menuItem.getId(), menuItemDTO.getId());
        assertEquals(menuItem.getName(), menuItemDTO.getName());
        assertEquals(menuItem.getPrice(), menuItemDTO.getPrice());
        assertEquals(menuItem.getDescription(), menuItemDTO.getDescription());
        assertEquals(menuItem.getImage(), menuItemDTO.getImage());
        assertEquals(1, menuItemDTO.getIngredients().size());
        assertEquals("Cheese", menuItemDTO.getIngredients().get(0).getName());
    }

    @Test
    void toMenuItemTest() {
        // Arrange
        MenuItemInputDTO inputDTO = new MenuItemInputDTO();
        inputDTO.setName("Burger");
        inputDTO.setPrice(7.99);
        inputDTO.setDescription("Juicy burger");
        inputDTO.setImage("burger.jpg");

        // Act
        MenuItem menuItem = MenuItemMapper.toMenuItem(inputDTO);

        // Assert
        assertNotNull(menuItem);
        assertEquals(inputDTO.getName(), menuItem.getName());
        assertEquals(inputDTO.getPrice(), menuItem.getPrice());
        assertEquals(inputDTO.getDescription(), menuItem.getDescription());
        assertEquals(inputDTO.getImage(), menuItem.getImage());
    }
}
