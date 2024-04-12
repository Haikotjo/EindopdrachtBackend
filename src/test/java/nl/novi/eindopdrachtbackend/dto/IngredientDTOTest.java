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

        // Reflectie om de 'id' te zetten zonder een setter in het model
        Field field = ingredient.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(ingredient, 1L);

        // Aanname dat er een passende constructor of methode is om DTO te maken
        IngredientDTO dto = new IngredientDTO();
        dto.setId((Long) field.get(ingredient));  // Gebruik reflectie om de id te krijgen en te zetten in DTO
        dto.setName(ingredient.getName());
        dto.setQuantity(ingredient.getQuantity());

        assertEquals(1L, dto.getId());
        assertEquals("Sugar", dto.getName());
        assertEquals(100, dto.getQuantity());
    }
}
