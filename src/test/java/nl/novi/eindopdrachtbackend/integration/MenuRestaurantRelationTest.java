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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MenuRestaurantRelationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    private Restaurant savedRestaurant;
    private Menu savedMenu;

    @Test
    @Transactional
    public void testMenuLinksBackToRestaurantCorrectly() {
        // Setup Restaurant
        Restaurant restaurant = new Restaurant("The Good Food Place", "123 Main St", "555-1234");
        savedRestaurant = restaurantRepository.save(restaurant);

        // Create Menu and link to Restaurant
        Menu menu = new Menu("Spring Specials", "Seasonal dishes for spring");
        menu.setRestaurant(savedRestaurant); // Directly linking menu to restaurant
        // It's crucial to add the menu to the restaurant to maintain the bidirectional relationship
        savedRestaurant.addMenu(menu);

        // Save the menu after adding to restaurant
        savedMenu = menuRepository.save(menu);

        // Retrieve the saved Menu and verify its relationship to the Restaurant
        Menu retrievedMenu = menuRepository.findById(savedMenu.getId()).orElseThrow();
        assertEquals(savedRestaurant.getId(), retrievedMenu.getRestaurant().getId(), "The menu does not correctly link back to the restaurant.");
    }
}
