package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuItemInputDTOTest {

    private MenuItemInputDTO menuItemInputDTO;

    @BeforeEach
    public void setUp() {
        menuItemInputDTO = new MenuItemInputDTO();
    }

    @Test
    public void testGettersAndSetters() {
        menuItemInputDTO.setName("Pizza");
        menuItemInputDTO.setPrice(12.5);
        menuItemInputDTO.setDescription("Delicious cheese pizza");
        menuItemInputDTO.setImage("image_url");

        List<Long> ingredientIds = new ArrayList<>();
        ingredientIds.add(1L);
        ingredientIds.add(2L);
        menuItemInputDTO.setIngredientIds(ingredientIds);

        assertEquals("Pizza", menuItemInputDTO.getName());
        assertEquals(12.5, menuItemInputDTO.getPrice(), 0.01);
        assertEquals("Delicious cheese pizza", menuItemInputDTO.getDescription());
        assertEquals("image_url", menuItemInputDTO.getImage());
        assertEquals(ingredientIds, menuItemInputDTO.getIngredientIds());
    }
}
