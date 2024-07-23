package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MenuMenuItemRelationTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant;

    @BeforeEach
    public void setup() {
        restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant = restaurantRepository.save(restaurant);
    }

    @Test
    @Transactional
    public void testMenuMenuItemRelation() {
        // Maak een Menu aan
        Menu menu = new Menu();
        menu.setName("Dinner Menu");
        menu.setDescription("Evening menu with multiple courses");
        menu.setRestaurant(restaurant);
        menu.setMenuItems(new HashSet<>()); // Zorg dat het een mutable collectie is
        menu = menuRepository.save(menu);

        // Maak een MenuItem aan
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Steak");
        menuItem.setPrice(25.99);
        menuItem.setDescription("Grilled steak with side dishes");
        menuItem.setImage("steak.jpg");
        menuItem.setRestaurant(restaurant); // Koppel het menu-item aan het restaurant
        menuItem = menuItemRepository.save(menuItem);

        // Voeg MenuItem toe aan Menu
        Set<MenuItem> menuItems = new HashSet<>(menu.getMenuItems());
        menuItems.add(menuItem);
        menu.setMenuItems(menuItems);
        menu = menuRepository.save(menu);

        // Controleer de relatie
        Menu savedMenu = menuRepository.findById(menu.getId()).get();
        assertTrue(savedMenu.getMenuItems().contains(menuItem));
    }
}
