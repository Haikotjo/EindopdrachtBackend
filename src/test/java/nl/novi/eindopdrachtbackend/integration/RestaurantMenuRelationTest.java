package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RestaurantMenuRelationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    private Restaurant savedRestaurant;
    private Menu savedMenu;

    @Test
    @Transactional
    public void testRestaurantMenuRelation() {
        // Make Restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setName("The Good Food Place");
        restaurant.setAddress("123 Main St");
        restaurant.setPhoneNumber("555-1234");
        savedRestaurant = restaurantRepository.save(restaurant);

        // Make Menu
        Menu menu = new Menu();
        menu.setName("Spring Specials");
        menu.setDescription("Seasonal dishes for spring");

        // Insert Menu to Restaurant
        savedRestaurant.getMenus().add(menu);
        savedMenu = menuRepository.save(menu);

        // Get saved Restaurant and verify relation
        Restaurant retrievedRestaurant = restaurantRepository.findById(savedRestaurant.getId()).orElseThrow();
        assertTrue(retrievedRestaurant.getMenus().contains(savedMenu), "The menu is not linked to the restaurant.");
    }
}
