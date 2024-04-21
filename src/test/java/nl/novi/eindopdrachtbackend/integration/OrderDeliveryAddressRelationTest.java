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
class OrderDeliveryAddressRelationTest {

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
        User user = new User("Jan Jansen", "jan@example.com", "securepassword", UserRole.CUSTOMER, "0612345678");
        user = userRepository.saveAndFlush(user); // Gebruik saveAndFlush om onmiddellijk te schrijven naar de database

        // Create a DeliveryAddress
        DeliveryAddress deliveryAddress = new DeliveryAddress("Delivery Street", 10, "Delivery City", 1000, "1000AB", "Delivery Country");
        deliveryAddress.setUser(user); // Stel de gebruiker expliciet in op het adres
        deliveryAddress = deliveryAddressRepository.saveAndFlush(deliveryAddress); // Sla het adres op

        // Create and save a Restaurant
        Restaurant restaurant = new Restaurant("The Good Food Place", "123 Main St", "555-1234");
        restaurant = restaurantRepository.save(restaurant);

        // Create and save an Order
        Order order = new Order(user, restaurant, deliveryAddress, false);
        order = orderRepository.save(order);

        // Retrieve the saved Order and verify its relationship to the DeliveryAddress
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow();
        assertEquals(deliveryAddress.getId(), savedOrder.getDeliveryAddress().getId(), "De order is niet correct gekoppeld aan het bezorgadres.");
    }
}
