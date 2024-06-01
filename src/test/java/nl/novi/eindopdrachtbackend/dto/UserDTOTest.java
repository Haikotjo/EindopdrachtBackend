package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void testUserDTOConstructorsAndSetters() {
        DeliveryAddressDTO deliveryAddressDTO = new DeliveryAddressDTO();
        deliveryAddressDTO.setStreet("1234 Main St");

        List<OrderDTO> orders = new ArrayList<>();
        orders.add(new OrderDTO());

        // Test constructor with arguments
        UserDTO userDTO = new UserDTO(1L, "John Doe", "john.doe@example.com", UserRole.CUSTOMER, "555-1234", deliveryAddressDTO, orders);

        // Test getters
        assertEquals(1L, userDTO.getId());
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals(UserRole.CUSTOMER, userDTO.getRole());
        assertEquals("555-1234", userDTO.getPhoneNumber());
        assertSame(deliveryAddressDTO, userDTO.getDeliveryAddress());
        assertSame(orders, userDTO.getOrders());

        // Test setters
        userDTO.setId(2L);
        userDTO.setName("Jane Doe");
        userDTO.setEmail("jane.doe@example.com");
        userDTO.setRole(UserRole.OWNER);
        userDTO.setPhoneNumber("555-5678");
        DeliveryAddressDTO newDeliveryAddressDTO = new DeliveryAddressDTO();
        newDeliveryAddressDTO.setStreet("5678 Main St");
        userDTO.setDeliveryAddress(newDeliveryAddressDTO);

        List<OrderDTO> newOrders = new ArrayList<>();
        newOrders.add(new OrderDTO());
        userDTO.setOrders(newOrders);

        // Test updated values
        assertEquals(2L, userDTO.getId());
        assertEquals("Jane Doe", userDTO.getName());
        assertEquals("jane.doe@example.com", userDTO.getEmail());
        assertEquals(UserRole.OWNER, userDTO.getRole());
        assertEquals("555-5678", userDTO.getPhoneNumber());
        assertSame(newDeliveryAddressDTO, userDTO.getDeliveryAddress());
        assertSame(newOrders, userDTO.getOrders());
    }
}
