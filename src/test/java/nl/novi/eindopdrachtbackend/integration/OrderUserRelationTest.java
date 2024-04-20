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
class OrderUserRelationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    DeliveryAddressRepository deliveryAddressRepository;

    @Test
    @Transactional
    public void testOrderLinksBackToUserCorrectly() {
        // Create and save a User
        User user = new User("Lisa Meijer", "lisa@example.com", "securepassword", User.Role.CUSTOMER, "User Street 2", "0622334455");
        user = userRepository.save(user);

        // Create and save a Restaurant
        Restaurant restaurant = new Restaurant("Delicious Eats", "Food Street 3", "555-9876");
        restaurant = restaurantRepository.save(restaurant);

        // Create and save a DeliveryAddress
        DeliveryAddress deliveryAddress = new DeliveryAddress("Parcel Avenue", 4, "Parcel Town", 1000, "1000PA", "Parcel Country");
        deliveryAddress.setUser(user); // Assuming setUser links the address to the user
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);

        // Create an Order and link it to the User, Restaurant, and DeliveryAddress
        Order order = new Order();
        order.setFulfilled(false);
        order.setCustomer(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);

        user.addOrder(order);

        // Save the order
        order = orderRepository.save(order);

        // Retrieve the saved Order and verify its relationship to the User, Restaurant, and DeliveryAddress
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow();
        assertEquals(user.getId(), savedOrder.getCustomer().getId(), "The order does not correctly link back to the user.");
        assertEquals(restaurant.getId(), savedOrder.getRestaurant().getId(), "The order does not correctly link back to the restaurant.");
        assertEquals(deliveryAddress.getId(), savedOrder.getDeliveryAddress().getId(), "The order does not correctly link back to the delivery address.");
    }
}
