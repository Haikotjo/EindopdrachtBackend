package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.model.MenuItem;

import java.util.List;

public interface MenuItemService {
    MenuItemDTO createMenuItem(MenuItemInputDTO menuItemInputDTO);
    MenuItemDTO updateMenuItem(Long id, MenuItemInputDTO menuItemInputDTO);
    List<MenuItemDTO> getAllMenuItems();
    MenuItemDTO getMenuItemById(Long id);
    void deleteMenuItem(Long id);
    List<MenuItemDTO> findByNameIgnoreCase(String name);
    void addIngredientToMenuItem(Long menuItemId, Long ingredientId);
}
