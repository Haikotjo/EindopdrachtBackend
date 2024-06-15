package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    public void toUserDTOTest() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        User user = new User();
        setField(user, "id", 1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("555-1234");

        Role role = new Role(UserRole.CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Act
        UserDTO userDTO = UserMapper.toUserDTO(user);

        // Assert
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals(List.of("CUSTOMER"), userDTO.getRoles());
        assertEquals("555-1234", userDTO.getPhoneNumber());
    }

    @Test
    public void toUserTest() {
        // Arrange
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setName("Jane Doe");
        userInputDTO.setEmail("jane.doe@example.com");
        userInputDTO.setPassword("securepassword");
        userInputDTO.setPhoneNumber("555-6789");
        userInputDTO.setRoles(List.of("OWNER"));

        // Act
        User user = UserMapper.toUser(userInputDTO);

        // Assert
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("securepassword", user.getPassword());
        assertEquals(1, user.getRoles().size());
        assertEquals(UserRole.OWNER, user.getRoles().iterator().next().getRolename());
        assertEquals("555-6789", user.getPhoneNumber());
    }

    @Test
    public void toUserDTONullAddressTest() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        User user = new User();
        setField(user, "id", 1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("555-1234");

        Role role = new Role(UserRole.CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Act
        UserDTO userDTO = UserMapper.toUserDTO(user);

        // Assert
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals(List.of("CUSTOMER"), userDTO.getRoles());
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
}
