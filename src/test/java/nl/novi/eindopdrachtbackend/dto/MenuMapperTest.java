package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MenuMapperTest {

    @Test
    void toMenuDTOTest() {
        // Arrange
        MenuItem menuItem1 = new MenuItem();
        menuItem1.setName("Pizza");
        menuItem1.setPrice(9.99);
        menuItem1.setDescription("Delicious pizza");
        menuItem1.setImage("pizza.jpg");

        MenuItem menuItem2 = new MenuItem();
        menuItem2.setName("Burger");
        menuItem2.setPrice(12.99);
        menuItem2.setDescription("Juicy burger");
        menuItem2.setImage("burger.jpg");

        Set<MenuItem> menuItems = new HashSet<>();
        menuItems.add(menuItem1);
        menuItems.add(menuItem2);

        Menu menu = new Menu();
        menu.setName("Dinner Menu");
        menu.setDescription("A selection of our finest dishes");
        menu.setMenuItems(menuItems);

        // Act
        MenuDTO menuDTO = MenuMapper.toMenuDTO(menu);

        // Assert
        assertNotNull(menuDTO);
        assertEquals(menu.getId(), menuDTO.getId());
        assertEquals(menu.getName(), menuDTO.getName());
        assertEquals(menu.getDescription(), menuDTO.getDescription());
        assertNotNull(menuDTO.getMenuItems());
        assertEquals(2, menuDTO.getMenuItems().size());
        assertTrue(menuDTO.getMenuItems().stream().anyMatch(item -> item.getName().equals("Pizza")));
        assertTrue(menuDTO.getMenuItems().stream().anyMatch(item -> item.getName().equals("Burger")));
    }
}
