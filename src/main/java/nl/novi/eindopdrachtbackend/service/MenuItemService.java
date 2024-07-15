package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.User;

import java.util.List;


/**
 * Service interface for managing menu items.
 */
public interface MenuItemService {

    /**
     * Get all menu items (Admin only)
     *
     * @return list of all MenuItemDTOs
     */
    List<MenuItemDTO> getAllMenuItems();

    /**
     * Get all menu items for a specific owner (Admin only).
     *
     * @param ownerId owner ID
     * @return list of MenuItemDTOs for the specified owner
     */
    List<MenuItemDTO> getAllMenuItemsForOwner(Long ownerId);

    /**
     * Get all menu items for the logged-in owner.
     *
     * @param email email of the logged-in owner
     * @return list of MenuItemDTOs for the logged-in owner
     */
    List<MenuItemDTO> getAllMenuItemsForLoggedInOwner(String email);

    /**
     * Get all menu items for a specific restaurant.
     *
     * @param restaurantId restaurant ID
     * @return list of MenuItemDTOs for the specified restaurant
     */
    List<MenuItemDTO> getAllMenuItemsForRestaurant(Long restaurantId);

    /**
     * Get menu item by ID for Owner (own menu items only).
     *
     * @param id menu item ID
     * @param ownerId owner ID
     * @return MenuItemDTO for the specified ID and owner
     */
    MenuItemDTO getMenuItemByIdForOwner(Long id, Long ownerId);

    /**
     * Get menu item by ID.
     *
     * @param id menu item ID
     * @return MenuItemDTO for the specified ID
     */
    MenuItemDTO getMenuItemById(Long id);

    /**
     * Create a new menu item for the logged-in owner's restaurant.
     *
     * @param menuItemInputDTO menu item input data
     * @param restaurantId the ID of the restaurant
     * @return created MenuItemDTO
     */
    MenuItemDTO createMenuItemForOwner(MenuItemInputDTO menuItemInputDTO, Long restaurantId);

    /**
     * Create a new menu item for a specific restaurant by an admin.
     *
     * @param menuItemInputDTO the menu item input data transfer object
     * @param restaurantId the ID of the restaurant
     * @return the created MenuItemDTO
     */
    MenuItemDTO createMenuItemForRestaurant(MenuItemInputDTO menuItemInputDTO, Long restaurantId);

    /**
     * Update an existing menu item for the logged-in owner's restaurant.
     *
     * @param menuItemId the ID of the menu item to update
     * @param menuItemInputDTO the menu item input data
     * @param restaurantId the ID of the restaurant
     * @return the updated MenuItemDTO
     */
    MenuItemDTO updateMenuItemForOwner(Long menuItemId, MenuItemInputDTO menuItemInputDTO, Long restaurantId);

    /**
     * Update an existing menu item by an admin.
     *
     * @param menuItemId the ID of the menu item to update
     * @param menuItemInputDTO the menu item input data
     * @return the updated MenuItemDTO
     */
    MenuItemDTO updateMenuItemByAdmin(Long menuItemId, MenuItemInputDTO menuItemInputDTO);

    /**
     * Delete a menu item for the logged-in owner's restaurant.
     *
     * @param menuItemId the ID of the menu item to delete
     * @param restaurantId the ID of the restaurant
     */
    void deleteMenuItemForOwner(Long menuItemId, Long restaurantId);

    /**
     * Delete a menu item by an admin.
     *
     * @param menuItemId the ID of the menu item to delete
     */
    void deleteMenuItemByAdmin(Long menuItemId);


    List<MenuItemDTO> findByNameIgnoreCase(String name);
}
