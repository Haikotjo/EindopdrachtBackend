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

        // Test getters
        assertEquals("Jane Doe", userInputDTO.getName());
        assertEquals("jane.doe@example.com", userInputDTO.getEmail());
        assertEquals("securepassword", userInputDTO.getPassword());
        assertEquals(roles, userInputDTO.getRoles());
        assertEquals("555-6789", userInputDTO.getPhoneNumber());
    }
}
