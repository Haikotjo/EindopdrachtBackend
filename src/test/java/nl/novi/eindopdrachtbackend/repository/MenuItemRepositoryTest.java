package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class MenuItemRepositoryTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private User owner;
    private Restaurant restaurant;
    private MenuItem menuItem;

    @BeforeEach
    public void setup() {
        owner = userRepository.findByEmail("owner.one@example.com").orElseThrow(() -> new RuntimeException("Owner not found"));
        restaurant = restaurantRepository.findByOwner(owner).orElseThrow(() -> new RuntimeException("Restaurant not found"));

        menuItem = new MenuItem();
        menuItem.setName("New Pizza");
        menuItem.setPrice(12.5);
        menuItem.setDescription("Delicious cheese pizza");
        menuItem.setImage("image_url");
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
    }

    @Test
    public void testFindByRestaurant_Owner_Id() {
        Long ownerId = owner.getId();
        List<MenuItem> menuItems = menuItemRepository.findByRestaurant_Owner_Id(ownerId);
        assertNotNull(menuItems);
        assertFalse(menuItems.isEmpty());
    }

    @Test
    public void testFindByRestaurant_Id() {
        Long restaurantId = restaurant.getId();
        List<MenuItem> menuItems = menuItemRepository.findByRestaurant_Id(restaurantId);
        assertNotNull(menuItems);
        assertFalse(menuItems.isEmpty());
    }

    @Test
    public void testFindByIdAndRestaurant_Owner_Id() {
        Long ownerId = owner.getId();
        Optional<MenuItem> foundMenuItem = menuItemRepository.findByIdAndRestaurant_Owner_Id(menuItem.getId(), ownerId);
        assertTrue(foundMenuItem.isPresent());
        assertEquals("New Pizza", foundMenuItem.get().getName());
    }

    @Test
    public void testFindByNameIgnoreCase() {
        List<MenuItem> menuItems = menuItemRepository.findByNameIgnoreCase("new pizza");
        assertNotNull(menuItems);
        assertFalse(menuItems.isEmpty());
        assertEquals("New Pizza", menuItems.get(0).getName());
    }
}
