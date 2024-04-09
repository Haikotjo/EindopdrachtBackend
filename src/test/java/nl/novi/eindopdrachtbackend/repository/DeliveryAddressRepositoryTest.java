package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class DeliveryAddressRepositoryTest {

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    private DeliveryAddress address;

    @BeforeEach
    void setUp() {
        address = new DeliveryAddress("Example Street", 123, "Example City", 1234, "1234AB", "Example Country");
        address = deliveryAddressRepository.save(address);
    }

    @Test
    void testFindById() {
        DeliveryAddress foundAddress = deliveryAddressRepository.findById(address.getId()).orElse(null);
        assertNotNull(foundAddress);
        assertEquals(address.getStreet(), foundAddress.getStreet());
        assertEquals(address.getHouseNumber(), foundAddress.getHouseNumber());
        assertEquals(address.getCity(), foundAddress.getCity());
        assertEquals(address.getPostcode(), foundAddress.getPostcode());
        assertEquals(address.getPostcodeNumber(), foundAddress.getPostcodeNumber());
        assertEquals(address.getCountry(), foundAddress.getCountry());
    }
}
