package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.MenuInputDTO;
import nl.novi.eindopdrachtbackend.model.Menu;

import java.util.List;

public interface MenuService {
    MenuDTO createMenu(MenuInputDTO menuInputDTO);
    MenuDTO updateMenu(Long id, MenuInputDTO menuInputDTO);
    List<MenuDTO> getAllMenus();
    MenuDTO getMenuById(Long id);
    void deleteMenu(Long id);
    List<MenuDTO> findByNameIgnoreCase(String name);
    void addMenuItemToMenu(Long menuId, Long menuItemId);

}
