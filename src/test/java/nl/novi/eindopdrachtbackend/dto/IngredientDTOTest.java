package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientDTOTest {

    @Test
    void testIngredientDTO() throws NoSuchFieldException, IllegalAccessException {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Sugar");
        ingredient.setQuantity(100);


        Field field = ingredient.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(ingredient, 1L);

        IngredientDTO dto = new IngredientDTO();
        dto.setId((Long) field.get(ingredient));
        dto.setName(ingredient.getName());
        dto.setQuantity(ingredient.getQuantity());

        assertEquals(1L, dto.getId());
        assertEquals("Sugar", dto.getName());
        assertEquals(100, dto.getQuantity());
    }
}
