package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryAddressDTOTest {

    @Test
    public void testDeliveryAddressDTO() {
        // Create an instance of DeliveryAddressDTO
        DeliveryAddressDTO addressDTO = new DeliveryAddressDTO();

        // Set values using setters
        addressDTO.setId(1L);
        addressDTO.setStreet("Main Street");
        addressDTO.setHouseNumber(100);
        addressDTO.setCity("Springfield");
        addressDTO.setPostcode("12345");
        addressDTO.setCountry("USA");
        addressDTO.setUserId(2L);

        // Assert the values using getters
        assertEquals(1L, addressDTO.getId(), "ID did not match");
        assertEquals("Main Street", addressDTO.getStreet(), "Street did not match");
        assertEquals(100, addressDTO.getHouseNumber(), "House number did not match");
        assertEquals("Springfield", addressDTO.getCity(), "City did not match");
        assertEquals("12345", addressDTO.getPostcode(), "Postcode did not match");
        assertEquals("USA", addressDTO.getCountry(), "Country did not match");
        assertEquals(2L, addressDTO.getUserId(), "User ID did not match");
    }
}
