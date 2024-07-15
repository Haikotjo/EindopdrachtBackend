package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.common.ApiResponse;
import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing menu items.
 */
@RestController
@RequestMapping("/menu-items")
public class MenuItemController {


    private final MenuItemService menuItemService;
    private final UserRepository userRepository;

    @Autowired
    public MenuItemController(MenuItemService menuItemService, UserRepository userRepository) {
        this.menuItemService = menuItemService;
        this.userRepository = userRepository;
    }

    /**
     * Get all MenuItems (Admin only)
     *
     * @return ResponseEntity containing a list of MenuItemsDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItems() {
        List<MenuItemDTO> menuItems = menuItemService.getAllMenuItems();
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    /**
     * Get all menu items for a specific owner (Admin only).
     *
     * @param ownerId owner ID
     * @return ResponseEntity containing a list of MenuItemDTO objects
     */
    @GetMapping("/admin/{ownerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItemsForOwner(@PathVariable Long ownerId) {
        try {
            List<MenuItemDTO> menuItems = menuItemService.getAllMenuItemsForOwner(ownerId);
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for owners to retrieve their own menu items.
     *
     * @return ResponseEntity containing a list of MenuItemDTO objects
     */
    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItemsForLoggedInOwner() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        try {
            List<MenuItemDTO> menuItems = menuItemService.getAllMenuItemsForLoggedInOwner(currentUserEmail);
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all menu items for a specific restaurant.
     *
     * @param restaurantId restaurant ID
     * @return ResponseEntity containing a list of MenuItemDTO objects
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItemDTO>> getAllMenuItemsForRestaurant(@PathVariable Long restaurantId) {
        try {
            List<MenuItemDTO> menuItems = menuItemService.getAllMenuItemsForRestaurant(restaurantId);
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get a specific menu item for the logged-in owner.
     *
     * @param id the ID of the menu item
     * @return ResponseEntity containing the Menu itemsDTO object
     */
    @GetMapping("/owner/menu-item/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<MenuItemDTO> getMenuItemByIdForOwner(@PathVariable Long id) {
        try {
            String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
            User currentUser = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
            MenuItemDTO menuItem = menuItemService.getMenuItemByIdForOwner(id, currentUser.getId());
            return new ResponseEntity<>(menuItem, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get a specific menu item by its ID open for all.
     *
     * @param id the ID of the menu item
     * @return ResponseEntity containing the MenuItemDTO object for the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable Long id) {
        try {
            MenuItemDTO menuItem = menuItemService.getMenuItemById(id);
            return new ResponseEntity<>(menuItem, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new menu item for the logged-in owner.
     *
     * @param menuItemInputDTO the menu item input data transfer object
     * @return ResponseEntity containing the created MenuItemDTO object
     */
    @PostMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<MenuItemDTO> createMenuItemForOwner(@RequestBody MenuItemInputDTO menuItemInputDTO) {
        User currentUser = getCurrentUser();
        Restaurant restaurant = currentUser.getRestaurant(); // Haal het restaurant op van de huidige gebruiker
        if (restaurant == null) {
            throw new ResourceNotFoundException("No restaurant found for the current user.");
        }
        MenuItemDTO newMenuItem = menuItemService.createMenuItemForOwner(menuItemInputDTO, restaurant.getId());
        return new ResponseEntity<>(newMenuItem, HttpStatus.CREATED);
    }

    /**
     * Create a new menu item for a specific restaurant by an admin.
     *
     * @param restaurantId the ID of the restaurant
     * @param menuItemInputDTO the menu item input data transfer object
     * @return ResponseEntity containing the created MenuItemDTO object
     */
    @PostMapping("/admin/{restaurantId}/menu-item")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MenuItemDTO> createMenuItemForRestaurantByAdmin(@PathVariable Long restaurantId, @RequestBody MenuItemInputDTO menuItemInputDTO) {
        MenuItemDTO newMenuItem = menuItemService.createMenuItemForRestaurant(menuItemInputDTO, restaurantId);
        return new ResponseEntity<>(newMenuItem, HttpStatus.CREATED);
    }





    private User getCurrentUser() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
        System.out.println("Current User Email: " + currentUserEmail);
        System.out.println("Current User Roles: " + user.getRoles());
        return user;
    }







    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMenuItem(@PathVariable Long id, @RequestBody MenuItemInputDTO menuItemInputDTO) {
        MenuItemDTO updatedMenuItemDTO = menuItemService.updateMenuItem(id, menuItemInputDTO);
        ApiResponse apiResponse = new ApiResponse(true, "MenuItem succesfully updated.", updatedMenuItemDTO);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuItemService.deleteMenuItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuItemDTO>> findByNameIgnoreCase(@RequestParam String name) {
        List<MenuItemDTO> menuItems = menuItemService.findByNameIgnoreCase(name);
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

    @PostMapping("/{menuItemId}/addIngredient/{ingredientId}")
    public ResponseEntity<Void> addIngredientToMenuItem(@PathVariable Long menuItemId, @PathVariable Long ingredientId) {
        menuItemService.addIngredientToMenuItem(menuItemId, ingredientId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
