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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void testUserOrderRelation() {
        // Make User
        User user = new User("Jan Jansen", "jan@example.com", "password123", User.Role.CUSTOMER, "Main Street 1", "0612345678");
        userRepository.saveAndFlush(user);

        // Make Restaurant
        Restaurant restaurant = new Restaurant("TestRestaurant", "Restaurant Street", "9876543210");
        restaurantRepository.saveAndFlush(restaurant);

        // Make DeliveryAddress
        DeliveryAddress deliveryAddress = new DeliveryAddress("Delivery Street", 1, "Delivery City", 1234, "1234AB", "Delivery Country");
        deliveryAddress.setUser(user);
        deliveryAddressRepository.saveAndFlush(deliveryAddress);

        // Make order and attach a User
        Order order = new Order(user, restaurant, deliveryAddress, true);
        user.addOrder(order);
        orderRepository.saveAndFlush(order);

        // verify order correctly attached to User
        User savedUser = userRepository.findById(user.getId()).orElseThrow();
        assertTrue(savedUser.getOrders().contains(order), "De order is niet gekoppeld aan de gebruiker.");
    }
}
