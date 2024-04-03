package nl.novi.eindopdrachtbackend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class OrderTest {

    @Test
    public void testOrderConstructorWithFulfilledTrue() {
        // Create an instance of Order with fulfilled status set to true
        Order order = new Order(true);

        // Verify that the fulfilled status is correctly set
        assertTrue(order.isFulfilled(), "Order fulfilled status should be true");
    }

    @Test
    public void testOrderConstructorWithFulfilledFalse() {
        // Create an instance of Order with fulfilled status set to false
        Order order = new Order(false);

        // Verify that the fulfilled status is correctly set
        assertFalse(order.isFulfilled(), "Order fulfilled status should be false");
    }
}
