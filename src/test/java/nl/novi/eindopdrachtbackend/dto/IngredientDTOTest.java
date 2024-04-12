package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientDTOTest {

    @Test
    void testIngredientDTO() throws NoSuchFieldException, IllegalAccessException {
        Ingredient ingredient = new Ingredient("Sugar", 100);

        // Zet id via reflection
        Field field = Ingredient.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(ingredient, 1L);

        IngredientDTO dto = new IngredientDTO(ingredient);

        assertEquals(1L, dto.getId());
        assertEquals("Sugar", dto.getName());
        assertEquals(100, dto.getQuantity());
    }
}
