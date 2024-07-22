//package nl.novi.eindopdrachtbackend.repository;
//
//import nl.novi.eindopdrachtbackend.model.MenuItem;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@DataJpaTest
//public class MenuItemRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private MenuItemRepository menuItemRepository;
//
//    @BeforeEach
//    void setUp() {
//        // Preparation
//        MenuItem pizza = new MenuItem("Pizza", 15.99, "Delicious cheese pizza", "pizza.jpg");
//        MenuItem burger = new MenuItem("Burger", 11.99, "Juicy beef burger", "burger.jpg");
//        entityManager.persist(pizza);
//        entityManager.persist(burger);
//        entityManager.flush();
//    }
//
//    @Test
//    void whenFindByNameIgnoreCase_thenReturnMenuItem() {
//        // Action
//        List<MenuItem> foundMenuItems = menuItemRepository.findByNameIgnoreCase("pizza");
//        // Verification
//        assertEquals(1, foundMenuItems.size());
//        assertTrue(foundMenuItems.stream().anyMatch(menuItem -> "Pizza".equals(menuItem.getName())));
//    }
//
//    @Test
//    void whenFindByNameIgnoreCaseWithDifferentCase_thenReturnMenuItem() {
//        // Action
//        List<MenuItem> foundMenuItems = menuItemRepository.findByNameIgnoreCase("PIZZA");
//        // Verification
//        assertEquals(1, foundMenuItems.size());
//        assertTrue(foundMenuItems.stream().anyMatch(menuItem -> "Pizza".equals(menuItem.getName())));
//    }
//}
