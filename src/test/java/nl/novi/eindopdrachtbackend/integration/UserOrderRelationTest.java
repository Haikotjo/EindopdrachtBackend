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
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableTransactionManagement
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
    void testUserOrderAssociation() {
        // Create and save a User
        User user = new User("Eva Evers", "eva@example.com", "password123", UserRole.CUSTOMER, "0698765432");
        user = userRepository.save(user);

        // Create and save a Restaurant
        Restaurant restaurant = new Restaurant("Tasty Meals", "456 Food Ave", "555-6789");
        restaurant = restaurantRepository.save(restaurant);

        // Create and save a DeliveryAddress
        DeliveryAddress deliveryAddress = new DeliveryAddress("Food Street", 10, "Food City", "12345", "Country");
        deliveryAddress.setUser(user);
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // Create and save an Order
        Order order = new Order(user, restaurant, deliveryAddress, false);
        order = orderRepository.save(order);

        // Add the order to the User (bi-directional relationship)
        user.getOrders().add(order);
        userRepository.save(user);

        // Reload to verify
        User foundUser = userRepository.findById(user.getId()).orElseThrow();
        Order foundOrder = orderRepository.findById(order.getId()).orElseThrow();

        assertTrue(foundUser.getOrders().contains(order), "Order is not linked correctly to the user.");
        assertEquals(user, foundOrder.getCustomer(), "User is not linked correctly as the customer of the order.");
    }
}
