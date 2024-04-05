package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService{

    private final MenuItemRepository menuItemRepository;
    private final IngredientRepository ingredientRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, IngredientRepository ingredientRepository) {
        this.menuItemRepository = menuItemRepository;
        this.ingredientRepository = ingredientRepository;
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

    @Override
    public void addIngredientToMenuItem(Long menuItemId, Long ingredientId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found for this id :: " + menuItemId));

        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + ingredientId));

        menuItem.addIngredient(ingredient);
        menuItemRepository.save(menuItem);
    }
}
