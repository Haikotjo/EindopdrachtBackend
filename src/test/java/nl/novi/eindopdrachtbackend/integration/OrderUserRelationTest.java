package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
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

    @Test
    @Transactional
    public void testOrderLinksBackToUserCorrectly() {
        // Create and save a User
        User user = new User("Lisa Meijer", "lisa@example.com", "securepassword", "CUSTOMER", "User Street 2", "0622334455");
        user = userRepository.save(user);

        // Create an Order and set its customer to the User
        Order order = new Order();
        order.setFulfilled(false);
        order.setCustomer(user);
        // It's crucial to add the order to the user to maintain the bidirectional relationship
        user.addOrder(order);

        // Save the order
        order = orderRepository.save(order);

        // Retrieve the saved Order and verify its relationship to the User
        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow();
        assertEquals(user.getId(), savedOrder.getCustomer().getId(), "The order does not correctly link back to the user.");
    }
}
