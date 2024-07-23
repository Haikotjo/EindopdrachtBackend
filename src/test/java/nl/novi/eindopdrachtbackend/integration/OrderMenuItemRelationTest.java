package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrderMenuItemRelationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    private User customer;
    private Restaurant restaurant;
    private DeliveryAddress deliveryAddress;

    @BeforeEach
    public void setup() {
        customer = new User();
        customer.setName("Jane Doe");
        customer.setEmail("jane.doe" + System.currentTimeMillis() + "@example.com");
        customer.setPassword("securepassword");
        customer = userRepository.save(customer);

        restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant = restaurantRepository.save(restaurant);

        deliveryAddress = new DeliveryAddress();
        deliveryAddress.setStreet("Test Street");
        deliveryAddress.setHouseNumber(123);
        deliveryAddress.setCity("Test City");
        deliveryAddress.setPostcode("12345");
        deliveryAddress.setCountry("Test Country");
        deliveryAddress.setUser(customer);
        deliveryAddress = deliveryAddressRepository.save(deliveryAddress);
    }

    @Test
    @Transactional
    public void testOrderMenuItemRelation() {
        // Maak een MenuItem aan
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Pizza Margherita");
        menuItem.setPrice(9.99);
        menuItem.setDescription("Classic pizza");
        menuItem.setImage("image.jpg");
        menuItem.setRestaurant(restaurant); // Koppel het menu-item aan het restaurant
        menuItem = menuItemRepository.save(menuItem);

        // Maak een Order aan
        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);
        order.setFulfilled(false);

        // Voeg MenuItem toe aan Order
        order.setMenuItems(Set.of(menuItem));
        order = orderRepository.save(order);

        // Controleer de relatie
        Order savedOrder = orderRepository.findById(order.getId()).get();
        assertTrue(savedOrder.getMenuItems().contains(menuItem));
    }
}
