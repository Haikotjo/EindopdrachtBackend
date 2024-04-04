package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService{

    private final MenuItemRepository menuItemRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public MenuItem createMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long id, MenuItem menuItemDetails) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found for this id :: " + id));

        menuItem.setName(menuItemDetails.getName());
        menuItem.setDescription(menuItemDetails.getDescription());
        menuItem.setPrice(menuItemDetails.getPrice());
        menuItem.setImage(menuItemDetails.getImage());

        return menuItemRepository.save(menuItem);
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found for this id :: " + id));
    }

    @Override
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found for this id :: " + id));

        menuItemRepository.delete(menuItem);
    }

    @Override
    public List<MenuItem> findByNameIgnoreCase(String name) {
        List<MenuItem> menuItem = menuItemRepository.findByNameIgnoreCase(name);
        if (menuItem.isEmpty()){
            throw new ResourceNotFoundException("Menu item not found with name: " + name);
        }
        return menuItem;
    }
}
