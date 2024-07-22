package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientMapperTest {

    private Ingredient ingredient;
    private IngredientInputDTO inputDTO;
    private User owner;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        owner = new User();
        setField(owner, "id", 1L);

        ingredient = new Ingredient();
        setField(ingredient, "id", 1L);
        ingredient.setName("Salt");
        ingredient.setQuantity(5);
        ingredient.setUnit("kg");
        ingredient.setCost(2.0);
        ingredient.setSupplier("Supplier1");
        ingredient.setExpirationDate(LocalDate.of(2025, 1, 1));
        ingredient.setDescription("Flavor enhancer");
        ingredient.setOwner(owner);

        inputDTO = new IngredientInputDTO();
        inputDTO.setName("Salt");
        inputDTO.setQuantity(5);
        inputDTO.setUnit("kg");
        inputDTO.setCost(2.0);
        inputDTO.setSupplier("Supplier1");
        inputDTO.setExpirationDate(LocalDate.of(2025, 1, 1));
        inputDTO.setDescription("Flavor enhancer");
        inputDTO.setOwnerId(owner.getId());
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    public void testToOwnerIngredientDTO() {
        IngredientDTO dto = IngredientMapper.toOwnerIngredientDTO(ingredient);
        assertEquals(ingredient.getId(), dto.getId());
        assertEquals(ingredient.getName(), dto.getName());
        assertEquals(ingredient.getQuantity(), dto.getQuantity());
        assertEquals(ingredient.getUnit(), dto.getUnit());
        assertEquals(ingredient.getCost(), dto.getCost());
        assertEquals(ingredient.getSupplier(), dto.getSupplier());
        assertEquals(ingredient.getExpirationDate(), dto.getExpirationDate());
        assertEquals(ingredient.getDescription(), dto.getDescription());
        assertEquals(ingredient.getOwner().getId(), dto.getOwnerId());
    }

    @Test
    public void testToCustomerIngredientDTO() {
        IngredientDTO dto = IngredientMapper.toCustomerIngredientDTO(ingredient);
        assertEquals(ingredient.getId(), dto.getId());
        assertEquals(ingredient.getName(), dto.getName());
        assertEquals(ingredient.getCost(), dto.getCost());
        assertEquals(ingredient.getDescription(), dto.getDescription());
    }

    @Test
    public void testToIngredient() {
        Ingredient entity = IngredientMapper.toIngredient(inputDTO, owner);
        assertEquals(inputDTO.getName(), entity.getName());
        assertEquals(inputDTO.getCost(), entity.getCost());
        assertEquals(inputDTO.getQuantity(), entity.getQuantity());
        assertEquals(inputDTO.getUnit(), entity.getUnit());
        assertEquals(inputDTO.getSupplier(), entity.getSupplier());
        assertEquals(inputDTO.getExpirationDate(), entity.getExpirationDate());
        assertEquals(inputDTO.getDescription(), entity.getDescription());
        assertEquals(owner, entity.getOwner());
    }
}
