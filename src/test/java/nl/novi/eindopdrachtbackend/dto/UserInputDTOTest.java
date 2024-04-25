package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.Test;
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

        // Set values using setters
        userInputDTO.setName("Jane Doe");
        userInputDTO.setEmail("jane.doe@example.com");
        userInputDTO.setPassword("securepassword");
        userInputDTO.setRole(UserRole.OWNER);
        userInputDTO.setPhoneNumber("555-6789");
        userInputDTO.setDeliveryAddress(addressDTO);

        // Test getters
        assertEquals("Jane Doe", userInputDTO.getName());
        assertEquals("jane.doe@example.com", userInputDTO.getEmail());
        assertEquals("securepassword", userInputDTO.getPassword());
        assertEquals(UserRole.OWNER, userInputDTO.getRole());
        assertEquals("555-6789", userInputDTO.getPhoneNumber());
        assertNotNull(userInputDTO.getDeliveryAddress());
        assertEquals("Second Street 456", userInputDTO.getDeliveryAddress().getStreet());
        assertEquals("Shelbyville", userInputDTO.getDeliveryAddress().getCity());
        assertEquals("67890", userInputDTO.getDeliveryAddress().getPostcode());
        assertEquals("USA", userInputDTO.getDeliveryAddress().getCountry());
    }
}
