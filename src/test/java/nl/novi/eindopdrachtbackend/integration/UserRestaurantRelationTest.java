//package nl.novi.eindopdrachtbackend.integration;
//
//import jakarta.transaction.Transactional;
//import nl.novi.eindopdrachtbackend.model.Restaurant;
//import nl.novi.eindopdrachtbackend.model.Role;
//import nl.novi.eindopdrachtbackend.model.User;
//import nl.novi.eindopdrachtbackend.model.UserRole;
//import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
//import nl.novi.eindopdrachtbackend.repository.RoleRepository;
//import nl.novi.eindopdrachtbackend.repository.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@EnableTransactionManagement
//class UserRestaurantRelationTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RestaurantRepository restaurantRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Test
//    @Transactional
//    void testUserRestaurantAssociation() {
//        // Create and save a Role
//        Role ownerRole = new Role(UserRole.OWNER);
//        roleRepository.save(ownerRole);
//
//        // Create and save a User
//        User user = new User("Eva Evers", "eva@example.com", "password123", Collections.singletonList(ownerRole), "0698765432");
//        user = userRepository.save(user); // Ensure it's saved before linking
//
//        // Create a Restaurant
//        Restaurant restaurant = new Restaurant("Tasty Meals", "456 Food Ave", "555-6789");
//        restaurant.setOwner(user); // Set user as the owner
//        user.setRestaurant(restaurant); // Set the restaurant in the user
//
//        // Save the Restaurant
//        restaurant = restaurantRepository.save(restaurant); // Ensure it's saved and linked
//        userRepository.save(user); // Save user again to ensure both sides are synchronized
//
//        // Reload to verify
//        User foundUser = userRepository.findById(user.getId()).orElseThrow();
//        Restaurant foundRestaurant = restaurantRepository.findById(restaurant.getId()).orElseThrow();
//
//        assertEquals(foundRestaurant, foundUser.getRestaurant(), "Restaurant is not linked correctly to the user.");
//        assertEquals(foundUser, foundRestaurant.getOwner(), "User is not linked correctly as the owner of the restaurant.");
//    }
//}
