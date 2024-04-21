package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", "johndoe@example.com", "password123", UserRole.CUSTOMER, "555-1234");
    }

    @Test
    void testUserGettersAndSetters() {
        // Test initial values from constructor
        assertEquals("John Doe", user.getName());
        assertEquals("johndoe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(UserRole.CUSTOMER, user.getRole());
        assertEquals("555-1234", user.getPhoneNumber());

        // Test setters
        user.setName("Jane Doe");
        user.setEmail("janedoe@example.com");
        user.setPassword("newPassword");
        user.setRole(UserRole.OWNER);
        user.setPhoneNumber("555-5678");

        // Test updated values
        assertEquals("Jane Doe", user.getName());
        assertEquals("janedoe@example.com", user.getEmail());
        assertEquals("newPassword", user.getPassword());
        assertEquals(UserRole.OWNER, user.getRole());
        assertEquals("555-5678", user.getPhoneNumber());
    }

    @Test
    void testUserDeliveryAddressAssociation() {
        DeliveryAddress address = new DeliveryAddress("123 Main St", 10, "Anytown", 1234, "A1B2C3", "CountryLand");
        user.setDeliveryAddress(address);

        assertEquals(address, user.getDeliveryAddress());
        assertNull(user.getDeliveryAddress().getUser());  // Controleer of de relatie correct is ingesteld

        address.setUser(user);
        assertEquals(user, address.getUser()); // Zorg ervoor dat de relatie wederzijds is
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
}
