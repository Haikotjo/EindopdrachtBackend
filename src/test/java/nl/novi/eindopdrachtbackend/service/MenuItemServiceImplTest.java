package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemServiceImplTest {

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private MenuItemServiceImpl menuItemService;

    private MenuItem pizza;
    private MenuItem burger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pizza = new MenuItem("Pizza", 15.99, "Delicious cheese pizza", "pizza.jpg");
        burger = new MenuItem("Burger", 11.99, "Beef burger with cheese", "burger.jpg");
    }

    @Test
    void getAllMenuItems_ReturnsListOfMenuItems() {
        // Preparation
        when(menuItemRepository.findAll()).thenReturn(Arrays.asList(pizza, burger));

        // Action
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();

        // Verification
        assertNotNull(menuItems);
        assertEquals(2, menuItems.size());
        verify(menuItemRepository).findAll();
    }

    @Test
    void getMenuItemById_ReturnsMenuItem() {
        // Preparation
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(pizza));

        // Action
        MenuItem foundMenuItem = menuItemService.getMenuItemById(1L);

        // Verification
        assertNotNull(foundMenuItem);
        assertEquals("Pizza", foundMenuItem.getName());
        verify(menuItemRepository).findById(anyLong());
    }

    @Test
    void getMenuItemById_ThrowsResourceNotFoundException() {
        // Preparation
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action
        assertThrows(ResourceNotFoundException.class, () -> menuItemService.getMenuItemById(1L));

        // Verification
        verify(menuItemRepository).findById(anyLong());
    }

    @Test
    void createMenuItem_ReturnsCreatedMenuItem() {
        // Preparation
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(pizza);

        // Action
        MenuItem createdMenuItem = menuItemService.createMenuItem(pizza);

        // Verification
        assertNotNull(createdMenuItem);
        assertEquals("Pizza", createdMenuItem.getName());
        assertEquals(15.99, createdMenuItem.getPrice());
        assertEquals("Delicious cheese pizza", createdMenuItem.getDescription());
        assertEquals("pizza.jpg", createdMenuItem.getImage());
        verify(menuItemRepository).save(any(MenuItem.class));
    }

    @Test
        // Preparation
    void updateMenuItem_ReturnsUpdatedMenuItem() {
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(pizza));
        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(pizza);

        // Action
        MenuItem updatedMenuItem = new MenuItem("Pizza", 17.99, "Delicious cheese pizza with extra cheese", "pizza_cheese.jpg");
        MenuItem result = menuItemService.updateMenuItem(1L, updatedMenuItem);

        // Verification
        assertNotNull(result);
        assertEquals(updatedMenuItem.getPrice(), result.getPrice());
        assertEquals(updatedMenuItem.getDescription(), result.getDescription());
        assertEquals(updatedMenuItem.getImage(), result.getImage());
        verify(menuItemRepository).save(any(MenuItem.class));
        verify(menuItemRepository).findById(anyLong());
    }

    @Test
    void updateMenuItem_ThrowsResourceNotFoundExceptionWhenNotFound() {
        // Preparation
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.empty());


        MenuItem updatedMenuItem = new MenuItem("Updated Pizza", 20.99, "Updated description", "updated_pizza.jpg");

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuItemService.updateMenuItem(1L, updatedMenuItem));

        verify(menuItemRepository).findById(anyLong());
        verify(menuItemRepository, never()).save(any(MenuItem.class));
    }


    @Test
    void deleteMenuItem_DeletesMenuItem() {
        // Preparation
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(pizza));

        // Action
        menuItemService.deleteMenuItem(1L);

        // Verification
        verify(menuItemRepository).delete(pizza);
    }

    @Test
    void deleteMenuItem_ThrowsResourceNotFoundExceptionWhenNotFound() {
        // Preparation
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuItemService.deleteMenuItem(1L));

        verify(menuItemRepository).findById(anyLong());
        verify(menuItemRepository, never()).delete(any(MenuItem.class));
    }

    @Test
    void findByNameIgnoreCase_ReturnsListOfMenuItems() {
        // Preparation
        when(menuItemRepository.findByNameIgnoreCase("burger")).thenReturn(Arrays.asList(burger));

        // Action
        List<MenuItem> result = menuItemService.findByNameIgnoreCase("burger");

        // Verification
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Burger", result.get(0).getName());
        verify(menuItemRepository).findByNameIgnoreCase("burger");
    }

    @Test
    void findByNameIgnoreCase_ThrowsResourceNotFoundExceptionWhenNotFound() {
        // Preparation
        when(menuItemRepository.findByNameIgnoreCase("nonexistent")).thenReturn(Collections.emptyList());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuItemService.findByNameIgnoreCase("nonexistent"));

        verify(menuItemRepository).findByNameIgnoreCase("nonexistent");
    }

    @Test
    void addIngredientToMenuItem_AddsIngredientToMenuItem() {
        // Preparation
        Long menuItemId = 1L;
        Long ingredientId = 1L;
        MenuItem pizza = new MenuItem("Pizza", 15.99, "Delicious cheese pizza", "pizza.jpg");
        Ingredient cheese = new Ingredient("Cheese", 100);

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(pizza));
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(cheese));

        // Action
        menuItemService.addIngredientToMenuItem(menuItemId, ingredientId);

        // Verificatie
        assertTrue(pizza.getIngredients().contains(cheese));
        verify(menuItemRepository).save(pizza);
        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }

    @Test
    void addIngredientToMenuItem_ThrowsResourceNotFoundExceptionWhenMenuItemNotFound() {
        // Preparation
        Long menuItemId = 1L;
        Long ingredientId = 1L;
        Ingredient cheese = new Ingredient("Cheese", 100);

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.empty());
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(cheese));

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuItemService.addIngredientToMenuItem(menuItemId, ingredientId));
        verify(menuItemRepository, never()).save(any(MenuItem.class));
        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }

    @Test
    void addIngredientToMenuItem_ThrowsResourceNotFoundExceptionWhenIngredientNotFound() {
        // Preparation
        Long menuItemId = 1L;
        Long ingredientId = 1L;
        MenuItem pizza = new MenuItem("Pizza", 15.99, "Delicious cheese pizza", "pizza.jpg");

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(pizza));
        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuItemService.addIngredientToMenuItem(menuItemId, ingredientId));
        verify(menuItemRepository, never()).save(any(MenuItem.class));
        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }
}
