package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MenuMenuItemRelationTest {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    @Transactional
    public void testMenuMenuItemRelation() {
        // Make Menu
        Menu menu = new Menu();
        menu.setName("Lunch Menu");
        menu.setDescription("Menu for lunch specials");
        menu = menuRepository.save(menu);

        // Make MenuItem
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Sandwich");
        menuItem.setPrice(5.99);
        menuItem.setDescription("Delicious sandwich");
        menuItem.setImage("sandwich.jpg");

        // Insert MenuItem to Menu
        menu.getMenuItems().add(menuItem);
        menuItem = menuItemRepository.save(menuItem);

        // Get saved Menu and verify relation
        Menu savedMenu = menuRepository.findById(menu.getId()).get();
        assertTrue(savedMenu.getMenuItems().contains(menuItem));
    }
}
