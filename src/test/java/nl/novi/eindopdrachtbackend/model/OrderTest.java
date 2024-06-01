package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void testOrderConstructor() {
        // Create an instance of Order using the constructor
        User customer = new User("John Doe", "john.doe@example.com", "password", UserRole.CUSTOMER, "1234567890");
        Restaurant restaurant = new Restaurant("Italian Bistro", "123 Main Street", "555-1234");
        DeliveryAddress deliveryAddress = new DeliveryAddress("Main Street", 123, "Springfield", "12345", "USA");

        Order order = new Order(customer, restaurant, deliveryAddress, false);

        // Verify that the constructor correctly initializes the fields
        assertEquals(customer, order.getCustomer(), "The customer does not match");
        assertEquals(restaurant, order.getRestaurant(), "The restaurant does not match");
        assertEquals(deliveryAddress, order.getDeliveryAddress(), "The delivery address does not match");
        assertFalse(order.isFulfilled(), "The fulfilled status does not match");
    }

    @Test
    public void testAddMenuItemToOrder() {
        // Arrange
        User customer = new User("John Doe", "john.doe@example.com", "password", UserRole.CUSTOMER, "1234567890");
        Restaurant restaurant = new Restaurant("Italian Bistro", "123 Main Street", "555-1234");
        DeliveryAddress deliveryAddress = new DeliveryAddress("Main Street", 123, "Springfield", "12345", "USA");

        Order order = new Order(customer, restaurant, deliveryAddress, false);
        MenuItem menuItem = new MenuItem("Margherita Pizza", 9.99, "Classic Margherita with tomato and cheese", "imagePath.jpg");

        Set<MenuItem> menuItems = new HashSet<>();
        menuItems.add(menuItem);
        order.setMenuItems(menuItems);

        // Act
        order.getMenuItems().add(menuItem);
        menuItem.getOrders().add(order);

        // Assert
        assertEquals(1, order.getMenuItems().size(), "The menu item was not added to the order");
        assertEquals(order, menuItem.getOrders().iterator().next(), "The order was not added to the menu item");
    }

    private void setIdUsingReflection(Object obj, Long idValue) throws NoSuchFieldException, IllegalAccessException {
        Field idField = obj.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(obj, idValue);
    }
}
