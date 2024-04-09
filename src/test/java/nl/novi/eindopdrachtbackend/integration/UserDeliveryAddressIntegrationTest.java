package nl.novi.eindopdrachtbackend.integration;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserDeliveryAddressIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Test
    @Transactional
    public void testAddDeliveryAddressToUser() {
        // Create a new User
        User user = new User("TestUser", "test@example.com", "password", "USER", "Main Street", "1234567890");
        user = userRepository.save(user);

        // Create a new DeliveryAddress and link it to the User
        DeliveryAddress address = new DeliveryAddress("TestStreet", 1, "TestCity", 1234, "1234AB", "TestCountry");
        user.addDeliveryAddress(address); // Add the address to the User, this method also sets the relationship on the side of DeliveryAddress
        address = deliveryAddressRepository.save(address);

        // Reload the user to ensure you have the most recent state, including linked addresses
        User savedUser = userRepository.findById(user.getId()).orElseThrow();
        assertEquals(1, savedUser.getDeliveryAddresses().size());
        assertEquals("TestStreet", savedUser.getDeliveryAddresses().get(0).getStreet());
    }
}
