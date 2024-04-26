package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantDTOTest {

    @Test
    void testRestaurantDTOConstructorsAndSetters() {
        // Test constructor with arguments
        RestaurantDTO dto = new RestaurantDTO();
        dto.setName("Test Restaurant");
        dto.setAddress("Test Address");
        dto.setPhoneNumber("1234567890");

        // Test getters
        assertEquals("Test Restaurant", dto.getName());
        assertEquals("Test Address", dto.getAddress());
        assertEquals("1234567890", dto.getPhoneNumber());

        // Test setters
        dto.setName("Updated Test Restaurant");
        dto.setAddress("Updated Test Address");
        dto.setPhoneNumber("0987654321");

        // Test updated values
        assertEquals("Updated Test Restaurant", dto.getName());
        assertEquals("Updated Test Address", dto.getAddress());
        assertEquals("0987654321", dto.getPhoneNumber());
    }

    @Test
    void testRestaurantInputDTOConstructorsAndSetters() {
        // Test constructor with arguments
        RestaurantInputDTO inputDto = new RestaurantInputDTO();
        inputDto.setName("Input Test Restaurant");
        inputDto.setAddress("Input Test Address");
        inputDto.setPhoneNumber("0987654321");

        // Test getters
        assertEquals("Input Test Restaurant", inputDto.getName());
        assertEquals("Input Test Address", inputDto.getAddress());
        assertEquals("0987654321", inputDto.getPhoneNumber());

        // Test setters
        inputDto.setName("Updated Input Test Restaurant");
        inputDto.setAddress("Updated Input Test Address");
        inputDto.setPhoneNumber("0123456789");

        // Test updated values
        assertEquals("Updated Input Test Restaurant", inputDto.getName());
        assertEquals("Updated Input Test Address", inputDto.getAddress());
        assertEquals("0123456789", inputDto.getPhoneNumber());
    }
}
