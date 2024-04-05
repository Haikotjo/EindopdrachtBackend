package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.model.Menu;

import java.util.List;

public interface MenuService {
    Menu createMenu(Menu menu);
    Menu updateMenu(Long id, Menu menuDetails);
    List<Menu> getAllMenus();
    Menu getMenuById(Long id);
    void deleteMenu(Long id);
    List<Menu> findByNameIgnoreCase(String name);
    void addMenuItemToMenu(Long menuId, Long menuItemId);

}
