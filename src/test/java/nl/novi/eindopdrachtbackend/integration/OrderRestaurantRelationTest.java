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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderRestaurantRelationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Test
    @Transactional
    public void testOrderLinksBackToRestaurantCorrectly() {
        // Setup User
        User user = new User("Jan Jansen", "jan@example.com", "password123", User.Role.OWNER, "Main Street 1", "0612345678");
        user = userRepository.save(user);

        // Setup Restaurant
        Restaurant restaurant = new Restaurant("The Good Food Place", "123 Main St", "555-1234");
        restaurant = restaurantRepository.save(restaurant);

        // Setup DeliveryAddress
        DeliveryAddress deliveryAddress = new DeliveryAddress("Delivery Street", 1, "Delivery City", 1234, "1234AB", "Delivery Country");
        deliveryAddress.setUser(user);
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // Create an Order and link it to User, Restaurant, and DeliveryAddress
        Order order = new Order(user, restaurant, deliveryAddress, true);
        order = orderRepository.save(order);

        // Retrieve the saved Order and verify its relationship to the Restaurant
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow();
        assertEquals(restaurant.getId(), savedOrder.getRestaurant().getId(), "De order is niet correct gekoppeld aan het restaurant.");
    }
}
