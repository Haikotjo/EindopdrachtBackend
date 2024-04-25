package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryAddressMapperTest {

    @Test
    public void toDeliveryAddressDTOTest() {
        // Arrange
        DeliveryAddress address = new DeliveryAddress();
        address.setStreet("Main Street 123");
        address.setHouseNumber(456);
        address.setCity("Springfield");
        address.setPostcode("12345");
        address.setCountry("USA");

        // Act
        DeliveryAddressDTO dto = DeliveryAddressMapper.toDeliveryAddressDTO(address);

        // Assert
        assertNotNull(dto);
        assertEquals("Main Street 123", dto.getStreet());
        assertEquals(456, dto.getHouseNumber());
        assertEquals("Springfield", dto.getCity());
        assertEquals("12345", dto.getPostcode());
        assertEquals("USA", dto.getCountry());
    }

    @Test
    public void toDeliveryAddressTest() {
        // Arrange
        DeliveryAddressInputDTO inputDTO = new DeliveryAddressInputDTO();
        inputDTO.setStreet("Second Street 456");
        inputDTO.setHouseNumber(789);
        inputDTO.setCity("Shelbyville");
        inputDTO.setPostcode("67890");
        inputDTO.setCountry("USA");

        // Act
        DeliveryAddress address = DeliveryAddressMapper.toDeliveryAddress(inputDTO);

        // Assert
        assertNotNull(address);
        assertEquals("Second Street 456", address.getStreet());
        assertEquals(789, address.getHouseNumber());
        assertEquals("Shelbyville", address.getCity());
        assertEquals("67890", address.getPostcode());
        assertEquals("USA", address.getCountry());
    }

    @Test
    public void toDeliveryAddressDTONullAddressTest() {
        // Arrange
        DeliveryAddress address = null;

        // Act
        DeliveryAddressDTO dto = DeliveryAddressMapper.toDeliveryAddressDTO(address);

        // Assert
        assertNull(dto, "DeliveryAddressDTO moet null zijn als de DeliveryAddress null is.");
    }
}
