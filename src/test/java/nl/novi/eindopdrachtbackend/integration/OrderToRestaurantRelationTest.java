package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrderToRestaurantRelationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @Transactional
    public void testOrderLinksBackToRestaurantCorrectly() {
        // Setup Restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setName("The Good Food Place");
        restaurant.setAddress("123 Main St");
        restaurant.setPhoneNumber("555-1234");
        restaurant = restaurantRepository.save(restaurant);

        // Create Order and link to Restaurant
        Order order = new Order();
        order.setFulfilled(true);
        order.setRestaurant(restaurant);
        // It's crucial to add the order to the restaurant to maintain the bidirectional relationship
        restaurant.addOrder(order);

        // Save the order
        order = orderRepository.save(order);

        // Retrieve the saved Order and verify its relationship to the Restaurant
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow();
        assertEquals(restaurant.getId(), savedOrder.getRestaurant().getId(), "The order is not correctly linked back to the restaurant.");
    }
}
