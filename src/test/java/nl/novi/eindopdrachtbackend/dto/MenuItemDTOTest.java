package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDTOTest {

    @Test
    public void testMenuItemDTO() throws NoSuchFieldException, IllegalAccessException {
        // Create IngredientDTO list with reflection
        List<IngredientDTO> ingredients = new ArrayList<>();
        IngredientDTO cheese = new IngredientDTO();
        setField(cheese, "id", 1L);
        cheese.setName("Cheese");
        cheese.setQuantity(2);
        ingredients.add(cheese);

        MenuItemDTO menuItemDTO = new MenuItemDTO();

        // Reflectively set id for MenuItemDTO
        setField(menuItemDTO, "id", 1L);

        menuItemDTO.setName("Pizza");
        menuItemDTO.setPrice(9.99);
        menuItemDTO.setDescription("Delicious cheese pizza");
        menuItemDTO.setImage("image.jpg");
        menuItemDTO.setIngredients(ingredients);

        // Assertions
        assertEquals(1L, menuItemDTO.getId());
        assertEquals("Pizza", menuItemDTO.getName());
        assertEquals(9.99, menuItemDTO.getPrice());
        assertEquals("Delicious cheese pizza", menuItemDTO.getDescription());
        assertEquals("image.jpg", menuItemDTO.getImage());
        assertEquals(1, menuItemDTO.getIngredients().size());
        assertEquals("Cheese", menuItemDTO.getIngredients().get(0).getName());

        // Test updating values
        menuItemDTO.setName("Burger");
        assertEquals("Burger", menuItemDTO.getName());
    }

    private void setField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}
