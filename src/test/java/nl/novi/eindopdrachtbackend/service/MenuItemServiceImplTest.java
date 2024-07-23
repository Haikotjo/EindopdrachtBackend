package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuItemServiceImpl menuService;

    private MenuItem menuItem;
    private User owner;
    private Restaurant restaurant;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        owner = new User();
        setField(owner, "id", 1L);
        owner.setEmail("test@example.com");

        restaurant = new Restaurant();
        setField(restaurant, "id", 1L);
        restaurant.setName("Test Restaurant");
        restaurant.setOwner(owner);

        menuItem = new MenuItem();
        setField(menuItem, "id", 1L);
        menuItem.setName("Pasta");
        menuItem.setDescription("Delicious pasta with tomato sauce");
        menuItem.setPrice(12.50);
        menuItem.setRestaurant(restaurant);
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    public void testGetAllMenuItems() {
        when(menuItemRepository.findAll()).thenReturn(Collections.singletonList(menuItem));

        List<MenuItemDTO> menuItems = menuService.getAllMenuItems();

        assertNotNull(menuItems);
        assertEquals(1, menuItems.size());
        verify(menuItemRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllMenuItemsForOwner() {
        when(menuItemRepository.findByRestaurant_Owner_Id(anyLong())).thenReturn(Collections.singletonList(menuItem));

        List<MenuItemDTO> menuItems = menuService.getAllMenuItemsForOwner(owner.getId());

        assertNotNull(menuItems);
        assertEquals(1, menuItems.size());
        verify(menuItemRepository, times(1)).findByRestaurant_Owner_Id(owner.getId());
    }

    @Test
    public void testGetAllMenuItemsForLoggedInOwner() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(owner));
        when(menuItemRepository.findByRestaurant_Owner_Id(anyLong())).thenReturn(Collections.singletonList(menuItem));

        List<MenuItemDTO> menuItems = menuService.getAllMenuItemsForLoggedInOwner(owner.getEmail());

        assertNotNull(menuItems);
        assertEquals(1, menuItems.size());
        verify(userRepository, times(1)).findByEmail(owner.getEmail());
        verify(menuItemRepository, times(1)).findByRestaurant_Owner_Id(owner.getId());
    }

    @Test
    public void testGetMenuItemByIdForOwner() {
        when(menuItemRepository.findByIdAndRestaurant_Owner_Id(anyLong(), anyLong())).thenReturn(Optional.of(menuItem));

        MenuItemDTO menuItemDTO = menuService.getMenuItemByIdForOwner(menuItem.getId(), owner.getId());

        assertNotNull(menuItemDTO);
        assertEquals("Pasta", menuItemDTO.getName());
        verify(menuItemRepository, times(1)).findByIdAndRestaurant_Owner_Id(menuItem.getId(), owner.getId());
    }

    @Test
    public void testGetMenuItemById() {
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(menuItem));

        MenuItemDTO menuItemDTO = menuService.getMenuItemById(menuItem.getId());

        assertNotNull(menuItemDTO);
        assertEquals("Pasta", menuItemDTO.getName());
        verify(menuItemRepository, times(1)).findById(menuItem.getId());
    }

    @Test
    public void testGetAllMenuItemsForRestaurant() {
        when(menuItemRepository.findByRestaurant_Id(anyLong())).thenReturn(Collections.singletonList(menuItem));

        List<MenuItemDTO> menuItems = menuService.getAllMenuItemsForRestaurant(restaurant.getId());

        assertNotNull(menuItems);
        assertEquals(1, menuItems.size());
        verify(menuItemRepository, times(1)).findByRestaurant_Id(restaurant.getId());
    }

    @Test
    public void testCreateMenuItemForOwner() throws NoSuchFieldException, IllegalAccessException {
        setField(restaurant, "id", 1L);
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));

        MenuItemInputDTO inputDTO = new MenuItemInputDTO();
        inputDTO.setName("Pasta");
        inputDTO.setDescription("Delicious pasta with tomato sauce");
        inputDTO.setPrice(12.50);

        MenuItemDTO menuItemDTO = menuService.createMenuItemForOwner(inputDTO, restaurant.getId());

        assertNotNull(menuItemDTO);
        assertEquals("Pasta", menuItemDTO.getName());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
        verify(restaurantRepository, times(1)).findById(restaurant.getId());
    }

    @Test
    public void testCreateMenuItemForRestaurant() throws NoSuchFieldException, IllegalAccessException {
        setField(restaurant, "id", 1L);
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));

        MenuItemInputDTO inputDTO = new MenuItemInputDTO();
        inputDTO.setName("Pasta");
        inputDTO.setDescription("Delicious pasta with tomato sauce");
        inputDTO.setPrice(12.50);

        MenuItemDTO menuItemDTO = menuService.createMenuItemForRestaurant(inputDTO, restaurant.getId());

        assertNotNull(menuItemDTO);
        assertEquals("Pasta", menuItemDTO.getName());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
        verify(restaurantRepository, times(1)).findById(restaurant.getId());
    }

    @Test
    public void testUpdateMenuItemForOwner() throws NoSuchFieldException, IllegalAccessException {
        // Stel velden in
        setField(menuItem, "id", 1L);
        setField(restaurant, "id", 1L);
        setField(owner, "id", 1L);
        restaurant.setOwner(owner);
        menuItem.setRestaurant(restaurant);

        // Mock instellingen voor menuItemRepository
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        // Mock instellingen voor ingredientRepository
        Ingredient ingredient1 = new Ingredient();
        setField(ingredient1, "id", 1L);
        Ingredient ingredient2 = new Ingredient();
        setField(ingredient2, "id", 2L);
        Ingredient ingredient3 = new Ingredient();
        setField(ingredient3, "id", 3L);

        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.findById(2L)).thenReturn(Optional.of(ingredient2));
        when(ingredientRepository.findById(3L)).thenReturn(Optional.of(ingredient3));

        // Input DTO
        MenuItemInputDTO inputDTO = new MenuItemInputDTO();
        inputDTO.setName("Updated Pasta");
        inputDTO.setDescription("Updated delicious pasta with tomato sauce");
        inputDTO.setPrice(15.00);
        inputDTO.setIngredientIds(Arrays.asList(1L, 2L, 3L));

        // Extra logging
        System.out.println("Running testUpdateMenuItemForOwner with MenuItem ID: " + menuItem.getId() + " and Restaurant ID: " + restaurant.getId());

        // Test de update method
        MenuItemDTO menuItemDTO = menuService.updateMenuItemForOwner(menuItem.getId(), inputDTO, restaurant.getId());

        // Assertions
        assertNotNull(menuItemDTO);
        assertEquals("Updated Pasta", menuItemDTO.getName());
        assertEquals("Updated delicious pasta with tomato sauce", menuItemDTO.getDescription());
        assertEquals(15.00, menuItemDTO.getPrice());
        verify(menuItemRepository, times(1)).findById(menuItem.getId());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }



    @Test
    public void testUpdateMenuItemByAdmin() throws NoSuchFieldException, IllegalAccessException {
        setField(menuItem, "id", 1L);

        // Voeg deze regel toe om ervoor te zorgen dat de juiste waarden worden gebruikt voor de mock repository
        when(menuItemRepository.findById(menuItem.getId())).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(menuItem);

        MenuItemInputDTO inputDTO = new MenuItemInputDTO();
        inputDTO.setName("Updated Pasta");
        inputDTO.setDescription("Updated delicious pasta with tomato sauce");
        inputDTO.setPrice(15.00);
        inputDTO.setIngredientIds(new ArrayList<>()); // Voeg dit toe om een lege lijst te initialiseren

        MenuItemDTO menuItemDTO = menuService.updateMenuItemByAdmin(menuItem.getId(), inputDTO);

        assertNotNull(menuItemDTO);
        assertEquals("Updated Pasta", menuItemDTO.getName());
        verify(menuItemRepository, times(1)).findById(menuItem.getId());
        verify(menuItemRepository, times(1)).save(any(MenuItem.class));
    }

    @Test
    public void testDeleteMenuItemForOwner() throws NoSuchFieldException, IllegalAccessException {
        setField(restaurant, "id", 1L);
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(menuItem));

        menuService.deleteMenuItemForOwner(menuItem.getId(), restaurant.getId());

        verify(menuItemRepository, times(1)).findById(menuItem.getId());
        verify(menuItemRepository, times(1)).delete(menuItem);
    }

    @Test
    public void testDeleteMenuItemByAdmin() throws NoSuchFieldException, IllegalAccessException {
        setField(restaurant, "id", 1L);
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(menuItem));

        menuService.deleteMenuItemByAdmin(menuItem.getId());

        verify(menuItemRepository, times(1)).findById(menuItem.getId());
        verify(menuItemRepository, times(1)).delete(menuItem);
    }

    @Test
    public void testFindByNameIgnoreCase() {
        when(menuItemRepository.findByNameIgnoreCase(anyString())).thenReturn(Collections.singletonList(menuItem));

        List<MenuItemDTO> menuItems = menuService.findByNameIgnoreCase("Pasta");

        assertNotNull(menuItems);
        assertEquals(1, menuItems.size());
        verify(menuItemRepository, times(1)).findByNameIgnoreCase("Pasta");
    }
}
