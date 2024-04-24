package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserInputDTOTest {

    @Test
    public void testUserInputDTO() {
        // Create an instance of UserInputDTO
        UserInputDTO userInputDTO = new UserInputDTO();

        // Set values using setters
        userInputDTO.setName("John Doe");
        userInputDTO.setEmail("john.doe@example.com");
        userInputDTO.setPassword("strongpassword");
        userInputDTO.setRole(UserRole.CUSTOMER);
        userInputDTO.setPhoneNumber("123-456-7890");

        // Optionally include a test for DeliveryAddressDTO if needed
        DeliveryAddressInputDTO address = new DeliveryAddressInputDTO();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setCountry("USA");
        address.setPostcode("12345");
        address.setHouseNumber(123);
        userInputDTO.setDeliveryAddress(address);

        // Assert the values using getters
        assertEquals("John Doe", userInputDTO.getName(), "Name did not match");
        assertEquals("john.doe@example.com", userInputDTO.getEmail(), "Email did not match");
        assertEquals("strongpassword", userInputDTO.getPassword(), "Password did not match");
        assertEquals(UserRole.CUSTOMER, userInputDTO.getRole(), "Role did not match");
        assertEquals("123-456-7890", userInputDTO.getPhoneNumber(), "Phone number did not match");

        // Assert DeliveryAddress properties
        assertNotNull(userInputDTO.getDeliveryAddress(), "Delivery address should not be null");
        assertEquals("123 Main St", userInputDTO.getDeliveryAddress().getStreet(), "Street did not match");
        assertEquals("Anytown", userInputDTO.getDeliveryAddress().getCity(), "City did not match");
        assertEquals("USA", userInputDTO.getDeliveryAddress().getCountry(), "Country did not match");
        assertEquals("12345", userInputDTO.getDeliveryAddress().getPostcode(), "Postcode did not match");
        assertEquals(123, userInputDTO.getDeliveryAddress().getHouseNumber(), "House number did not match");
    }
}
