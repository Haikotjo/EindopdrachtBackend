package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDTOTest {

    @Test
    public void testUserDTO() {
        // Create an instance of UserDTO using the constructor
        UserDTO userDTO = new UserDTO(1L, "John Doe", "john.doe@example.com", UserRole.CUSTOMER, "123 Main St", "123-456-7890");

        // Assert initial values
        assertEquals(1L, userDTO.getId());
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals(UserRole.CUSTOMER, userDTO.getRole()); // Direct comparison of enum values
        assertEquals("123 Main St", userDTO.getAddress());
        assertEquals("123-456-7890", userDTO.getPhoneNumber());

        // Test updating values
        userDTO.setName("Jane Doe");
        assertEquals("Jane Doe", userDTO.getName());

        userDTO.setEmail("jane.doe@example.com");
        assertEquals("jane.doe@example.com", userDTO.getEmail());

        userDTO.setRole(UserRole.OWNER); // Directly set to another enum value from the model
        assertEquals(UserRole.OWNER, userDTO.getRole());

        userDTO.setAddress("456 Elm St");
        assertEquals("456 Elm St", userDTO.getAddress());

        userDTO.setPhoneNumber("987-654-3210");
        assertEquals("987-654-3210", userDTO.getPhoneNumber());
    }
}
