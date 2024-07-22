//package nl.novi.eindopdrachtbackend.repository;
//
//import nl.novi.eindopdrachtbackend.model.Menu;
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
//public class MenuRepositoryTest {
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @Autowired
//    private MenuRepository menuRepository;
//
//    @BeforeEach
//    void setUp() {
//        // Preparation
//        Menu breakfastMenu = new Menu("Breakfast Menu", "A delicious way to start your day");
//        Menu lunchMenu = new Menu("Lunch Menu", "A hearty midday selection");
//        entityManager.persist(breakfastMenu);
//        entityManager.persist(lunchMenu);
//        entityManager.flush();
//    }
//
//    @Test
//    void whenFindByNameIgnoreCase_thenReturnMenu() {
//        // Action
//        List<Menu> foundMenus = menuRepository.findByNameIgnoreCase("breakfast menu");
//        // Verification
//        assertEquals(1, foundMenus.size());
//        assertTrue(foundMenus.stream().anyMatch(menu -> "Breakfast Menu".equals(menu.getName())));
//    }
//
//    @Test
//    void whenFindByNameIgnoreCaseWithDifferentCase_thenReturnMenu() {
//        // Action
//        List<Menu> foundMenus = menuRepository.findByNameIgnoreCase("BREAKFAST MENU");
//        // Verification
//        assertEquals(1, foundMenus.size());
//        assertTrue(foundMenus.stream().anyMatch(menu -> "Breakfast Menu".equals(menu.getName())));
//    }
//}
