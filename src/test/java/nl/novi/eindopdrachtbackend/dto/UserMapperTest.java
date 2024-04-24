package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
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
        user.setPhoneNumber("555-1234");

        DeliveryAddress address = new DeliveryAddress();
        address.setStreet("Main Street 123");
        address.setCity("Springfield");
        address.setPostcode("12345");
        address.setCountry("USA");
        user.setDeliveryAddress(address);  // Setting the address for the user

        // Act
        UserDTO userDTO = UserMapper.toUserDTO(user);

        // Assert
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals(UserRole.CUSTOMER, userDTO.getRole());
        assertEquals("555-1234", userDTO.getPhoneNumber());
        assertNotNull(userDTO.getDeliveryAddress());
        assertEquals("Main Street 123", userDTO.getDeliveryAddress().getStreet());
        assertEquals("Springfield", userDTO.getDeliveryAddress().getCity());
    }

    @Test
    public void toUserTest() {
        // Arrange
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setName("Jane Doe");
        userInputDTO.setEmail("jane.doe@example.com");
        userInputDTO.setPassword("securepassword");
        userInputDTO.setRole(UserRole.OWNER);
        userInputDTO.setPhoneNumber("555-6789");

        DeliveryAddressInputDTO addressInputDTO = new DeliveryAddressInputDTO();
        addressInputDTO.setStreet("Second Street 456");
        addressInputDTO.setCity("Shelbyville");
        addressInputDTO.setPostcode("67890");
        addressInputDTO.setCountry("USA");
        userInputDTO.setDeliveryAddress(addressInputDTO);  // Setting the input address for the user

        // Act
        User user = UserMapper.toUser(userInputDTO);

        // Assert
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("securepassword", user.getPassword());
        assertEquals(UserRole.OWNER, user.getRole());
        assertEquals("555-6789", user.getPhoneNumber());
        assertNotNull(user.getDeliveryAddress());
        assertEquals("Second Street 456", user.getDeliveryAddress().getStreet());
        assertEquals("Shelbyville", user.getDeliveryAddress().getCity());
    }

    @Test
    public void toUserDTONullAddressTest() {
        // Arrange
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setRole(UserRole.CUSTOMER);
        user.setPhoneNumber("555-1234");
        user.setDeliveryAddress(null);  // Geen adres ingesteld

        // Act
        UserDTO userDTO = UserMapper.toUserDTO(user);

        // Assert
        assertEquals("John Doe", userDTO.getName());
        assertNull(userDTO.getDeliveryAddress(), "DeliveryAddress moet null zijn als de User geen adres heeft.");
    }
}
