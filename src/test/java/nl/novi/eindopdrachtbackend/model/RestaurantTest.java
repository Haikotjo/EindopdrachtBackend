package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    @Test
    public void testRestaurantConstructor() {
        // Create an instance of Restaurant using the constructor
        Restaurant restaurant = new Restaurant("The Fat Duck", "High Street, Bray", "0123456789");

        // Verify that the constructor correctly initializes the fields
        assertEquals("The Fat Duck", restaurant.getName(), "The name does not match");
        assertEquals("High Street, Bray", restaurant.getAddress(), "The address does not match");
        assertEquals("0123456789", restaurant.getPhoneNumber(), "The phone number does not match");
    }
}