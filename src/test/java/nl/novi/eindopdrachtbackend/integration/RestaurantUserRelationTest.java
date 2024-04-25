package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@EnableTransactionManagement
public class RestaurantUserRelationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    @Transactional
    public void testRestaurantToOwnerAssociation() {
        // Create and save a User
        User user = new User("Eva Evers", "eva@example.com", "password123", UserRole.OWNER, "0698765432");
        user = userRepository.save(user);

        // Create and save a Restaurant
        Restaurant restaurant = new Restaurant("Tasty Meals", "456 Food Ave", "555-6789");
        restaurant.setOwner(user);
        restaurant = restaurantRepository.save(restaurant);

        // Fetch the restaurant from the database and verify its owner
        Restaurant loadedRestaurant = restaurantRepository.findById(restaurant.getId()).orElseThrow();
        User loadedUser = loadedRestaurant.getOwner();

        assertNotNull(loadedUser, "The owner should not be null");
        assertEquals(user.getId(), loadedUser.getId(), "The owner's ID should match the user's ID");
        assertEquals(user, loadedUser, "The fetched owner should be the same as the user created");
    }
}
