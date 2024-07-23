package nl.novi.eindopdrachtbackend.integration;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MenuItemRepositoryIntegrationTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    private MenuItem menuItem;
    private Restaurant restaurant;
    private User owner;

    @BeforeEach
    public void setup() {
        owner = new User();
        owner.setName("Owner Name");
        owner.setEmail("owner@example.com");
        owner.setPassword("password");
        userRepository.save(owner);

        restaurant = new Restaurant();
        restaurant.setName("Restaurant Name");
        restaurant.setAddress("Restaurant Address");
        restaurant.setPhoneNumber("1234567890");
        restaurant.setOwner(owner);
        restaurantRepository.save(restaurant);

        menuItem = new MenuItem();
        menuItem.setName("Pizza");
        menuItem.setPrice(12.5);
        menuItem.setDescription("Delicious cheese pizza");
        menuItem.setImage("image_url");
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
    }

    @Test
    public void testFindByRestaurant_Owner_Id() {
        List<MenuItem> menuItems = menuItemRepository.findByRestaurant_Owner_Id(owner.getId());
        assertNotNull(menuItems);
        assertFalse(menuItems.isEmpty());
        assertEquals("Pizza", menuItems.get(0).getName());
    }

    @Test
    public void testFindByRestaurant_Id() {
        List<MenuItem> menuItems = menuItemRepository.findByRestaurant_Id(restaurant.getId());
        assertNotNull(menuItems);
        assertFalse(menuItems.isEmpty());
        assertEquals("Pizza", menuItems.get(0).getName());
    }

    @Test
    public void testFindByIdAndRestaurant_Owner_Id() {
        Optional<MenuItem> foundMenuItem = menuItemRepository.findByIdAndRestaurant_Owner_Id(menuItem.getId(), owner.getId());
        assertTrue(foundMenuItem.isPresent(), "MenuItem should be present");
        assertEquals("Pizza", foundMenuItem.get().getName());
    }

    @Test
    public void testFindByNameIgnoreCase() {
        List<MenuItem> menuItems = menuItemRepository.findByNameIgnoreCase("pizza");
        assertNotNull(menuItems);
        assertFalse(menuItems.isEmpty());
        assertEquals("Pizza", menuItems.get(0).getName());
    }
}
