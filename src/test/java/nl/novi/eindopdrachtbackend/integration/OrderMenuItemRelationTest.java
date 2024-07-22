//package nl.novi.eindopdrachtbackend.integration;
//
//import jakarta.transaction.Transactional;
//import nl.novi.eindopdrachtbackend.model.*;
//import nl.novi.eindopdrachtbackend.repository.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@EnableTransactionManagement
//class OrderMenuItemRelationTest {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private MenuItemRepository menuItemRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RestaurantRepository restaurantRepository;
//
//    @Autowired
//    private DeliveryAddressRepository deliveryAddressRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Test
//    @Transactional
//    void testOrderMenuItemAssociation() {
//        // Create and save a Role
//        Role customerRole = new Role(UserRole.CUSTOMER);
//        roleRepository.save(customerRole);
//
//        // Create and save a User
//        User user = new User("Eva Evers", "eva@example.com", "password123", Collections.singletonList(customerRole), "0698765432");
//        user = userRepository.save(user);
//
//        // Create and save a Restaurant
//        Restaurant restaurant = new Restaurant("Tasty Meals", "456 Food Ave", "555-6789");
//        restaurant = restaurantRepository.save(restaurant);
//
//        // Create and save a DeliveryAddress
//        DeliveryAddress deliveryAddress = new DeliveryAddress("Food Street", 10, "Food City", "12345", "Country");
//        deliveryAddress.setUser(user);
//        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);
//
//        // Create and save a MenuItem
//        MenuItem menuItem = new MenuItem("Margherita Pizza", 9.99, "Classic Margherita with tomato and cheese", "imagePath.jpg");
//        menuItem = menuItemRepository.save(menuItem);
//
//        // Create an Order and associate with User, Restaurant, and MenuItem
//        Order order = new Order(user, restaurant, deliveryAddress, false);
//        Set<MenuItem> menuItems = new HashSet<>();
//        menuItems.add(menuItem);
//        order.setMenuItems(menuItems);
//
//        // Save the Order
//        order = orderRepository.save(order);
//
//        // Add the order to the MenuItem (bi-directional relationship)
//        menuItem.getOrders().add(order);
//        menuItemRepository.save(menuItem);
//
//        // Reload to verify
//        Order foundOrder = orderRepository.findById(order.getId()).orElseThrow();
//        MenuItem foundMenuItem = menuItemRepository.findById(menuItem.getId()).orElseThrow();
//
//        assertTrue(foundOrder.getMenuItems().contains(menuItem), "MenuItem is not linked correctly to the order.");
//        assertTrue(foundMenuItem.getOrders().contains(order), "Order is not linked correctly to the MenuItem.");
//    }
//}
