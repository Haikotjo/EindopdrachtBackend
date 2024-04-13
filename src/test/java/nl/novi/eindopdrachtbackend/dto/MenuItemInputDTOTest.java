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

        MenuInputDTO menuInputDTO = new MenuInputDTO();

        menuInputDTO.setName("Pizza");
        menuInputDTO.setPrice(9.99);
        menuInputDTO.setDescription("Delicious cheese pizza");
        menuInputDTO.setImage("image.jpg");
        menuInputDTO.setIngredientIds(ingredientIds);

        // Assertions
        assertEquals("Pizza", menuInputDTO.getName());
        assertEquals(9.99, menuInputDTO.getPrice());
        assertEquals("Delicious cheese pizza", menuInputDTO.getDescription());
        assertEquals("image.jpg", menuInputDTO.getImage());
        assertEquals(3, menuInputDTO.getIngredientIds().size());
        assertEquals(1L, menuInputDTO.getIngredientIds().get(0));
        assertEquals(2L, menuInputDTO.getIngredientIds().get(1));
        assertEquals(3L, menuInputDTO.getIngredientIds().get(2));

        // Test updating values
        menuInputDTO.setName("Burger");
        assertEquals("Burger", menuInputDTO.getName());
    }
}
