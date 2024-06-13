package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        DeliveryAddress address = new DeliveryAddress();
        address.setStreet("Main Street 123");
        address.setCity("Springfield");
        address.setPostcode("12345");
        address.setCountry("USA");
        setField(address, "id", 1L);
        user.setDeliveryAddress(address);

        Restaurant restaurant = new Restaurant();
        setField(restaurant, "id", 1L);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        restaurant.setPhoneNumber("555-6789");

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        setField(order1, "id", 1L);
        order1.setCustomer(user); // Voeg de klant toe aan de bestelling
        order1.setRestaurant(restaurant); // Voeg het restaurant toe aan de bestelling
        orders.add(order1);
        user.setOrders(orders);

        Role role = new Role(UserRole.CUSTOMER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        // Act
        UserDTO userDTO = UserMapper.toUserDTO(user);

        // Assert
        assertEquals("John Doe", userDTO.getName());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals(List.of("CUSTOMER"), userDTO.getRoles());
        assertEquals("555-1234", userDTO.getPhoneNumber());
        assertNotNull(userDTO.getDeliveryAddress());
        assertEquals("Main Street 123", userDTO.getDeliveryAddress().getStreet());
        assertEquals("Springfield", userDTO.getDeliveryAddress().getCity());

        // Check orders
        assertNotNull(userDTO.getOrders());
        assertEquals(1, userDTO.getOrders().size());
        assertEquals(1L, userDTO.getOrders().get(0).getId());
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

        DeliveryAddressInputDTO addressInputDTO = new DeliveryAddressInputDTO();
        addressInputDTO.setStreet("Second Street 456");
        addressInputDTO.setCity("Shelbyville");
        addressInputDTO.setPostcode("67890");
        addressInputDTO.setCountry("USA");
        userInputDTO.setDeliveryAddress(addressInputDTO);

        // Act
        User user = UserMapper.toUser(userInputDTO);

        // Assert
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("securepassword", user.getPassword());
        assertEquals(1, user.getRoles().size());
        assertEquals(UserRole.OWNER, user.getRoles().iterator().next().getRolename());
        assertEquals("555-6789", user.getPhoneNumber());
        assertNotNull(user.getDeliveryAddress());
        assertEquals("Second Street 456", user.getDeliveryAddress().getStreet());
        assertEquals("Shelbyville", user.getDeliveryAddress().getCity());
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
        user.setDeliveryAddress(null);

        Restaurant restaurant = new Restaurant();
        setField(restaurant, "id", 1L);
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("123 Test St");
        restaurant.setPhoneNumber("555-6789");

        List<Order> orders = new ArrayList<>();
        Order order1 = new Order();
        setField(order1, "id", 1L);
        order1.setCustomer(user); // Voeg de klant toe aan de bestelling
        order1.setRestaurant(restaurant); // Voeg het restaurant toe aan de bestelling
        orders.add(order1);
        user.setOrders(orders);

        Role role = new Role(UserRole.CUSTOMER);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        // Act
        UserDTO userDTO = UserMapper.toUserDTO(user);

        // Assert
        assertEquals("John Doe", userDTO.getName());
        assertNull(userDTO.getDeliveryAddress(), "DeliveryAddress moet null zijn als de User geen adres heeft.");
        assertEquals(List.of("CUSTOMER"), userDTO.getRoles());

        // Check orders
        assertNotNull(userDTO.getOrders());
        assertEquals(1, userDTO.getOrders().size());
        assertEquals(1L, userDTO.getOrders().get(0).getId());
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }
}
