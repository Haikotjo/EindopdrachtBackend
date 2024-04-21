package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    private User user;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        // Setup data
        user = new User("John Doe", "john@example.com", "password", UserRole.CUSTOMER, "555-1234");
        user = entityManager.persistAndFlush(user);

        restaurant = new Restaurant("The Good Food Place", "123 Main St", "555-1234");
        restaurant = entityManager.persistAndFlush(restaurant);

        DeliveryAddress deliveryAddress = new DeliveryAddress("Delivery Street", 1, "Delivery City", 1234, "1234AB", "Delivery Country");
        deliveryAddress.setUser(user);
        deliveryAddress = entityManager.persistAndFlush(deliveryAddress);

        Order order1 = new Order(user, restaurant, deliveryAddress, true);
        entityManager.persistAndFlush(order1);

        Order order2 = new Order(user, restaurant, deliveryAddress, false);
        entityManager.persistAndFlush(order2);
    }

    @Test
    void findByCustomerId_ShouldReturnOrders() {
        // Preparation & Action
        List<Order> orders = orderRepository.findOrdersByCustomerId(user.getId());

        // Verification
        assertThat(orders).hasSize(2).allMatch(order -> order.getCustomer().equals(user));
    }

    @Test
    void findOrdersByRestaurantId_ShouldReturnOrders() {
        // Preparation & Action
        List<Order> orders = orderRepository.findOrdersByRestaurantId(restaurant.getId());

        // Verification
        assertThat(orders).hasSize(2).allMatch(order -> order.getRestaurant().equals(restaurant));
    }
}
