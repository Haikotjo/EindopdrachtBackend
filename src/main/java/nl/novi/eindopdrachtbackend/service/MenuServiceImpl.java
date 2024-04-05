package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuServiceImpl(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    @Override
    public Menu updateMenu(Long id, Menu menuDetails) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found for this id :: " + id));

        menu.setName(menuDetails.getName());
        menu.setDescription(menuDetails.getDescription());

        return menuRepository.save(menu);
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found for this id :: " + id));
    }

    @Override
    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found for this id :: " + id));

        menuRepository.delete(menu);
    }

    @Override
    public List<Menu> findByNameIgnoreCase(String name) {
        List<Menu> menus = menuRepository.findByNameIgnoreCase(name);
        if (menus.isEmpty()) {
            throw new ResourceNotFoundException("Menu not found with name: " + name);
        }
        return menus;
    }

    @Override
    public void addMenuItemToMenu(Long menuId, Long menuItemId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found for this id :: " + menuId));

        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found for this id :: " + menuItemId));

        menu.getMenuItems().add(menuItem);
        menuRepository.save(menu);
    }
}
