//package nl.novi.eindopdrachtbackend.dto;
//
//import nl.novi.eindopdrachtbackend.model.Ingredient;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class IngredientMapperTest {
//
//    @Test
//    void testToIngredientDTO() {
//        // Arrange
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Tomato");
//        ingredient.setQuantity(10);
//
//        // Act
//        IngredientDTO dto = IngredientMapper.toIngredientDTO(ingredient);
//
//        // Assert
//        assertEquals("Tomato", dto.getName());
//        assertEquals(10, dto.getQuantity());
//    }
//
//    @Test
//    void testToIngredient() throws NoSuchFieldException, IllegalAccessException {
//        // Arrange
//        IngredientInputDTO inputDTO = new IngredientInputDTO();
//        inputDTO.setName("Tomato");
//        inputDTO.setQuantity(10);
//
//        Ingredient ingredient = IngredientMapper.toIngredient(inputDTO);
//
//        // Reflect ID-veld no setter
//        java.lang.reflect.Field field = Ingredient.class.getDeclaredField("id");
//        field.setAccessible(true);
//        field.set(ingredient, 1L);
//
//        // Act
//        IngredientDTO dto = IngredientMapper.toIngredientDTO(ingredient);
//
//        // Assert
//        assertEquals("Tomato", dto.getName());
//        assertEquals(10, dto.getQuantity());
//        assertEquals(1L, dto.getId());
//    }
//}
