package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
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

    @Test
    @Transactional
    public void testUserOrderRelation() {
        // Make User
        User user = new User();
        user.setName("Jan Jansen");
        user.setEmail("jan@example.com");
        user.setPassword("password123");
        user.setRole("ADMIN");
        user.setAddress("Main Street 1");
        user.setPhoneNumber("0612345678");
        user = userRepository.save(user);

        // Make order
        Order order = new Order(true);
        order.setCustomer(user);
        user.addOrder(order);

        // Save order
        order = orderRepository.save(order);

        // Get saved User and verify relation
        User savedUser = userRepository.findById(user.getId()).orElseThrow();
        assertTrue(savedUser.getOrders().contains(order), "The order is not linked to the user.");
    }
}
