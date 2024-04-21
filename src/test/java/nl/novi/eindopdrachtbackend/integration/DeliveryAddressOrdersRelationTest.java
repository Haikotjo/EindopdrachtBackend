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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DeliveryAddressOrdersRelationTest {

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
    void testDeliveryAddressIsLinkedToOrdersCorrectly() {
        // Create and save a User
        User user = new User("Eva Evers", "eva@example.com", "password123", UserRole.OWNER, "0698765432");
        user = userRepository.save(user);

        // Create and save a Restaurant
        Restaurant restaurant = new Restaurant("Tasty Meals", "456 Food Ave", "555-6789");
        restaurant = restaurantRepository.save(restaurant);

        // Create and save a DeliveryAddress
        DeliveryAddress deliveryAddress = new DeliveryAddress("Parcel Road", 5, "Parcel City", 2000, "2000CD", "Delivery Land");
        deliveryAddress.setUser(user);
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // Create and save an Order
        Order order1 = new Order(user, restaurant, deliveryAddress, true);
        order1 = orderRepository.save(order1);
        Order order2 = new Order(user, restaurant, deliveryAddress, false);
        order2 = orderRepository.save(order2);

        deliveryAddress.addOrder(order1);
        deliveryAddress.addOrder(order2);

        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // Haal de opgeslagen DeliveryAddress op en verifieer de koppeling met Orders
        DeliveryAddress savedDeliveryAddress = deliveryAddressRepository.findById(deliveryAddress.getId()).orElseThrow();
        assertEquals(2, savedDeliveryAddress.getOrders().size(), "Het bezorgadres is niet correct gekoppeld aan de orders.");
    }
}
