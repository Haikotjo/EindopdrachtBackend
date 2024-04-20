package nl.novi.eindopdrachtbackend.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MenuDTOTest {

    @Test
    public void testMenuDTO() throws NoSuchFieldException, IllegalAccessException {
        // Create list of MenuItemDTO with reflection
        List<MenuItemDTO> menuItems = new ArrayList<>();
        MenuItemDTO pizza = new MenuItemDTO();
        setField(pizza, "id", 1L);
        pizza.setName("Pizza");
        pizza.setPrice(9.99);
        pizza.setDescription("Delicious cheese pizza");
        pizza.setImage("pizza.jpg");

        MenuItemDTO burger = new MenuItemDTO();
        setField(burger, "id", 2L);
        burger.setName("Burger");
        burger.setPrice(12.99);
        burger.setDescription("Juicy hamburger");
        burger.setImage("burger.jpg");

        menuItems.add(pizza);
        menuItems.add(burger);

        MenuDTO menuDTO = new MenuDTO();

        // Reflectively set id for MenuDTO
        setField(menuDTO, "id", 100L);

        menuDTO.setName("Dinner Menu");
        menuDTO.setDescription("A selection of our finest dishes");
        menuDTO.setMenuItems(menuItems);

        // Asserts
        assertEquals(100L, menuDTO.getId());
        assertEquals("Dinner Menu", menuDTO.getName());
        assertEquals("A selection of our finest dishes", menuDTO.getDescription());
        assertNotNull(menuDTO.getMenuItems());
        assertEquals(2, menuDTO.getMenuItems().size());
        assertEquals("Pizza", menuDTO.getMenuItems().get(0).getName());
        assertEquals("Burger", menuDTO.getMenuItems().get(1).getName());

        // Test updating values
        menuDTO.setName("Lunch Menu");
        assertEquals("Lunch Menu", menuDTO.getName());
    }

    private void setField(Object object, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}
