package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserDeliveryAddressIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @Transactional
    void testOrderIsLinkedToDeliveryAddressCorrectly() {
        // Create and save a User with the correct Role
        User user = new User("Jan Jansen", "jan@example.com", "securepassword", User.Role.CUSTOMER, "User Street 1", "0612345678");
        user = userRepository.save(user);

        // Create and save a Restaurant
        Restaurant restaurant = new Restaurant("The Good Food Place", "123 Main St", "555-1234");
        restaurant = restaurantRepository.save(restaurant);

        // Create and save a DeliveryAddress
        DeliveryAddress deliveryAddress = new DeliveryAddress("Delivery Street", 10, "Delivery City", 1000, "1000AB", "Delivery Country");
        deliveryAddress.setUser(user);  // Link user to the delivery address
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // Link delivery address back to user
        user.setDeliveryAddress(deliveryAddress);
        userRepository.save(user);

        // Create and save an Order
        Order order = new Order(user, restaurant, deliveryAddress, false);
        order = orderRepository.save(order);

        // Retrieve the saved Order and verify its relationship to the DeliveryAddress
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow();
        assertEquals(deliveryAddress.getId(), savedOrder.getDeliveryAddress().getId(), "De order is niet correct gekoppeld aan het bezorgadres.");
    }
}
