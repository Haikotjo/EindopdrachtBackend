package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.MenuInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepository;
    @Mock
    private MenuItemRepository menuItemRepository;
    @InjectMocks
    private MenuServiceImpl menuService;

    private MenuDTO menuDTO;
    private MenuInputDTO updateMenuDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menuDTO = new MenuDTO();
        menuDTO.setId(1L);
        menuDTO.setName("Lunch Menu");
        menuDTO.setMenuItems(new ArrayList<>()); // Assume MenuDTO includes a list of MenuItemDTOs

        updateMenuDTO = new MenuInputDTO();
        updateMenuDTO.setName("Updated Lunch Menu");
        updateMenuDTO.setMenuItemIds(Arrays.asList(1L, 2L)); // Assume MenuInputDTO includes a list of MenuItem IDs
    }

    @Test
    void getAllMenus_ReturnsListOfMenus() {
        // Arrange
        Menu menu = new Menu();
        menu.setName(menuDTO.getName());
        List<Menu> menus = new ArrayList<>();
        menus.add(menu);

        when(menuRepository.findAll()).thenReturn(menus);

        // Act
        List<MenuDTO> menuDTOList = menuService.getAllMenus();

        // Assert
        assertNotNull(menuDTOList);
        assertEquals(1, menuDTOList.size());
        assertEquals(menuDTO.getName(), menuDTOList.get(0).getName());
        verify(menuRepository, times(1)).findAll();
    }

    @Test
    void createMenu_WithMenuItems_ReturnsCreatedMenu() {
        // Arrange
        MenuInputDTO newMenuDTO = new MenuInputDTO();
        newMenuDTO.setName("Summer Menu");
        newMenuDTO.setMenuItemIds(Arrays.asList(1L));  // IDs of existing menu items

        MenuItem menuItem = new MenuItem();
        menuItem.setName("Salad");

        // Stel de mock in om een reeds geconfigureerd MenuItem te retourneren, simuleer het vinden ervan op ID
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        // Simuleer het gedrag van het opslaan van een Menu en retourneer het direct zoals het is opgeslagen
        when(menuRepository.save(any(Menu.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        MenuDTO createdMenuDTO = menuService.createMenu(newMenuDTO);

        // Assert
        assertNotNull(createdMenuDTO);
        assertEquals(newMenuDTO.getName(), createdMenuDTO.getName());
        assertFalse(createdMenuDTO.getMenuItems().isEmpty());
        assertEquals("Salad", createdMenuDTO.getMenuItems().get(0).getName());  // Controleer of het menu-item correct is toegevoegd
        verify(menuRepository).save(any(Menu.class));
        verify(menuItemRepository).findById(1L);
    }


    @Test
    void updateMenu_WithMenuItems_ReturnsUpdatedMenu() {
        // Arrange
        Long menuId = 1L;
        Menu existingMenu = new Menu();
        existingMenu.setName("Old Menu");

        updateMenuDTO = new MenuInputDTO();
        updateMenuDTO.setName("Updated Menu");
        updateMenuDTO.setMenuItemIds(Arrays.asList(1L)); // IDs of existing menu items

        MenuItem menuItem = new MenuItem();
        menuItem.setName("Updated Item");

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(existingMenu));
        when(menuRepository.save(any(Menu.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));

        // Act
        MenuDTO updatedMenuDTO = menuService.updateMenu(menuId, updateMenuDTO);

        // Assert
        assertNotNull(updatedMenuDTO);
        assertEquals(updateMenuDTO.getName(), updatedMenuDTO.getName());
        assertFalse(updatedMenuDTO.getMenuItems().isEmpty());
        assertEquals("Updated Item", updatedMenuDTO.getMenuItems().get(0).getName());
        verify(menuRepository).findById(menuId);
        verify(menuRepository).save(any(Menu.class));
        verify(menuItemRepository).findById(1L);
    }

    @Test
    void findByNameIgnoreCase_ReturnsListOfMenus() {
        // Arrange
        Menu menu = new Menu();
        menu.setName("Brunch Menu");
        List<Menu> menus = Arrays.asList(menu);

        when(menuRepository.findByNameIgnoreCase("brunch")).thenReturn(menus);

        // Act
        List<MenuDTO> result = menuService.findByNameIgnoreCase("brunch");

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Brunch Menu", result.get(0).getName());
        verify(menuRepository).findByNameIgnoreCase("brunch");
    }

    @Test
    void addMenuItemToMenu_AddsMenuItem() {
        // Arrange
        Long menuId = 1L;
        Long menuItemId = 2L;
        Menu menu = new Menu();
        MenuItem menuItem = new MenuItem();

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(menuItem));

        // Act
        menuService.addMenuItemToMenu(menuId, menuItemId);

        // Assert
        assertTrue(menu.getMenuItems().contains(menuItem)); // Verifies if menuItem is added correctly
        verify(menuRepository).save(menu);
        verify(menuRepository).findById(menuId);
        verify(menuItemRepository).findById(menuItemId);
    }


    @Test
    void deleteMenu_DeletesMenu() {
        // Arrange
        Long menuId = 1L;
        Menu menu = new Menu();

        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        // Act
        menuService.deleteMenu(menuId);

        // Assert
        verify(menuRepository).delete(menu);
        verify(menuRepository).findById(menuId);
    }

}
