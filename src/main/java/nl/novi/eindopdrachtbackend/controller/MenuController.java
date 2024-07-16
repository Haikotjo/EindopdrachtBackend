package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.common.ApiResponse;
import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.MenuInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.MenuItemService;
import nl.novi.eindopdrachtbackend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {


    private final MenuService menuService;
    private final UserRepository userRepository;


    @Autowired
    public MenuController(MenuService menuService, UserRepository userRepository) {
        this.menuService = menuService;
        this.userRepository = userRepository;
    }

    /**
     * Get all menus (Admin only)
     *
     * @return ResponseEntity containing a list of MenuDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        List<MenuDTO> menus = menuService.getAllMenus();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    /**
     * Endpoint for owners to retrieve their own menus.
     *
     * @return ResponseEntity containing a list of MenuDTO objects
     */
    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<MenuDTO>> getAllMenusForLoggedInOwner() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        try {
            List<MenuDTO> menus = menuService.getAllMenusForLoggedInOwner(currentUserEmail);
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all menus for a specific restaurant.
     *
     * @param restaurantId restaurant ID
     * @return ResponseEntity containing a list of MenuDTO objects
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuDTO>> getAllMenusForRestaurant(@PathVariable Long restaurantId) {
        try {
            List<MenuDTO> menus = menuService.getAllMenusForRestaurant(restaurantId);
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get a specific menu by its ID open for all.
     *
     * @param id the ID of the menu
     * @return ResponseEntity containing the MenuDTO object for the specified ID
     */
    @GetMapping("/menu/{id}")
    public ResponseEntity<MenuDTO> getMenuById(@PathVariable Long id) {
        try {
            MenuDTO menu = menuService.getMenuById(id);
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new menu for the logged-in owner.
     *
     * @param menuInputDTO the menu input data transfer object
     * @return ResponseEntity containing the created MenuDTO object
     */
    @PostMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<MenuDTO> createMenuForOwner(@RequestBody MenuInputDTO menuInputDTO) {
        User currentUser = getCurrentUser();
        Restaurant restaurant = currentUser.getRestaurant();
        if (restaurant == null) {
            throw new ResourceNotFoundException("No restaurant found for the current user.");
        }
        MenuDTO newMenu = menuService.createMenuForOwner(menuInputDTO, restaurant.getId());
        return new ResponseEntity<>(newMenu, HttpStatus.CREATED);
    }

    /**
     * Create a new menu for a specific restaurant by an admin.
     *
     * @param restaurantId the ID of the restaurant
     * @param menuInputDTO the menu input data transfer object
     * @return ResponseEntity containing the created MenuDTO object
     */
    @PostMapping("/admin/{restaurantId}/menu")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MenuDTO> createMenuForRestaurantByAdmin(@PathVariable Long restaurantId, @RequestBody MenuInputDTO menuInputDTO) {
        MenuDTO newMenu = menuService.createMenuForRestaurant(menuInputDTO, restaurantId);
        return new ResponseEntity<>(newMenu, HttpStatus.CREATED);
    }

    /**
     * Update an existing menu for the logged-in owner.
     *
     * @param menuId the ID of the menu to update
     * @param menuInputDTO the menu input data transfer object
     * @return ResponseEntity containing the updated MenuDTO object
     */
    @PutMapping("/owner/{menuId}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<MenuDTO> updateMenuForOwner(
            @PathVariable Long menuId,
            @RequestBody MenuInputDTO menuInputDTO) {
        User currentUser = getCurrentUser();
        Restaurant restaurant = currentUser.getRestaurant();
        if (restaurant == null) {
            throw new ResourceNotFoundException("No restaurant found for the current user.");
        }
        MenuDTO updatedMenu = menuService.updateMenuForOwner(menuId, menuInputDTO, restaurant.getId());
        return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
    }

    /**
     * Update an existing menu by an admin, including adding menu items by ID.
     *
     * @param menuId the ID of the menu to update
     * @param menuInputDTO the menu input data transfer object
     * @return ResponseEntity containing the updated MenuDTO object
     */
    @PutMapping("/admin/{menuId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<MenuDTO> updateMenuByAdmin(
            @PathVariable Long menuId,
            @RequestBody MenuInputDTO menuInputDTO) {
        MenuDTO updatedMenu = menuService.updateMenuByAdmin(menuId, menuInputDTO);
        return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
    }

    /**
     * Delete a menu for the logged-in owner.
     *
     * @param menuId the ID of the menu to delete
     * @return ResponseEntity with the status of the operation
     */
    @DeleteMapping("/owner/{menuId}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<Void> deleteMenuForOwner(@PathVariable Long menuId) {
        User currentUser = getCurrentUser();
        Restaurant restaurant = currentUser.getRestaurant(); // Haal het restaurant op van de huidige gebruiker
        if (restaurant == null) {
            throw new ResourceNotFoundException("No restaurant found for the current user.");
        }
        menuService.deleteMenuForOwner(menuId, restaurant.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete a menu by an admin.
     *
     * @param menuId the ID of the menu to delete
     * @return ResponseEntity with the status of the operation
     */
    @DeleteMapping("/admin/{menuId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteMenuByAdmin(@PathVariable Long menuId) {
        menuService.deleteMenuByAdmin(menuId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Find menus by name, ignoring case.
     *
     * @param name the name of the menu
     * @return ResponseEntity containing a list of MenuDTO objects
     */
    @GetMapping("/search")
    public ResponseEntity<List<MenuDTO>> findByNameIgnoreCase(@RequestParam String name) {
        List<MenuDTO> menus = menuService.findByNameIgnoreCase(name);
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    /**
     * Retrieve the currently authenticated user from the security context.
     *
     * This method fetches the email of the currently authenticated user from the security context,
     * retrieves the corresponding User entity from the user repository, and returns the User object.
     * If the user is not found, it throws a ResourceNotFoundException.
     *
     * @return the User object representing the currently authenticated user
     * @throws ResourceNotFoundException if no user is found with the current authenticated email
     */
    private User getCurrentUser() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
        System.out.println("Current User Email: " + currentUserEmail);
        System.out.println("Current User Roles: " + user.getRoles());
        return user;
    }
}
