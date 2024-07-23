package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MenuItemDTOTest {

    @Test
    public void testMenuItemDTOGettersSetters() throws NoSuchFieldException, IllegalAccessException {
        MenuItemDTO menuItemDTO = new MenuItemDTO();

        setField(menuItemDTO, "id", 1L);
        menuItemDTO.setName("Pizza");
        menuItemDTO.setPrice(12.5);
        menuItemDTO.setDescription("Delicious cheese pizza");
        menuItemDTO.setImage("image_url");

        List<IngredientDTO> ingredients = new ArrayList<>();
        menuItemDTO.setIngredients(ingredients);

        assertEquals(1L, menuItemDTO.getId());
        assertEquals("Pizza", menuItemDTO.getName());
        assertEquals(12.5, menuItemDTO.getPrice(), 0.01);
        assertEquals("Delicious cheese pizza", menuItemDTO.getDescription());
        assertEquals("image_url", menuItemDTO.getImage());
        assertEquals(ingredients, menuItemDTO.getIngredients());
    }

    @Test
    public void testMenuItemDTOConstructor() throws NoSuchFieldException, IllegalAccessException {
        List<IngredientDTO> ingredients = new ArrayList<>();
        MenuItemDTO menuItemDTO = new MenuItemDTO(1L, "Pizza", 12.5, "Delicious cheese pizza", "image_url", ingredients);

        assertEquals(1L, menuItemDTO.getId());
        assertEquals("Pizza", menuItemDTO.getName());
        assertEquals(12.5, menuItemDTO.getPrice(), 0.01);
        assertEquals("Delicious cheese pizza", menuItemDTO.getDescription());
        assertEquals("image_url", menuItemDTO.getImage());
        assertEquals(ingredients, menuItemDTO.getIngredients());
    }

    @Test
    public void testEmptyConstructor() {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        assertNull(menuItemDTO.getIngredients());
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
}
