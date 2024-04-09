package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RestaurantOrdersRelationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Transactional
    public void testRestaurantOrderRelation() {
        // Make Restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setName("The Good Food Place");
        restaurant.setAddress("123 Main St");
        restaurant.setPhoneNumber("555-1234");
        restaurant = restaurantRepository.save(restaurant);

        // Make order and link it to the restaurant
        Order order = new Order();
        order.setFulfilled(true);
        order.setRestaurant(restaurant);
        restaurant.addOrder(order);

        // Save order
        order = orderRepository.save(order);

        // Get saved Restaurant and verify relation
        Restaurant savedRestaurant = restaurantRepository.findById(restaurant.getId()).orElseThrow();
        assertTrue(savedRestaurant.getOrders().contains(order), "The order is not linked to the restaurant.");
    }
}
