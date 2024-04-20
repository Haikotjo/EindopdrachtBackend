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
        userInputDTO.setRole(UserRole.CUSTOMER); // Use enum and convert to String for the role
        userInputDTO.setAddress("123 Main St");
        userInputDTO.setPhoneNumber("123-456-7890");

        // Assert the values using getters
        assertEquals("John Doe", userInputDTO.getName(), "Name did not match");
        assertEquals("john.doe@example.com", userInputDTO.getEmail(), "Email did not match");
        assertEquals("strongpassword", userInputDTO.getPassword(), "Password did not match");
        assertEquals(UserRole.CUSTOMER, userInputDTO.getRole(), "Role did not match"); // Using toString to compare String representation
        assertEquals("123 Main St", userInputDTO.getAddress(), "Address did not match");
        assertEquals("123-456-7890", userInputDTO.getPhoneNumber(), "Phone number did not match");
    }
}
