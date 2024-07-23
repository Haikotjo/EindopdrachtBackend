package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class MenuItemRepositoryTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    private MenuItem menuItem;
    private Restaurant restaurant;
    private User owner;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        owner = new User();
        setField(owner, "id", 1L);

        restaurant = new Restaurant();
        setField(restaurant, "id", 1L);
        restaurant.setOwner(owner);

        menuItem = new MenuItem();
        menuItem.setName("Pizza");
        menuItem.setPrice(12.5);
        menuItem.setDescription("Delicious cheese pizza");
        menuItem.setImage("image_url");
        menuItem.setRestaurant(restaurant);

        // Use reflection to set the id
        Field idField = MenuItem.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(menuItem, 1L);
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    public void testFindByRestaurant_Owner_Id() {
        Long ownerId = 1L;
        when(menuItemRepository.findByRestaurant_Owner_Id(ownerId)).thenReturn(Arrays.asList(menuItem));
        List<MenuItem> menuItems = menuItemRepository.findByRestaurant_Owner_Id(ownerId);
        assertNotNull(menuItems);
        assertEquals(1, menuItems.size());
        assertEquals("Pizza", menuItems.get(0).getName());
    }

    @Test
    public void testFindByRestaurant_Id() {
        Long restaurantId = 1L;
        when(menuItemRepository.findByRestaurant_Id(restaurantId)).thenReturn(Arrays.asList(menuItem));
        List<MenuItem> menuItems = menuItemRepository.findByRestaurant_Id(restaurantId);
        assertNotNull(menuItems);
        assertEquals(1, menuItems.size());
        assertEquals("Pizza", menuItems.get(0).getName());
    }

    @Test
    public void testFindByIdAndRestaurant_Owner_Id() {
        Long ownerId = 1L;
        when(menuItemRepository.findByIdAndRestaurant_Owner_Id(1L, ownerId)).thenReturn(Optional.of(menuItem));
        Optional<MenuItem> foundMenuItem = menuItemRepository.findByIdAndRestaurant_Owner_Id(1L, ownerId);
        assertTrue(foundMenuItem.isPresent());
        assertEquals("Pizza", foundMenuItem.get().getName());
    }

    @Test
    public void testFindByNameIgnoreCase() {
        when(menuItemRepository.findByNameIgnoreCase("pizza")).thenReturn(Arrays.asList(menuItem));
        List<MenuItem> menuItems = menuItemRepository.findByNameIgnoreCase("pizza");
        assertNotNull(menuItems);
        assertEquals(1, menuItems.size());
        assertEquals("Pizza", menuItems.get(0).getName());
    }
}
