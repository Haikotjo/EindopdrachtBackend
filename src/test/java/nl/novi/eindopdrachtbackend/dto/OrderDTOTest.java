package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderDTOTest {

    @Test
    void testOrderDTOConstructorsAndSetters() {
        // Arrange
        DeliveryAddressDTO deliveryAddressDTO = new DeliveryAddressDTO();
        deliveryAddressDTO.setId(300L);
        deliveryAddressDTO.setStreet("Main Street");
        deliveryAddressDTO.setHouseNumber(123);
        deliveryAddressDTO.setCity("Springfield");
        deliveryAddressDTO.setPostcode("12345");
        deliveryAddressDTO.setCountry("USA");

        // Act
        OrderDTO dto = new OrderDTO(1L, true, 100L, 200L, deliveryAddressDTO);

        // Assert
        assertEquals(1L, dto.getId());
        assertTrue(dto.isFulfilled());
        assertEquals(100L, dto.getCustomerId());
        assertEquals(200L, dto.getRestaurantId());
        assertEquals(deliveryAddressDTO, dto.getDeliveryAddress());

        // Act again
        dto.setId(2L);
        dto.setFulfilled(false);
        dto.setCustomerId(101L);
        dto.setRestaurantId(201L);

        DeliveryAddressDTO newDeliveryAddressDTO = new DeliveryAddressDTO();
        newDeliveryAddressDTO.setId(301L);
        newDeliveryAddressDTO.setStreet("Second Street");
        newDeliveryAddressDTO.setHouseNumber(456);
        newDeliveryAddressDTO.setCity("Shelbyville");
        newDeliveryAddressDTO.setPostcode("67890");
        newDeliveryAddressDTO.setCountry("USA");

        dto.setDeliveryAddress(newDeliveryAddressDTO);

        // Assert updated values
        assertEquals(2L, dto.getId());
        assertFalse(dto.isFulfilled());
        assertEquals(101L, dto.getCustomerId());
        assertEquals(201L, dto.getRestaurantId());
        assertEquals(newDeliveryAddressDTO, dto.getDeliveryAddress());
    }

    @Test
    void testOrderInputDTOConstructorsAndSetters() {
        // Act
        OrderInputDTO inputDto = new OrderInputDTO(true, 100L, 200L, 300L);

        // Assert
        assertTrue(inputDto.isFulfilled());
        assertEquals(100L, inputDto.getCustomerId());
        assertEquals(200L, inputDto.getRestaurantId());
        assertEquals(300L, inputDto.getDeliveryAddressId());

        // Act again
        inputDto.setFulfilled(false);
        inputDto.setCustomerId(101L);
        inputDto.setRestaurantId(201L);
        inputDto.setDeliveryAddressId(301L);

        // Assert updated values
        assertFalse(inputDto.isFulfilled());
        assertEquals(101L, inputDto.getCustomerId());
        assertEquals(201L, inputDto.getRestaurantId());
        assertEquals(301L, inputDto.getDeliveryAddressId());
    }
}
