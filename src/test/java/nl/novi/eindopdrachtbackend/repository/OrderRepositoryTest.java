package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
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
        user = new User("John Doe", "john@example.com", "password", "ROLE_USER", "123 Main St", "555-1234");
        entityManager.persist(user);

        restaurant = new Restaurant("The Good Food Place", "123 Main St", "555-1234");
        entityManager.persist(restaurant);

        Order order1 = new Order(user, true);
        order1.setRestaurant(restaurant);
        entityManager.persist(order1);

        Order order2 = new Order(user, false);
        order2.setRestaurant(restaurant);
        entityManager.persist(order2);

        entityManager.flush();
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
