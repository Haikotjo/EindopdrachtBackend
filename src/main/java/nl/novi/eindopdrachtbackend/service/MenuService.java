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

    MenuDTO createMenu(MenuInputDTO menuInputDTO);
    MenuDTO updateMenu(Long id, MenuInputDTO menuInputDTO);
    void deleteMenu(Long id);
    List<MenuDTO> findByNameIgnoreCase(String name);
    void addMenuItemToMenu(Long menuId, Long menuItemId);

}
