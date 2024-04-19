package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemInputDTOTest {

    @Test
    public void testMenuInputDTO() {
        // Create Ingredient IDs list
        List<Long> ingredientIds = new ArrayList<>();
        ingredientIds.add(1L);
        ingredientIds.add(2L);
        ingredientIds.add(3L);

        MenuItemInputDTO menuItemInputDTO = new MenuItemInputDTO();

        menuItemInputDTO.setName("Pizza");
        menuItemInputDTO.setPrice(9.99);
        menuItemInputDTO.setDescription("Delicious cheese pizza");
        menuItemInputDTO.setImage("image.jpg");
        menuItemInputDTO.setIngredientIds(ingredientIds);

        // Assertions
        assertEquals("Pizza", menuItemInputDTO.getName());
        assertEquals(9.99, menuItemInputDTO.getPrice());
        assertEquals("Delicious cheese pizza", menuItemInputDTO.getDescription());
        assertEquals("image.jpg", menuItemInputDTO.getImage());
        assertEquals(3, menuItemInputDTO.getIngredientIds().size());
        assertEquals(1L, menuItemInputDTO.getIngredientIds().get(0));
        assertEquals(2L, menuItemInputDTO.getIngredientIds().get(1));
        assertEquals(3L, menuItemInputDTO.getIngredientIds().get(2));

        // Test updating values
        menuItemInputDTO.setName("Burger");
        assertEquals("Burger", menuItemInputDTO.getName());
    }
}
