package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryAddressMapperTest {

    @Test
    public void toDeliveryAddressDTOTest() {
        // Arrange
        DeliveryAddress address = new DeliveryAddress();
        address.setStreet("Elm Street");
        address.setHouseNumber(123);
        address.setCity("Anytown");
        address.setPostcode("98765");
        address.setCountry("Neverland");

        // Simulate the DeliveryAddress already having a User
        User user = new User();
        address.setUser(user);  // Assume user is set with an ID elsewhere (like after saving in JPA)

        // Act
        DeliveryAddressDTO dto = DeliveryAddressMapper.toDeliveryAddressDTO(address);

        // Assert
        assertEquals("Elm Street", dto.getStreet());
        assertEquals(123, dto.getHouseNumber());
        assertEquals("Anytown", dto.getCity());
        assertEquals("98765", dto.getPostcode());
        assertEquals("Neverland", dto.getCountry());
        assertNull(dto.getUserId(), "UserId should be null if not explicitly set.");
    }

    @Test
    public void toDeliveryAddressTest() {
        // Arrange
        DeliveryAddressInputDTO inputDTO = new DeliveryAddressInputDTO();
        inputDTO.setStreet("Maple Street");
        inputDTO.setHouseNumber(456);
        inputDTO.setCity("Springfield");
        inputDTO.setPostcode("12345");
        inputDTO.setCountry("Freedonia");

        // Act
        DeliveryAddress address = DeliveryAddressMapper.toDeliveryAddress(inputDTO);

        // Assert
        assertEquals("Maple Street", address.getStreet());
        assertEquals(456, address.getHouseNumber());
        assertEquals("Springfield", address.getCity());
        assertEquals("12345", address.getPostcode());
        assertEquals("Freedonia", address.getCountry());
    }
}
