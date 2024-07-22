package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientDTOTest {

    @Test
    public void testIngredientDTOGettersSetters() throws NoSuchFieldException, IllegalAccessException {
        IngredientDTO ingredientDTO = new IngredientDTO();

        setField(ingredientDTO, "id", 1L);
        ingredientDTO.setName("Tomato");
        ingredientDTO.setQuantity(10);
        ingredientDTO.setUnit("kg");
        ingredientDTO.setCost(5.0);
        ingredientDTO.setSupplier("Supplier1");
        ingredientDTO.setExpirationDate(LocalDate.of(2025, 1, 1));
        ingredientDTO.setDescription("Fresh Tomatoes");
        ingredientDTO.setOwnerId(2L);

        assertEquals(1L, ingredientDTO.getId());
        assertEquals("Tomato", ingredientDTO.getName());
        assertEquals(10, ingredientDTO.getQuantity());
        assertEquals("kg", ingredientDTO.getUnit());
        assertEquals(5.0, ingredientDTO.getCost());
        assertEquals("Supplier1", ingredientDTO.getSupplier());
        assertEquals(LocalDate.of(2025, 1, 1), ingredientDTO.getExpirationDate());
        assertEquals("Fresh Tomatoes", ingredientDTO.getDescription());
        assertEquals(2L, ingredientDTO.getOwnerId());
    }

    @Test
    public void testIngredientDTOConstructor() throws NoSuchFieldException, IllegalAccessException {
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "Tomato", 5.0, 10, "kg", "Supplier1", LocalDate.of(2025, 1, 1), "Fresh Tomatoes", 2L);

        assertEquals(1L, ingredientDTO.getId());
        assertEquals("Tomato", ingredientDTO.getName());
        assertEquals(10, ingredientDTO.getQuantity());
        assertEquals("kg", ingredientDTO.getUnit());
        assertEquals(5.0, ingredientDTO.getCost());
        assertEquals("Supplier1", ingredientDTO.getSupplier());
        assertEquals(LocalDate.of(2025, 1, 1), ingredientDTO.getExpirationDate());
        assertEquals("Fresh Tomatoes", ingredientDTO.getDescription());
        assertEquals(2L, ingredientDTO.getOwnerId());
    }

    @Test
    public void testIngredientDTOFromEntity() throws NoSuchFieldException, IllegalAccessException {
        Ingredient ingredient = new Ingredient();
        setField(ingredient, "id", 1L);
        ingredient.setName("Tomato");
        ingredient.setQuantity(10);
        ingredient.setUnit("kg");
        ingredient.setCost(5.0);
        ingredient.setSupplier("Supplier1");
        ingredient.setExpirationDate(LocalDate.of(2025, 1, 1));
        ingredient.setDescription("Fresh Tomatoes");

        IngredientDTO ingredientDTO = new IngredientDTO(ingredient);

        assertEquals(1L, ingredientDTO.getId());
        assertEquals("Tomato", ingredientDTO.getName());
        assertEquals(10, ingredientDTO.getQuantity());
        assertEquals("kg", ingredientDTO.getUnit());
        assertEquals(5.0, ingredientDTO.getCost());
        assertEquals("Supplier1", ingredientDTO.getSupplier());
        assertEquals(LocalDate.of(2025, 1, 1), ingredientDTO.getExpirationDate());
        assertEquals("Fresh Tomatoes", ingredientDTO.getDescription());
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
}
