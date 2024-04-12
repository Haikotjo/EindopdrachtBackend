package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientMapperTest {

    @Test
    void testToEntity() {
        IngredientInputDTO dto = new IngredientInputDTO();
        dto.setName("Sugar");
        dto.setQuantity(100);

        Ingredient ingredient = IngredientMapper.toEntity(dto);

        assertEquals("Sugar", ingredient.getName());
        assertEquals(100, ingredient.getQuantity());
    }

    @Test
    void testToDTO() throws NoSuchFieldException, IllegalAccessException {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Salt");
        ingredient.setQuantity(50);

        // Set id via reflection
        Field field = Ingredient.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(ingredient, 1L);

        IngredientDTO resultDTO = IngredientMapper.toDTO(ingredient);

        assertEquals(1L, resultDTO.getId());
        assertEquals("Salt", resultDTO.getName());
        assertEquals(50, resultDTO.getQuantity());
    }
}
