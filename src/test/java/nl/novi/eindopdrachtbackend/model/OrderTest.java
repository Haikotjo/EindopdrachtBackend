package nl.novi.eindopdrachtbackend.model;

        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    private User user;
    private Restaurant restaurant;
    private DeliveryAddress deliveryAddress;
    private Order order;

    @BeforeEach
    void setUp() {
        // Create an instance of entities
        user = new User("TestUser", "test@example.com", "password123", "USER", "Main Street", "0123456789");
        restaurant = new Restaurant("TestRestaurant", "Restaurant Street", "987654321");
        deliveryAddress = new DeliveryAddress("Delivery Street", 1, "Delivery City", 1234, "1234AB", "Delivery Country");

        // Make a new order with relations
        order = new Order(user, restaurant, deliveryAddress, true);
    }

    @Test
    public void testOrderIsCorrectlyInitialized() {
        assertNotNull(order.getCustomer(), "Order should have a customer");
        assertEquals("TestUser", order.getCustomer().getName());
        assertNotNull(order.getRestaurant(), "Order should have a restaurant");
        assertEquals("TestRestaurant", order.getRestaurant().getName());
        assertNotNull(order.getDeliveryAddress(), "Order should have a delivery address");
        assertEquals("Delivery Street", order.getDeliveryAddress().getStreet());
        assertEquals(true, order.isFulfilled(), "Order fulfillment status should be true");
    }
}
