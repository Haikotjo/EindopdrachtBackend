//package nl.novi.eindopdrachtbackend.integration;
//
//import jakarta.transaction.Transactional;
//import nl.novi.eindopdrachtbackend.model.*;
//import nl.novi.eindopdrachtbackend.repository.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//class OrderRestaurantRelationTest {
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private OrderRepository orderRepository;
//    @Autowired
//    private RestaurantRepository restaurantRepository;
//    @Autowired
//    private DeliveryAddressRepository deliveryAddressRepository;
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Test
//    @Transactional
//    public void testOrderLinksBackToRestaurantCorrectly() {
//        // Setup Role
//        Role ownerRole = new Role(UserRole.OWNER);
//        roleRepository.save(ownerRole);
//
//        // Setup User
//        User user = new User("Jan Jansen", "jan@example.com", "password123", Collections.singletonList(ownerRole), "0612345678");
//        user = userRepository.save(user);
//
//        // Setup Restaurant
//        Restaurant restaurant = new Restaurant("The Good Food Place", "123 Main St", "555-1234");
//        restaurant = restaurantRepository.save(restaurant);
//
//        // Setup DeliveryAddress
//        DeliveryAddress deliveryAddress = new DeliveryAddress("Delivery Street", 1, "Delivery City", "1234AB", "Delivery Country");
//        deliveryAddress.setUser(user);
//        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);
//
//        // Create an Order and link it to User, Restaurant, and DeliveryAddress
//        Order order = new Order(user, restaurant, deliveryAddress, true);
//        order = orderRepository.save(order);
//
//        // Retrieve the saved Order and verify its relationship to the Restaurant
//        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow();
//        assertEquals(restaurant.getId(), savedOrder.getRestaurant().getId(), "De order is niet correct gekoppeld aan het restaurant.");
//    }
//}
