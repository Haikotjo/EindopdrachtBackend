package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.MenuInputDTO;
import nl.novi.eindopdrachtbackend.model.Menu;

import java.util.List;

public interface MenuService {

    /**
     * Get all menus (Admin only)
     *
     * @return list of all MenuDTOs
     */
    List<MenuDTO> getAllMenus();

    /**
     * Get all menus for the logged-in owner.
     *
     * @param email email of the logged-in owner
     * @return list of MenuDTOs for the logged-in owner
     */
    List<MenuDTO> getAllMenusForLoggedInOwner(String email);

    /**
     * Get all menus for a specific restaurant.
     *
     * @param restaurantId restaurant ID
     * @return list of MenuDTOs for the specified restaurant
     */
    List<MenuDTO> getAllMenusForRestaurant(Long restaurantId);

    /**
     * Get menu by ID.
     *
     * @param id menu ID
     * @return MenuDTO for the specified ID
     */
    MenuDTO getMenuById(Long id);

    /**
     * Create a new menu for the logged-in owner's restaurant.
     *
     * @param menuInputDTO menu input data
     * @param restaurantId the ID of the restaurant
     * @return created MenuDTO
     */
    MenuDTO createMenuForOwner(MenuInputDTO menuInputDTO, Long restaurantId);

    /**
     * Create a new menu for a specific restaurant by an admin.
     *
     * @param menuInputDTO the menu input data transfer object
     * @param restaurantId the ID of the restaurant
     * @return the created MenuDTO
     */
    MenuDTO createMenuForRestaurant(MenuInputDTO menuInputDTO, Long restaurantId);

    /**
     * Update an existing menu for the logged-in owner's restaurant.
     *
     * @param menuId the ID of the menu to update
     * @param menuInputDTO the menu input data
     * @param restaurantId the ID of the restaurant
     * @return the updated MenuDTO
     */
    MenuDTO updateMenuForOwner(Long menuId, MenuInputDTO menuInputDTO, Long restaurantId);

    /**
     * Update an existing menu by an admin.
     *
     * @param menuId the ID of the menu to update
     * @param menuInputDTO the menu input data
     * @return the updated MenuDTO
     */
    MenuDTO updateMenuByAdmin(Long menuId, MenuInputDTO menuInputDTO);

    /**
     * Delete a menu for the logged-in owner's restaurant.
     *
     * @param menuId the ID of the menu to delete
     * @param restaurantId the ID of the restaurant
     */
    void deleteMenuForOwner(Long menuId, Long restaurantId);

    /**
     * Delete a menu by an admin.
     *
     * @param menuId the ID of the menu to delete
     */
    void deleteMenuByAdmin(Long menuId);

    /**
     * Find menus by name, ignoring case.
     *
     * @param name the name of the menu
     * @return list of MenuDTOs
     */
    List<MenuDTO> findByNameIgnoreCase(String name);
}
