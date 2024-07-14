package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.model.MenuItem;

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

    MenuItemDTO createMenuItem(MenuItemInputDTO menuItemInputDTO);
    MenuItemDTO updateMenuItem(Long id, MenuItemInputDTO menuItemInputDTO);
    void deleteMenuItem(Long id);
    List<MenuItemDTO> findByNameIgnoreCase(String name);
    void addIngredientToMenuItem(Long menuItemId, Long ingredientId);
}
