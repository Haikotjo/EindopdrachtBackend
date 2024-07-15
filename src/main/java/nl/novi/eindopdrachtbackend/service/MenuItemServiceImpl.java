package nl.novi.eindopdrachtbackend.service;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
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
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, IngredientRepository ingredientRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuItemDTO> getAllMenuItemsForLoggedInOwner(String email) {
        try {
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
            return getAllMenuItemsForOwner(currentUser.getId());
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve menu items for user with email " + email, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MenuItemDTO> getAllMenuItemsForRestaurant(Long restaurantId) {
        try {
            List<MenuItem> menuItems = menuItemRepository.findByRestaurant_Id(restaurantId);
            if (menuItems.isEmpty()) {
                throw new ResourceNotFoundException("No menu items found for restaurant with ID " + restaurantId);
            }
            return menuItems.stream()
                    .map(MenuItemMapper::toMenuItemDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve menu items for restaurant with ID " + restaurantId, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItemDTO getMenuItemByIdForOwner(Long id, Long ownerId) {
        try {
            MenuItem menuItem = menuItemRepository.findByIdAndRestaurant_Owner_Id(id, ownerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found for this id :: " + id + " and owner id :: " + ownerId));
            return MenuItemMapper.toMenuItemDTO(menuItem);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve menu item with id " + id + " for owner with id " + ownerId, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItemDTO getMenuItemById(Long id) {
        try {
            MenuItem menuItem = menuItemRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found for this id :: " + id));
            return MenuItemMapper.toMenuItemDTO(menuItem);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve menu item with id " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItemDTO createMenuItemForOwner(MenuItemInputDTO menuItemInputDTO, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantId));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemInputDTO.getName());
        menuItem.setPrice(menuItemInputDTO.getPrice());
        menuItem.setDescription(menuItemInputDTO.getDescription());
        menuItem.setImage(menuItemInputDTO.getImage());
        menuItem.setRestaurant(restaurant);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return MenuItemMapper.toMenuItemDTO(savedMenuItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItemDTO createMenuItemForRestaurant(MenuItemInputDTO menuItemInputDTO, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantId));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemInputDTO.getName());
        menuItem.setPrice(menuItemInputDTO.getPrice());
        menuItem.setDescription(menuItemInputDTO.getDescription());
        menuItem.setImage(menuItemInputDTO.getImage());
        menuItem.setRestaurant(restaurant);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return MenuItemMapper.toMenuItemDTO(savedMenuItem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuItemDTO updateMenuItemForOwner(Long menuItemId, MenuItemInputDTO menuItemInputDTO, Long restaurantId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("MenuItem not found for this id :: " + menuItemId));

        if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
            throw new AccessDeniedException("You do not have permission to modify this menu item.");
        }

        menuItem.setName(menuItemInputDTO.getName());
        menuItem.setPrice(menuItemInputDTO.getPrice());
        menuItem.setDescription(menuItemInputDTO.getDescription());
        menuItem.setImage(menuItemInputDTO.getImage());

        // Voeg nieuwe ingrediënten toe aan het menu-item
        Set<Ingredient> ingredients = new HashSet<>();
        for (Long ingredientId : menuItemInputDTO.getIngredientIds()) {
            Ingredient ingredient = ingredientRepository.findById(ingredientId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + ingredientId));
            ingredients.add(ingredient);
        }
        menuItem.setIngredients(ingredients);

        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);
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
