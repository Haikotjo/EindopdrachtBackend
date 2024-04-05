package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;

import java.util.List;

public interface MenuItemService {
    MenuItem createMenuItem(MenuItem menuItem);
    MenuItem updateMenuItem (Long id, MenuItem menuItemDetails);
    List<MenuItem> getAllMenuItems();
    MenuItem getMenuItemById(Long id);
    void deleteMenuItem(Long id);
    List<MenuItem> findByNameIgnoreCase(String name);
    void addIngredientToMenuItem(Long menuItemId, Long ingredientId);

}
