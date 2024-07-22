package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class IngredientMenuItemRelationTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private User owner;
    private Restaurant restaurant;

    @BeforeEach
    public void setup() {
        owner = new User();
        owner.setName("John Doe");
        owner.setEmail("john.doe" + System.currentTimeMillis() + "@example.com");
        owner.setPassword("securepassword");
        owner = userRepository.save(owner);

        restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");

        restaurant = restaurantRepository.save(restaurant);
    }

    @Test
    @Transactional
    public void testIngredientMenuItemRelation() {
        // Maak een Ingredient aan
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Kaas");
        ingredient.setQuantity(50);
        ingredient.setUnit("kg");
        ingredient.setCost(20.0);
        ingredient.setSupplier("SupplierX");
        ingredient.setExpirationDate(LocalDate.of(2024, 12, 31));
        ingredient.setDescription("Mozzarella Cheese");
        ingredient.setOwner(owner);
        ingredient = ingredientRepository.save(ingredient);

        // Maak een MenuItem aan
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Pizza Margherita");
        menuItem.setPrice(9.99);
        menuItem.setDescription("Classic pizza");
        menuItem.setImage("image.jpg");
        menuItem.setRestaurant(restaurant); // Koppel het menu-item aan het restaurant

        // Voeg Ingredient toe aan MenuItem
        menuItem.setIngredients(Set.of(ingredient));
        menuItem = menuItemRepository.save(menuItem);

        // Controleer de relatie
        MenuItem savedMenuItem = menuItemRepository.findById(menuItem.getId()).get();
        assertTrue(savedMenuItem.getIngredients().contains(ingredient));
    }
}
