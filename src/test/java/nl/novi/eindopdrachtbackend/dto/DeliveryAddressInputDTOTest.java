package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryAddressInputDTOTest {

    @Test
    void testDeliveryAddressInputDTO() {
        // Instantiate the DTO with default constructor
        DeliveryAddressInputDTO addressDTO = new DeliveryAddressInputDTO();

        // Set values using setters
        addressDTO.setStreet("Main Street");
        addressDTO.setHouseNumber(123);
        addressDTO.setCity("Springfield");
        addressDTO.setPostcode("12345");
        addressDTO.setCountry("USA");

        // Test getters
        assertEquals("Main Street", addressDTO.getStreet(), "Street getter or setter not working");
        assertEquals(123, addressDTO.getHouseNumber(), "House number getter or setter not working");
        assertEquals("Springfield", addressDTO.getCity(), "City getter or setter not working");
        assertEquals("12345", addressDTO.getPostcode(), "Postcode getter or setter not working");
        assertEquals("USA", addressDTO.getCountry(), "Country getter or setter not working");
    }
}
