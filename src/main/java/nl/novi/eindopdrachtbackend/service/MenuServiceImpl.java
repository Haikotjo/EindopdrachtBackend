package nl.novi.eindopdrachtbackend.service;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.MenuInputDTO;
import nl.novi.eindopdrachtbackend.dto.MenuMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Menu;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.MenuRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;

    public MenuServiceImpl(MenuRepository menuRepository, MenuItemRepository menuItemRepository, UserRepository userRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuDTO> getAllMenus() {
        return menuRepository.findAll().stream()
                .map(MenuMapper::toMenuDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuDTO> getAllMenusForLoggedInOwner(String email) {
        try {
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
            Restaurant restaurant = currentUser.getRestaurant();
            if (restaurant == null) {
                throw new ResourceNotFoundException("No restaurant found for the current user.");
            }
            List<Menu> menus = menuRepository.findByRestaurant_Id(restaurant.getId());
            return menus.stream()
                    .map(MenuMapper::toMenuDTO)
                    .collect(Collectors.toList());
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve menus for user with email " + email, e);
        }
    }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<MenuDTO> getAllMenusForRestaurant(Long restaurantId) {
            try {
                List<Menu> menus = menuRepository.findByRestaurant_Id(restaurantId);
                if (menus.isEmpty()) {
                    throw new ResourceNotFoundException("No menus found for restaurant with ID " + restaurantId);
                }
                return menus.stream()
                        .map(MenuMapper::toMenuDTO)
                        .collect(Collectors.toList());
            } catch (Exception e) {
                throw new RuntimeException("Failed to retrieve menus for restaurant with ID " + restaurantId, e);
            }
        }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuDTO getMenuById(Long id) {
        try {
            Menu menu = menuRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu not found for this id :: " + id));
            return MenuMapper.toMenuDTO(menu);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve menu with id " + id, e);
        }
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
