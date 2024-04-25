package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryAddressTest {

    @Test
    public void testDeliveryAddressConstructor() {
        // Create an instance of DeliveryAddress using the constructor
        DeliveryAddress address = new DeliveryAddress("Baker Street", 221, "London", "NW1", "UK");

        // Verify that the constructor correctly initializes the fields
        assertEquals("Baker Street", address.getStreet(), "The street does not match");
        assertEquals(221, address.getHouseNumber(), "The house number does not match");
        assertEquals("London", address.getCity(), "The city does not match");
        assertEquals("NW1", address.getPostcode(), "The postcode does not match");
        assertEquals("UK", address.getCountry(), "The country does not match");
    }
}
