package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    @Test
    void testUserDTOConstructorsAndSetters() {
        // Test constructor with arguments
        UserDTO userDTO = new UserDTO(1L, "John Doe", "john.doe@example.com", UserRole.CUSTOMER, "555-1234");

        // Test getters
        assertEquals(1L, userDTO.getId());
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals(UserRole.CUSTOMER, userDTO.getRole());
        assertEquals("555-1234", userDTO.getPhoneNumber());

        // Test setters
        userDTO.setId(2L);
        userDTO.setName("Jane Doe");
        userDTO.setEmail("jane.doe@example.com");
        userDTO.setRole(UserRole.OWNER);
        userDTO.setPhoneNumber("555-5678");

        // Test updated values
        assertEquals(2L, userDTO.getId());
        assertEquals("Jane Doe", userDTO.getName());
        assertEquals("jane.doe@example.com", userDTO.getEmail());
        assertEquals(UserRole.OWNER, userDTO.getRole());
        assertEquals("555-5678", userDTO.getPhoneNumber());
    }
}
