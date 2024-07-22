package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientInputDTOTest {

    private IngredientInputDTO ingredientInputDTO;

    @BeforeEach
    public void setUp() {
        ingredientInputDTO = new IngredientInputDTO();
    }

    @Test
    public void testGettersAndSetters() {
        ingredientInputDTO.setName("Tomato");
        ingredientInputDTO.setQuantity(10);
        ingredientInputDTO.setUnit("kg");
        ingredientInputDTO.setCost(5.0);
        ingredientInputDTO.setSupplier("Supplier1");
        ingredientInputDTO.setExpirationDate(LocalDate.of(2025, 1, 1));
        ingredientInputDTO.setDescription("Fresh Tomatoes");
        ingredientInputDTO.setOwnerId(2L);

        assertEquals("Tomato", ingredientInputDTO.getName());
        assertEquals(10, ingredientInputDTO.getQuantity());
        assertEquals("kg", ingredientInputDTO.getUnit());
        assertEquals(5.0, ingredientInputDTO.getCost());
        assertEquals("Supplier1", ingredientInputDTO.getSupplier());
        assertEquals(LocalDate.of(2025, 1, 1), ingredientInputDTO.getExpirationDate());
        assertEquals("Fresh Tomatoes", ingredientInputDTO.getDescription());
        assertEquals(2L, ingredientInputDTO.getOwnerId());
    }
}
