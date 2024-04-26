package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantInputDTOTest {

    @Test
    void testRestaurantInputDTOGettersAndSetters() {
        // Initialize the DTO with no args constructor
        RestaurantInputDTO restaurantInputDTO = new RestaurantInputDTO();

        // Set values using setters
        restaurantInputDTO.setName("Test Restaurant");
        restaurantInputDTO.setAddress("Test Address");
        restaurantInputDTO.setPhoneNumber("1234567890");

        // Test getters
        assertEquals("Test Restaurant", restaurantInputDTO.getName());
        assertEquals("Test Address", restaurantInputDTO.getAddress());
        assertEquals("1234567890", restaurantInputDTO.getPhoneNumber());

        // Update and re-test values to ensure setters are working properly
        restaurantInputDTO.setName("Updated Test Restaurant");
        restaurantInputDTO.setAddress("Updated Test Address");
        restaurantInputDTO.setPhoneNumber("0987654321");

        // Test updated values
        assertEquals("Updated Test Restaurant", restaurantInputDTO.getName());
        assertEquals("Updated Test Address", restaurantInputDTO.getAddress());
        assertEquals("0987654321", restaurantInputDTO.getPhoneNumber());
    }
}
