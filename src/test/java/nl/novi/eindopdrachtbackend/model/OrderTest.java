package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Role createRole(UserRole userRole) {
        return new Role(userRole);
    }

    @Test
    public void testOrderConstructor() {
        // Create roles
        Role customerRole = createRole(UserRole.CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

        // Create an instance of Order using the constructor
        User customer = new User("John Doe", "john.doe@example.com", "password", roles, "1234567890");
        Restaurant restaurant = new Restaurant("Italian Bistro", "123 Main Street", "555-1234");
        DeliveryAddress deliveryAddress = new DeliveryAddress("Main Street", 123, "Springfield", "12345", "USA");

        Order order = new Order(customer, restaurant, deliveryAddress, false);
        LocalDateTime now = LocalDateTime.now();

        // Verify that the constructor correctly initializes the fields
        assertEquals(customer, order.getCustomer(), "The customer does not match");
        assertEquals(restaurant, order.getRestaurant(), "The restaurant does not match");
        assertEquals(deliveryAddress, order.getDeliveryAddress(), "The delivery address does not match");
        assertFalse(order.isFulfilled(), "The fulfilled status does not match");
        assertNotNull(order.getOrderDateTime(), "The order date/time should not be null");
        assertTrue(order.getOrderDateTime().isAfter(now.minusSeconds(1)) && order.getOrderDateTime().isBefore(now.plusSeconds(1)),
                "The order date/time should be around the current time");
    }

    @Test
    public void testAddMenuItemToOrder() {
        // Arrange
        Role customerRole = createRole(UserRole.CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

        User customer = new User("John Doe", "john.doe@example.com", "password", roles, "1234567890");
        Restaurant restaurant = new Restaurant("Italian Bistro", "123 Main Street", "555-1234");
        DeliveryAddress deliveryAddress = new DeliveryAddress("Main Street", 123, "Springfield", "12345", "USA");

        Order order = new Order(customer, restaurant, deliveryAddress, false);
        MenuItem menuItem = new MenuItem("Margherita Pizza", 9.99, "Classic Margherita with tomato and cheese", "imagePath.jpg");

        Set<MenuItem> menuItems = new HashSet<>();
        menuItems.add(menuItem);
        order.setMenuItems(menuItems);

        // Act
        order.getMenuItems().add(menuItem);

        // Assert
        assertEquals(1, order.getMenuItems().size(), "The menu item was not added to the order");
        assertEquals(menuItem, order.getMenuItems().iterator().next(), "The menu item was not correctly added to the order");
        assertEquals(9.99, order.getTotalPrice(), 0.001, "The total price does not match the price of the menu item");
    }

    @Test
    public void testOrderDateTimeSetter() {
        // Arrange
        Role customerRole = createRole(UserRole.CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

        User customer = new User("John Doe", "john.doe@example.com", "password", roles, "1234567890");
        Restaurant restaurant = new Restaurant("Italian Bistro", "123 Main Street", "555-1234");
        DeliveryAddress deliveryAddress = new DeliveryAddress("Main Street", 123, "Springfield", "12345", "USA");

        Order order = new Order(customer, restaurant, deliveryAddress, false);
        LocalDateTime newDateTime = LocalDateTime.of(2022, 1, 1, 12, 0);

        // Act
        order.setOrderDateTime(newDateTime);

        // Assert
        assertEquals(newDateTime, order.getOrderDateTime(), "The order date/time does not match the set value");
    }

    private void setIdUsingReflection(Object obj, Long idValue) throws NoSuchFieldException, IllegalAccessException {
        Field idField = obj.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(obj, idValue);
    }
}
