//package nl.novi.eindopdrachtbackend.repository;
//
//import nl.novi.eindopdrachtbackend.model.Restaurant;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//public class RestaurantRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private RestaurantRepository restaurantRepository;
//
//    @BeforeEach
//    void setUp() {
//        // Setup data
//        Restaurant restaurant1 = new Restaurant("The Good Food Place", "123 Main St", "555-1234");
//        entityManager.persist(restaurant1);
//
//        Restaurant restaurant2 = new Restaurant("The Good Mood Cafe", "124 Main St", "555-1235");
//        entityManager.persist(restaurant2);
//
//        entityManager.flush();
//    }
//
//    @Test
//    void findByNameIgnoreCase_ExistingName_ReturnsListOfRestaurants() {
//        // Preparation & Action
//        List<Restaurant> foundRestaurants = restaurantRepository.findByNameIgnoreCase("the good food place");
//
//        // Verification
//        assertThat(foundRestaurants).hasSize(1);
//        assertThat(foundRestaurants.get(0).getName()).isEqualTo("The Good Food Place");
//    }
//
//    @Test
//    void findByNameIgnoreCase_NonExistingName_ReturnsEmptyList() {
//        // Preparation & Action
//        List<Restaurant> foundRestaurants = restaurantRepository.findByNameIgnoreCase("nonexistent");
//
//        // Verification
//        assertThat(foundRestaurants).isEmpty();
//    }
//}
