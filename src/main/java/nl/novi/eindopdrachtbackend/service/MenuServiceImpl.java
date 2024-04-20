package nl.novi.eindopdrachtbackend.service;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.MenuInputDTO;
import nl.novi.eindopdrachtbackend.dto.MenuMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuServiceImpl(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuDTO createMenu(MenuInputDTO menuInputDTO) {
        Menu menu = MenuMapper.toMenu(menuInputDTO);
        if (menuInputDTO.getMenuItemIds() != null && !menuInputDTO.getMenuItemIds().isEmpty()) {
            Set<MenuItem> menuItems = menuInputDTO.getMenuItemIds().stream()
                    .map(id -> menuItemRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found for this id :: " + id)))
                    .collect(Collectors.toSet());
            menu.setMenuItems(menuItems);
        }
        Menu savedMenu = menuRepository.save(menu);
        return MenuMapper.toMenuDTO(savedMenu);
    }

    @Transactional
    public MenuDTO updateMenu(Long id, MenuInputDTO menuInputDTO) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found for this id :: " + id));

        existingMenu.setName(menuInputDTO.getName());
        existingMenu.setDescription(menuInputDTO.getDescription());

        Set<MenuItem> currentMenuItems = existingMenu.getMenuItems();
        Set<Long> newMenuItemIds =new HashSet<>(menuInputDTO.getMenuItemIds()) ;

        currentMenuItems.removeIf(menuItem -> !newMenuItemIds.contains(menuItem.getId()));

        for (Long menuItemId : newMenuItemIds) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found for this id :: " + menuItemId));
            if (!currentMenuItems.contains(menuItem)) {
                existingMenu.addMenuItem(menuItem);
            }
        }

        Menu updatedMenu = menuRepository.save(existingMenu);
        return MenuMapper.toMenuDTO(updatedMenu);
    }

    @Override
    public List<MenuDTO> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(MenuMapper::toMenuDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MenuDTO getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found for this id :: " + id));
        return MenuMapper.toMenuDTO(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found for this id :: " + id));
        menuRepository.delete(menu);
    }
    @Override
    public List<MenuDTO> findByNameIgnoreCase(String name) {
        List<Menu> menus = menuRepository.findByNameIgnoreCase(name);
        if (menus.isEmpty()) {
            throw new ResourceNotFoundException("Menu not found with name: " + name);
        }
        return menus.stream()
                .map(MenuMapper::toMenuDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addMenuItemToMenu(Long menuId, Long menuItemId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu not found for this id :: " + menuId));
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found for this id :: " + menuItemId));
        menu.addMenuItem(menuItem);
        menuRepository.save(menu);
    }
}
