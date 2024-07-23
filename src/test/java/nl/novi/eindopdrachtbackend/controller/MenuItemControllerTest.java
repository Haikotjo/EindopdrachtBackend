package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuItemControllerTest {

    @Mock
    private MenuItemService menuItemService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MenuItemController menuItemController;

    private User user;
    private Restaurant restaurant;
    private MenuItemDTO menuItemDTO;
    private MenuItemInputDTO menuItemInputDTO;

    @BeforeEach
    void setUp() throws Exception {
        user = new User();
        setField(user, "id", 1L);
        user.setEmail("test@example.com");

        restaurant = new Restaurant();
        setField(restaurant, "id", 1L);
        restaurant.setName("Test Restaurant");
        user.setRestaurant(restaurant);

        menuItemDTO = new MenuItemDTO();
        setField(menuItemDTO, "id", 1L);
        menuItemDTO.setName("Test MenuItem");

        menuItemInputDTO = new MenuItemInputDTO();
        menuItemInputDTO.setName("Test MenuItem Input");
        menuItemInputDTO.setDescription("Test Description");
        menuItemInputDTO.setPrice(10.0);
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllMenuItems() {
        when(menuItemService.getAllMenuItems()).thenReturn(List.of(menuItemDTO));

        ResponseEntity<List<MenuItemDTO>> response = menuItemController.getAllMenuItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(menuItemService, times(1)).getAllMenuItems();
    }

    @Test
    void testGetAllMenuItemsForOwner() {
        when(menuItemService.getAllMenuItemsForOwner(anyLong())).thenReturn(List.of(menuItemDTO));

        ResponseEntity<List<MenuItemDTO>> response = menuItemController.getAllMenuItemsForOwner(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(menuItemService, times(1)).getAllMenuItemsForOwner(anyLong());
    }

    @Test
    void testGetAllMenuItemsForLoggedInOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Lenient mocking for UserRepository.findByEmail() method
            lenient().when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

            // Mocking MenuItemService.getAllMenuItemsForLoggedInOwner() method
            when(menuItemService.getAllMenuItemsForLoggedInOwner(anyString())).thenReturn(List.of(menuItemDTO));

            // Calling the controller method
            ResponseEntity<List<MenuItemDTO>> response = menuItemController.getAllMenuItemsForLoggedInOwner();

            // Verifying the response
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(1, response.getBody().size());

            // Verifying the mocked interactions
            verify(menuItemService, times(1)).getAllMenuItemsForLoggedInOwner(anyString());
        }
    }

    @Test
    void testGetAllMenuItemsForRestaurant() {
        when(menuItemService.getAllMenuItemsForRestaurant(anyLong())).thenReturn(List.of(menuItemDTO));

        ResponseEntity<List<MenuItemDTO>> response = menuItemController.getAllMenuItemsForRestaurant(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(menuItemService, times(1)).getAllMenuItemsForRestaurant(anyLong());
    }

    @Test
    void testGetMenuItemByIdForOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Mocking UserRepository.findByEmail() method
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

            // Mocking MenuItemService.getMenuItemByIdForOwner() method
            when(menuItemService.getMenuItemByIdForOwner(anyLong(), anyLong())).thenReturn(menuItemDTO);

            // Calling the controller method
            ResponseEntity<MenuItemDTO> response = menuItemController.getMenuItemByIdForOwner(1L);

            // Verifying the response
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(menuItemDTO, response.getBody());

            // Verifying the mocked interactions
            verify(menuItemService, times(1)).getMenuItemByIdForOwner(anyLong(), anyLong());
        }
    }

    @Test
    void testGetMenuItemById() {
        when(menuItemService.getMenuItemById(anyLong())).thenReturn(menuItemDTO);

        ResponseEntity<MenuItemDTO> response = menuItemController.getMenuItemById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(menuItemDTO, response.getBody());
        verify(menuItemService, times(1)).getMenuItemById(anyLong());
    }

    @Test
    void testCreateMenuItemForOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Mocking UserRepository.findByEmail() method
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

            // Mocking MenuItemService.createMenuItemForOwner() method
            when(menuItemService.createMenuItemForOwner(any(MenuItemInputDTO.class), anyLong())).thenReturn(menuItemDTO);

            // Calling the controller method
            ResponseEntity<MenuItemDTO> response = menuItemController.createMenuItemForOwner(menuItemInputDTO);

            // Verifying the response
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(menuItemDTO, response.getBody());

            // Verifying the mocked interactions
            verify(menuItemService, times(1)).createMenuItemForOwner(any(MenuItemInputDTO.class), anyLong());
        }
    }

    @Test
    void testCreateMenuItemForRestaurantByAdmin() {
        when(menuItemService.createMenuItemForRestaurant(any(MenuItemInputDTO.class), anyLong())).thenReturn(menuItemDTO);

        ResponseEntity<MenuItemDTO> response = menuItemController.createMenuItemForRestaurantByAdmin(1L, menuItemInputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(menuItemDTO, response.getBody());
        verify(menuItemService, times(1)).createMenuItemForRestaurant(any(MenuItemInputDTO.class), anyLong());
    }

    @Test
    void testUpdateMenuItemForOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Mocking UserRepository.findByEmail() method
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

            // Mocking MenuItemService.updateMenuItemForOwner() method
            when(menuItemService.updateMenuItemForOwner(anyLong(), any(MenuItemInputDTO.class), anyLong())).thenReturn(menuItemDTO);

            // Calling the controller method
            ResponseEntity<MenuItemDTO> response = menuItemController.updateMenuItemForOwner(1L, menuItemInputDTO);

            // Verifying the response
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(menuItemDTO, response.getBody());

            // Verifying the mocked interactions
            verify(menuItemService, times(1)).updateMenuItemForOwner(anyLong(), any(MenuItemInputDTO.class), anyLong());
        }
    }

    @Test
    void testUpdateMenuItemByAdmin() {
        when(menuItemService.updateMenuItemByAdmin(anyLong(), any(MenuItemInputDTO.class))).thenReturn(menuItemDTO);

        ResponseEntity<MenuItemDTO> response = menuItemController.updateMenuItemByAdmin(1L, menuItemInputDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(menuItemDTO, response.getBody());
        verify(menuItemService, times(1)).updateMenuItemByAdmin(anyLong(), any(MenuItemInputDTO.class));
    }

    @Test
    void testDeleteMenuItemForOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Mocking UserRepository.findByEmail() method
            User mockUser = new User();
            setField(mockUser, "id", 1L);
            mockUser.setEmail("test@example.com");

            // Voeg een restaurant toe aan de mockUser
            Restaurant mockRestaurant = new Restaurant();
            setField(mockRestaurant, "id", 1L);
            mockUser.setRestaurant(mockRestaurant);

            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));

            // Mocking MenuItemService.deleteMenuItemForOwner() method
            doNothing().when(menuItemService).deleteMenuItemForOwner(anyLong(), anyLong());

            // Calling the controller method
            ResponseEntity<Void> response = menuItemController.deleteMenuItemForOwner(1L);

            // Verifying the response
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

            // Verifying the mocked interactions
            verify(menuItemService, times(1)).deleteMenuItemForOwner(anyLong(), anyLong());
        }
    }

    @Test
    void testDeleteMenuItemByAdmin() {
        doNothing().when(menuItemService).deleteMenuItemByAdmin(anyLong());

        ResponseEntity<Void> response = menuItemController.deleteMenuItemByAdmin(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(menuItemService, times(1)).deleteMenuItemByAdmin(anyLong());
    }

    @Test
    void testFindByNameIgnoreCase() {
        when(menuItemService.findByNameIgnoreCase(anyString())).thenReturn(List.of(menuItemDTO));

        ResponseEntity<List<MenuItemDTO>> response = menuItemController.findByNameIgnoreCase("Test MenuItem");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(menuItemService, times(1)).findByNameIgnoreCase(anyString());
    }
}
