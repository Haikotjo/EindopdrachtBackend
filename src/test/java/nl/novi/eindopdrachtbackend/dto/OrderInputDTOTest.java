package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderInputDTOTest {

    @Test
    void testOrderInputDTOGettersAndSetters() {
        // Initialize the DTO with no args constructor
        OrderInputDTO orderInputDTO = new OrderInputDTO();

        // Set values using setters
        orderInputDTO.setFulfilled(true);
        orderInputDTO.setCustomerId(100L);
        orderInputDTO.setRestaurantId(200L);
        orderInputDTO.setDeliveryAddressId(300L);
        List<Long> menuItemIds = Arrays.asList(1L, 2L, 3L);
        orderInputDTO.setMenuItemIds(menuItemIds);

        // Test getters
        assertTrue(orderInputDTO.isFulfilled());
        assertEquals(100L, orderInputDTO.getCustomerId());
        assertEquals(200L, orderInputDTO.getRestaurantId());
        assertEquals(300L, orderInputDTO.getDeliveryAddressId());
        assertEquals(menuItemIds, orderInputDTO.getMenuItemIds());

        // Update and re-test values to ensure setters are working properly
        orderInputDTO.setFulfilled(false);
        orderInputDTO.setCustomerId(101L);
        orderInputDTO.setRestaurantId(201L);
        orderInputDTO.setDeliveryAddressId(301L);
        List<Long> newMenuItemIds = Arrays.asList(4L, 5L, 6L);
        orderInputDTO.setMenuItemIds(newMenuItemIds);

        // Test updated values
        assertFalse(orderInputDTO.isFulfilled());
        assertEquals(101L, orderInputDTO.getCustomerId());
        assertEquals(201L, orderInputDTO.getRestaurantId());
        assertEquals(301L, orderInputDTO.getDeliveryAddressId());
        assertEquals(newMenuItemIds, orderInputDTO.getMenuItemIds());
    }
}
