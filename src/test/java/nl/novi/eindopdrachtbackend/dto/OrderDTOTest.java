package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderDTOTest {

    @Test
    void testOrderDTOConstructorsAndSetters() {
        // Test constructor with arguments
        OrderDTO dto = new OrderDTO(1L, true, 100L, 200L, 300L);

        // Test getters
        assertEquals(1L, dto.getId());
        assertTrue(dto.isFulfilled());
        assertEquals(100L, dto.getCustomerId());
        assertEquals(200L, dto.getRestaurantId());
        assertEquals(300L, dto.getDeliveryAddressId());

        // Test setters
        dto.setId(2L);
        dto.setFulfilled(false);
        dto.setCustomerId(101L);
        dto.setRestaurantId(201L);
        dto.setDeliveryAddressId(301L);

        // Test updated values
        assertEquals(2L, dto.getId());
        assertFalse(dto.isFulfilled());
        assertEquals(101L, dto.getCustomerId());
        assertEquals(201L, dto.getRestaurantId());
        assertEquals(301L, dto.getDeliveryAddressId());
    }

    @Test
    void testOrderInputDTOConstructorsAndSetters() {
        // Test constructor with arguments
        OrderInputDTO inputDto = new OrderInputDTO(true, 100L, 200L, 300L);

        // Test getters
        assertTrue(inputDto.isFulfilled());
        assertEquals(100L, inputDto.getCustomerId());
        assertEquals(200L, inputDto.getRestaurantId());
        assertEquals(300L, inputDto.getDeliveryAddressId());

        // Test setters
        inputDto.setFulfilled(false);
        inputDto.setCustomerId(101L);
        inputDto.setRestaurantId(201L);
        inputDto.setDeliveryAddressId(301L);

        // Test updated values
        assertFalse(inputDto.isFulfilled());
        assertEquals(101L, inputDto.getCustomerId());
        assertEquals(201L, inputDto.getRestaurantId());
        assertEquals(301L, inputDto.getDeliveryAddressId());
    }
}
