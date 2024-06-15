package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role(UserRole.CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        DeliveryAddress address = new DeliveryAddress("123 Main St", 10, "Anytown", "A1B2C3", "CountryLand");
        user = new User("John Doe", "johndoe@example.com", "password123", roles, "555-1234", new ArrayList<>(), address);
    }

    @Test
    void testUserGettersAndSetters() {
        // Test initial values from constructor
        assertEquals("John Doe", user.getName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertTrue(user.getRoles().contains(role));
        assertEquals("555-1234", user.getPhoneNumber());

        // Test setters
        Role newRole = new Role(UserRole.OWNER);
        Set<Role> newRoles = new HashSet<>();
        newRoles.add(newRole);
        user.setName("Jane Doe");
        user.setEmail("janedoe@example.com");
        user.setPassword("newPassword");
        user.setRoles(newRoles);
        user.setPhoneNumber("555-5678");

        // Test updated values
        assertEquals("Jane Doe", user.getName());
        assertEquals("janedoe@example.com", user.getEmail());
        assertEquals("newPassword", user.getPassword());
        assertTrue(user.getRoles().contains(newRole));
        assertEquals("555-5678", user.getPhoneNumber());
    }

    @Test
    void testUserDeliveryAddressAssociation() {
        DeliveryAddress address = new DeliveryAddress("123 Main St", 10, "Anytown", "A1B2C3", "CountryLand");
        user.setDeliveryAddress(address);

        assertEquals(address, user.getDeliveryAddress());
        assertEquals(user, user.getDeliveryAddress().getUser());  // Controleer of de relatie correct is ingesteld
    }

    @Test
    void testUserOrdersAssociation() {
        Order order = new Order();
        user.setOrders(new ArrayList<>());
        user.getOrders().add(order);
        order.setCustomer(user);

        assertTrue(user.getOrders().contains(order));
        assertEquals(user, order.getCustomer());
    }

    @Test
    void testUserRestaurantAssociation() {
        Restaurant restaurant = new Restaurant("Gusto", "123 Food St", "123-456-7890");
        user.setRestaurant(restaurant);

        assertEquals(restaurant, user.getRestaurant());
        assertNull(restaurant.getOwner()); // Controleer of de relatie aanvankelijk correct is ingesteld

        restaurant.setOwner(user); // Stel de wederzijdse relatie expliciet in
        assertEquals(user, restaurant.getOwner()); // Verifieer dat de relatie wederzijds correct is ingesteld
    }
}
