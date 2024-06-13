package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserInputDTOTest {

    @Test
    void testUserInputDTOGettersAndSetters() {
        // Initialize the DTO with no args constructor
        UserInputDTO userInputDTO = new UserInputDTO();

        // Create a DeliveryAddressInputDTO
        DeliveryAddressInputDTO addressDTO = new DeliveryAddressInputDTO();
        addressDTO.setStreet("Second Street 456");
        addressDTO.setHouseNumber(789);
        addressDTO.setCity("Shelbyville");
        addressDTO.setPostcode("67890");
        addressDTO.setCountry("USA");

        // Create roles list
        List<String> roles = new ArrayList<>();
        roles.add("OWNER");
        roles.add("ADMIN");

        // Set values using setters
        userInputDTO.setName("Jane Doe");
        userInputDTO.setEmail("jane.doe@example.com");
        userInputDTO.setPassword("securepassword");
        userInputDTO.setRoles(roles);
        userInputDTO.setPhoneNumber("555-6789");
        userInputDTO.setDeliveryAddress(addressDTO);

        // Test getters
        assertEquals("Jane Doe", userInputDTO.getName());
        assertEquals("jane.doe@example.com", userInputDTO.getEmail());
        assertEquals("securepassword", userInputDTO.getPassword());
        assertEquals(roles, userInputDTO.getRoles());
        assertEquals("555-6789", userInputDTO.getPhoneNumber());
        assertNotNull(userInputDTO.getDeliveryAddress());
        assertEquals("Second Street 456", userInputDTO.getDeliveryAddress().getStreet());
        assertEquals("Shelbyville", userInputDTO.getDeliveryAddress().getCity());
        assertEquals("67890", userInputDTO.getDeliveryAddress().getPostcode());
        assertEquals("USA", userInputDTO.getDeliveryAddress().getCountry());
    }
}
