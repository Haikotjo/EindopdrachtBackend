package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    public void toUserDTOTest() {
        // Arrange
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");  // Normaal niet meegenomen in een DTO voor leesoperaties
        user.setRole(UserRole.CUSTOMER);
        user.setAddress("123 Main St");
        user.setPhoneNumber("555-1234");

        // Act
        UserDTO userDTO = UserMapper.toUserDTO(user);

        // Assert
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals(UserRole.CUSTOMER, userDTO.getRole());
        assertEquals("123 Main St", userDTO.getAddress());
        assertEquals("555-1234", userDTO.getPhoneNumber());
    }

    @Test
    public void toUserTest() {
        // Arrange
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setName("Jane Doe");
        userInputDTO.setEmail("jane.doe@example.com");
        userInputDTO.setPassword("securepassword");
        userInputDTO.setRole(UserRole.OWNER);
        userInputDTO.setAddress("456 Elm St");
        userInputDTO.setPhoneNumber("555-6789");

        // Act
        User user = UserMapper.toUser(userInputDTO);

        // Assert
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("securepassword", user.getPassword());
        assertEquals(UserRole.OWNER, user.getRole());
        assertEquals("456 Elm St", user.getAddress());
        assertEquals("555-6789", user.getPhoneNumber());
    }
}
