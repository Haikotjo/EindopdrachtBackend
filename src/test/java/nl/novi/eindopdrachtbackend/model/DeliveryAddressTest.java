package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryAddressTest {

    @Test
    void shouldKeepCityAndStreet() {
        // Arrange
        DeliveryAddress d = new DeliveryAddress("Gracht", 244, "Amsterdam", 1515, "GG", "Netherlands");

        // Act
        String cityResult = d.getCity();
        String streetResult = d.getStreet();
        int houseNumberResult = d.getHouseNumber();
        String postcodeResult = d.getPostcode();
        int postcodeNumberResult = d.getPostcodeNumber();

        // Assert
        assertEquals("Amsterdam", cityResult);
        assertEquals("Gracht", streetResult);
        assertEquals("GG", postcodeResult);
        assertEquals(1515, postcodeNumberResult);
        assertEquals(244, houseNumberResult);
    }
}
