package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.*;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RestaurantOrdersRelationTest {

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
    public void testRestaurantLinksBackToOrderCorrectly() {
        // Setup Restaurant
        Restaurant restaurant = new Restaurant("The Good Food Place", "123 Main St", "555-1234");
        restaurant = restaurantRepository.save(restaurant);

        // Setup User
        User user = new User("Jan Jansen", "jan@example.com", "password123", UserRole.OWNER, "0612345678");
        user = userRepository.save(user);

        // Setup DeliveryAddress
        DeliveryAddress deliveryAddress = new DeliveryAddress("Delivery Street", 1, "Delivery City", "1234AB", "Delivery Country");
        deliveryAddress.setUser(user);
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // Create an Order and link it to User, Restaurant, and DeliveryAddress
        Order order = new Order(user, restaurant, deliveryAddress, true);
        restaurant.addOrder(order);

        // Refresh and retrieve the Restaurant to ensure it is up-to-date
        order = orderRepository.save(order);
        restaurant = restaurantRepository.save(restaurant);

        // Get saved User and verify relation
        Restaurant savedRestaurant = restaurantRepository.findById(restaurant.getId()).orElseThrow();
        assertTrue(savedRestaurant.getOrders().contains(order), "The order is not linked to the restaurant.");

    }
}
