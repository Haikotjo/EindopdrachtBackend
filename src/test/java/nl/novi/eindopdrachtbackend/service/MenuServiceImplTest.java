package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
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

class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private MenuServiceImpl menuService;

    private Menu breakfastMenu;
    private Menu dinnerMenu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        breakfastMenu = new Menu("Breakfast", "Delicious breakfast menu");
        dinnerMenu = new Menu("Dinner", "Hearty dinner menu");
    }

    @Test
    void getAllMenus_ReturnsListOfMenus() {
        // Preparation
        when(menuRepository.findAll()).thenReturn(Arrays.asList(breakfastMenu, dinnerMenu));

        // Action
        List<Menu> menus = menuService.getAllMenus();

        // Verification
        assertNotNull(menus);
        assertEquals(2, menus.size());
        verify(menuRepository).findAll();
    }

    @Test
    void getMenuById_ReturnsMenu() {
        // Preparation
        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(breakfastMenu));

        // Action
        Menu foundMenu = menuService.getMenuById(1L);

        // Verification
        assertNotNull(foundMenu);
        assertEquals("Breakfast", foundMenu.getName());
        verify(menuRepository).findById(anyLong());
    }

    @Test
    void getMenuById_ThrowsResourceNotFoundException() {
        // Preparation
        when(menuRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuService.getMenuById(1L));
        verify(menuRepository).findById(anyLong());
    }

    @Test
    void createMenu_ReturnsCreatedMenu() {
        // Preparation
        when(menuRepository.save(any(Menu.class))).thenReturn(breakfastMenu);

        // Action
        Menu createdMenu = menuService.createMenu(breakfastMenu);

        // Verification
        assertNotNull(createdMenu);
        assertEquals("Breakfast", createdMenu.getName());
        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    void updateMenu_ReturnsUpdatedMenu() {
        // Preparation
        Long menuId = 1L;
        Menu updatedDetails = new Menu("Updated Menu", "Updated description");
        Menu expectedSavedMenu = new Menu("Updated Menu", "Updated description");
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(breakfastMenu));
        when(menuRepository.save(any(Menu.class))).thenReturn(expectedSavedMenu);

        // Action
        Menu updatedMenu = menuService.updateMenu(menuId, updatedDetails);

        // Verification
        assertNotNull(updatedMenu);
        assertEquals(updatedDetails.getName(), updatedMenu.getName());
        assertEquals(updatedDetails.getDescription(), updatedMenu.getDescription());
        verify(menuRepository).save(any(Menu.class));
    }

    @Test
    void deleteMenu_DeletesMenu() {
        // Preparation
        Long menuId = 1L;
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(breakfastMenu));

        // Action
        menuService.deleteMenu(menuId);

        // Verification
        verify(menuRepository).delete(breakfastMenu);
    }

    @Test
    void deleteMenu_ThrowsResourceNotFoundExceptionWhenNotFound() {
        // Preparation
        Long menuId = 1L;
        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuService.deleteMenu(menuId));
        verify(menuRepository, never()).delete(any(Menu.class));
    }

    @Test
    void findByNameIgnoreCase_ReturnsListOfMenus() {
        // Preparation
        String name = "breakfast";
        when(menuRepository.findByNameIgnoreCase(name)).thenReturn(Collections.singletonList(breakfastMenu));

        // Action
        List<Menu> foundMenus = menuService.findByNameIgnoreCase(name);

        // Verification
        assertNotNull(foundMenus);
        assertFalse(foundMenus.isEmpty());
        assertEquals(1, foundMenus.size());
        assertEquals(breakfastMenu.getName(), foundMenus.get(0).getName());
    }

    @Test
    void findByNameIgnoreCase_ThrowsResourceNotFoundExceptionWhenNotFound() {
        // Preparation
        String name = "nonexistent";
        when(menuRepository.findByNameIgnoreCase(name)).thenReturn(Collections.emptyList());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuService.findByNameIgnoreCase(name));
    }

    @Test
    void addMenuItemToMenu_AddsMenuItemToMenu() {
        // Preparation
        Long menuId = 1L;
        Long menuItemId = 1L;
        MenuItem menuItem = new MenuItem("Pizza", 15.99, "Delicious cheese pizza", "pizza.jpg");
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(breakfastMenu));
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(menuItem));

        // Action
        menuService.addMenuItemToMenu(menuId, menuItemId);

        // Verification
        assertTrue(breakfastMenu.getMenuItems().contains(menuItem));
        verify(menuRepository).save(breakfastMenu);
    }

    @Test
    void addMenuItemToMenu_ThrowsResourceNotFoundExceptionWhenMenuNotFound() {
        // Preparation
        Long menuId = 1L;
        Long menuItemId = 1L;
        when(menuRepository.findById(menuId)).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuService.addMenuItemToMenu(menuId, menuItemId));
    }

    @Test
    void addMenuItemToMenu_ThrowsResourceNotFoundExceptionWhenMenuItemNotFound() {
        // Preparation
        Long menuId = 1L;
        Long menuItemId = 1L;
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(breakfastMenu));
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> menuService.addMenuItemToMenu(menuId, menuItemId));
    }

}
