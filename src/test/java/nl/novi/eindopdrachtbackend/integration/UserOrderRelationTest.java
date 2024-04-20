package nl.novi.eindopdrachtbackend.integration;

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

@SpringBootTest
class UserOrderRelationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Test
    public void testUserOrderRelation() {
        // Make User
        User user = new User();
        user.setName("Jan Jansen");
        user.setEmail("jan@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.CUSTOMER);
        user.setAddress("Main Street 1");
        user.setPhoneNumber("0612345678");
        user = userRepository.save(user);

        // Make Restaurant
        Restaurant restaurant = new Restaurant("TestRestaurant", "Restaurant Street", "9876543210");
        restaurant = restaurantRepository.save(restaurant);

        // Make DeliveryAddress
        DeliveryAddress deliveryAddress = new DeliveryAddress("Delivery Street", 1, "Delivery City", 1234, "1234AB", "Delivery Country");
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // Make order
        Order order = new Order(user, restaurant, deliveryAddress, true);
        user.addOrder(order);

        // Save order
        order = orderRepository.save(order);
        user = userRepository.save(user);

        // Get saved User and verify relation
        User savedUser = userRepository.findById(user.getId()).orElseThrow();
        assertTrue(savedUser.getOrders().contains(order), "The order is not linked to the user.");
    }
}
