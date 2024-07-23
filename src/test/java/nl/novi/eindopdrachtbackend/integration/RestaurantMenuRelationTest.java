package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.Restaurant;
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
class RestaurantMenuRelationTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    private Restaurant restaurant;

    @BeforeEach
    public void setup() {
        restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant = restaurantRepository.save(restaurant);
    }

    @Test
    @Transactional
    public void testRestaurantMenuRelation() {
        // Maak een Menu aan
        Menu menu = new Menu();
        menu.setName("Dinner Menu");
        menu.setDescription("Evening menu with multiple courses");
        menu.setRestaurant(restaurant); // Koppel het menu aan het restaurant
        menu = menuRepository.save(menu);

        // Voeg Menu toe aan Restaurant
        Set<Menu> menus = new HashSet<>(restaurant.getMenus());
        menus.add(menu);
        restaurant.setMenus(menus);
        restaurant = restaurantRepository.save(restaurant);

        // Controleer de relatie
        Restaurant savedRestaurant = restaurantRepository.findById(restaurant.getId()).get();
        assertTrue(savedRestaurant.getMenus().contains(menu));
    }
}
