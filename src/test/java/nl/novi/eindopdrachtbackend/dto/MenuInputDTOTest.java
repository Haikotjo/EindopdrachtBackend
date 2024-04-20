package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class MenuInputDTOTest {

    @Test
    public void testMenuInputDTO() {
        // Create list of menu item IDs
        List<Long> menuItemsIds = new ArrayList<>();
        menuItemsIds.add(10L);
        menuItemsIds.add(20L);
        menuItemsIds.add(30L);

        MenuInputDTO menuInputDTO = new MenuInputDTO();

        menuInputDTO.setName("Spring Menu");
        menuInputDTO.setDescription("A selection of light dishes for spring");
        menuInputDTO.setMenuItemsIds(menuItemsIds);

        // Assertions
        assertEquals("Spring Menu", menuInputDTO.getName());
        assertEquals("A selection of light dishes for spring", menuInputDTO.getDescription());
        assertNotNull(menuInputDTO.getMenuItemsIds());
        assertEquals(3, menuInputDTO.getMenuItemsIds().size());
        assertEquals(10L, menuInputDTO.getMenuItemsIds().get(0));
        assertEquals(20L, menuInputDTO.getMenuItemsIds().get(1));
        assertEquals(30L, menuInputDTO.getMenuItemsIds().get(2));

        // Test updating values
        menuInputDTO.setName("Summer Menu");
        assertEquals("Summer Menu", menuInputDTO.getName());
        menuInputDTO.setDescription("Fresh salads and cold drinks");
        assertEquals("Fresh salads and cold drinks", menuInputDTO.getDescription());
    }
}
