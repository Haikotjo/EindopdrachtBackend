package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
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

    @Test
    @Transactional
    public void testRestaurantMenuRelation() {
        // Make Restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setName("The Good Food Place");
        restaurant.setAddress("123 Main St");
        restaurant.setPhoneNumber("555-1234");
        restaurant = restaurantRepository.save(restaurant);

        // Make Menu
        Menu menu = new Menu();
        menu.setName("Spring Specials");
        menu.setDescription("Seasonal dishes for spring");

        // Insert Menu to Restaurant
        restaurant.getMenus().add(menu);
        menu = menuRepository.save(menu);

        // Get saved Restaurant and verify relation
        Restaurant savedRestaurant = restaurantRepository.findById(restaurant.getId()).orElseThrow();
        assertTrue(savedRestaurant.getMenus().contains(menu), "The menu is not linked to the restaurant.");
    }
}



