package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientInputDTOTest {

    @Test
    void testSettersAndGetters() {
        IngredientInputDTO dto = new IngredientInputDTO();
        dto.setName("Salt");
        dto.setQuantity(50);

        assertEquals("Salt", dto.getName());
        assertEquals(50, dto.getQuantity());
    }
}
