package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MenuItemMenuRelationTest {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @Transactional
    public void testMenuItemLinksBackToMenuCorrectly() {
        // Create a new Menu and save it
        Menu menu = new Menu("Dinner", "A delicious dinner menu");
        menu = menuRepository.save(menu);

        // Create a new MenuItem and add it to the Menu
        MenuItem menuItem = new MenuItem("Steak", 20.99, "A juicy steak", "steak.jpg");
        menuItem.getMenus().add(menu); // Directly add the Menu to the MenuItem
        menu.getMenuItems().add(menuItem); // Ensure bidirectional relationship is established

        // Save both entities
        menuItem = menuItemRepository.save(menuItem);
        menu = menuRepository.save(menu);

        // Verify the relationship from the MenuItem back to the Menu
        MenuItem savedMenuItem = menuItemRepository.findById(menuItem.getId()).orElseThrow();
        assertTrue(savedMenuItem.getMenus().contains(menu), "The MenuItem does not link back to the Menu correctly.");
    }
}
