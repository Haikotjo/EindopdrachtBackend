package nl.novi.eindopdrachtbackend.service;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for managing menu items.
 */
@Service
public class MenuItemServiceImpl implements MenuItemService{

    private final MenuItemRepository menuItemRepository;
    private final IngredientRepository ingredientRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, IngredientRepository ingredientRepository) {
        this.menuItemRepository = menuItemRepository;
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuItemDTO> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        return menuItems.stream()
                .map(MenuItemMapper::toMenuItemDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuItemDTO> getAllMenuItemsForOwner(Long ownerId) {
        try {
            List<MenuItem> menuItems = menuItemRepository.findByRestaurant_Owner_Id(ownerId);
            if (menuItems.isEmpty()) {
                throw new ResourceNotFoundException("No menu items found for owner with ID " + ownerId);
            }
            return menuItems.stream()
                    .map(MenuItemMapper::toMenuItemDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve menu items for owner with ID " + ownerId, e);
        }
    }





    @Override
    public MenuItemDTO createMenuItem(MenuItemInputDTO menuItemInputDTO) {
        MenuItem menuItem = MenuItemMapper.toMenuItem(menuItemInputDTO);
        if (menuItemInputDTO.getIngredientIds() != null && !menuItemInputDTO.getIngredientIds().isEmpty()) {
            Set<Ingredient> ingredients = menuItemInputDTO.getIngredientIds().stream()
                    .map(id -> ingredientRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id)))
                    .collect(Collectors.toSet());
            menuItem.setIngredients(ingredients);
        }
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return MenuItemMapper.toMenuItemDTO(savedMenuItem);
    }

    @Transactional
    public MenuItemDTO updateMenuItem(Long id, MenuItemInputDTO menuItemInputDTO) {
        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found for this id :: " + id));

        existingMenuItem.setName(menuItemInputDTO.getName());
        existingMenuItem.setDescription(menuItemInputDTO.getDescription());
        existingMenuItem.setPrice(menuItemInputDTO.getPrice());
        existingMenuItem.setImage(menuItemInputDTO.getImage());

        Set<Ingredient> currentIngredients = existingMenuItem.getIngredients();
        Set<Long> newIngredientIds = new HashSet<>(menuItemInputDTO.getIngredientIds());

        currentIngredients.removeIf(ingredient -> !newIngredientIds.contains(ingredient.getId()));

        for (Long ingredientId : newIngredientIds) {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + ingredientId));
            if (!currentIngredients.contains(ingredient)) {
                existingMenuItem.addIngredient(ingredient);
            }
        }

        MenuItem updatedMenuItem = menuItemRepository.save(existingMenuItem);
        return MenuItemMapper.toMenuItemDTO(updatedMenuItem);
    }

    @Override
    public void deleteMenuItem(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu Item not found for this id :: " + id));
        menuItemRepository.delete(menuItem);
    }

    @Override
    public List<MenuItemDTO> findByNameIgnoreCase(String name) {
        List<MenuItem> menuItem = menuItemRepository.findByNameIgnoreCase(name);
        if (menuItem.isEmpty()){
            throw new ResourceNotFoundException("Menu item not found with name: " + name);
        }
        return menuItem.stream()
                .map(MenuItemMapper::toMenuItemDTO)
                .collect(Collectors.toList());
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
