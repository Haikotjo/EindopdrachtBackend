package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryAddressDTOTest {

    @Test
    void testDeliveryAddressDTOConstructorsAndSetters() {
        // Test constructor with arguments
        DeliveryAddressDTO addressDTO = new DeliveryAddressDTO(1L, "1234 Main St", 123, "Anytown", "98765", "Countryland", 2L);

        // Test getters
        assertEquals(1L, addressDTO.getId());
        assertEquals("1234 Main St", addressDTO.getStreet());
        assertEquals(123, addressDTO.getHouseNumber());
        assertEquals("Anytown", addressDTO.getCity());
        assertEquals("98765", addressDTO.getPostcode());
        assertEquals("Countryland", addressDTO.getCountry());
        assertEquals(2L, addressDTO.getUserId());

        // Test setters
        addressDTO.setId(3L);
        addressDTO.setStreet("5678 Main St");
        addressDTO.setHouseNumber(456);
        addressDTO.setCity("Springfield");
        addressDTO.setPostcode("54321");
        addressDTO.setCountry("Freedonia");
        addressDTO.setUserId(1L);

        // Test updated values
        assertEquals(3L, addressDTO.getId());
        assertEquals("5678 Main St", addressDTO.getStreet());
        assertEquals(456, addressDTO.getHouseNumber());
        assertEquals("Springfield", addressDTO.getCity());
        assertEquals("54321", addressDTO.getPostcode());
        assertEquals("Freedonia", addressDTO.getCountry());
        assertEquals(1L, addressDTO.getUserId());
    }
}
