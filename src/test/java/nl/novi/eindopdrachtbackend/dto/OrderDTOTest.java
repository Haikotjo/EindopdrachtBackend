package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

        List<MenuItemDTO> menuItems = new ArrayList<>();
        MenuItemDTO menuItemDTO = new MenuItemDTO(1L, "Pizza", 9.99, "Delicious pizza", "image.jpg", null);
        menuItems.add(menuItemDTO);

        LocalDateTime orderDateTime = LocalDateTime.now();

        // Act
        OrderDTO dto = new OrderDTO(1L, true, 100L, 200L, deliveryAddressDTO, menuItems, 9.99, orderDateTime);

        // Assert
        assertEquals(1L, dto.getId());
        assertTrue(dto.isFulfilled());
        assertEquals(100L, dto.getCustomerId());
        assertEquals(200L, dto.getRestaurantId());
        assertEquals(deliveryAddressDTO, dto.getDeliveryAddress());
        assertEquals(menuItems, dto.getMenuItems());
        assertEquals(9.99, dto.getTotalPrice(), 0.001);
        assertEquals(orderDateTime, dto.getOrderDateTime());

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

        List<MenuItemDTO> newMenuItems = new ArrayList<>();
        MenuItemDTO newMenuItemDTO = new MenuItemDTO(2L, "Burger", 12.99, "Tasty burger", "image2.jpg", null);
        newMenuItems.add(newMenuItemDTO);

        LocalDateTime newOrderDateTime = LocalDateTime.now();

        dto.setDeliveryAddress(newDeliveryAddressDTO);
        dto.setMenuItems(newMenuItems);
        dto.setTotalPrice(12.99);
        dto.setOrderDateTime(newOrderDateTime);

        // Assert updated values
        assertEquals(2L, dto.getId());
        assertFalse(dto.isFulfilled());
        assertEquals(101L, dto.getCustomerId());
        assertEquals(201L, dto.getRestaurantId());
        assertEquals(newDeliveryAddressDTO, dto.getDeliveryAddress());
        assertEquals(newMenuItems, dto.getMenuItems());
        assertEquals(12.99, dto.getTotalPrice(), 0.001);
        assertEquals(newOrderDateTime, dto.getOrderDateTime());
    }

    @Test
    void testOrderInputDTOConstructorsAndSetters() {
        // Arrange
        List<Long> menuItemIds = new ArrayList<>();
        menuItemIds.add(1L);

        // Act
        OrderInputDTO inputDto = new OrderInputDTO(true, 100L, 200L, 300L, menuItemIds);

        // Assert
        assertTrue(inputDto.isFulfilled());
        assertEquals(100L, inputDto.getCustomerId());
        assertEquals(200L, inputDto.getRestaurantId());
        assertEquals(300L, inputDto.getDeliveryAddressId());
        assertEquals(menuItemIds, inputDto.getMenuItemIds());

        // Act again
        inputDto.setFulfilled(false);
        inputDto.setCustomerId(101L);
        inputDto.setRestaurantId(201L);
        inputDto.setDeliveryAddressId(301L);

        List<Long> newMenuItemIds = new ArrayList<>();
        newMenuItemIds.add(2L);
        inputDto.setMenuItemIds(newMenuItemIds);

        // Assert updated values
        assertFalse(inputDto.isFulfilled());
        assertEquals(101L, inputDto.getCustomerId());
        assertEquals(201L, inputDto.getRestaurantId());
        assertEquals(301L, inputDto.getDeliveryAddressId());
        assertEquals(newMenuItemIds, inputDto.getMenuItemIds());
    }
}
