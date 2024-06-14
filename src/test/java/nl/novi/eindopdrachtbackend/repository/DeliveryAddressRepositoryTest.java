package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class DeliveryAddressRepositoryTest {

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private UserRepository userRepository;

    private DeliveryAddress address;

    @BeforeEach
    void setUp() {
        // Maak eerst een User aan, omdat DeliveryAddress een User nodig heeft
        Role customerRole = new Role(UserRole.CUSTOMER);
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);

        User user = new User("TestUser", "test@example.com", "testpass", roles, "1234567890");
        user = userRepository.save(user);  // Sla de User op om een ID te krijgen

        // Maak nu de DeliveryAddress aan met de opgeslagen User
        address = new DeliveryAddress("Example Street", 123, "Example City", "1234AB", "Example Country");
        address.setUser(user);  // Koppel de User aan de DeliveryAddress
        address = deliveryAddressRepository.save(address);  // Sla de DeliveryAddress op
    }

    @Test
    void testFindById() {
        DeliveryAddress foundAddress = deliveryAddressRepository.findById(address.getId()).orElse(null);
        assertNotNull(foundAddress);
        assertEquals(address.getStreet(), foundAddress.getStreet());
        assertEquals(address.getHouseNumber(), foundAddress.getHouseNumber());
        assertEquals(address.getCity(), foundAddress.getCity());
        assertEquals(address.getPostcode(), foundAddress.getPostcode());
        assertEquals(address.getCountry(), foundAddress.getCountry());
    }
}
