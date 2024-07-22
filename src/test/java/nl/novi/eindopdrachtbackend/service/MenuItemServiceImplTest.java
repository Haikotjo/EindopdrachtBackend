//package nl.novi.eindopdrachtbackend.service;
//
//import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
//import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
//import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
//import nl.novi.eindopdrachtbackend.model.Ingredient;
//import nl.novi.eindopdrachtbackend.model.MenuItem;
//import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
//import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class MenuItemServiceImplTest {
//
//    @Mock
//    private MenuItemRepository menuItemRepository;
//    @Mock
//    private IngredientRepository ingredientRepository;
//    @InjectMocks
//    private MenuItemServiceImpl menuItemService;
//
//    private MenuItemDTO pizzaDTO;
//    private MenuItemInputDTO updatePizzaDTO;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        pizzaDTO = new MenuItemDTO();
//        pizzaDTO.setId(1L);
//        pizzaDTO.setName("Pizza");
//        pizzaDTO.setPrice(9.99);
//        pizzaDTO.setDescription("Delicious cheese pizza");
//        pizzaDTO.setImage("pizza.jpg");
//
//        updatePizzaDTO = new MenuItemInputDTO();
//        updatePizzaDTO.setName("Updated Pizza");
//        updatePizzaDTO.setPrice(10.99);
//        updatePizzaDTO.setDescription("Delicious cheese pizza with extra toppings");
//        updatePizzaDTO.setImage("updated_pizza.jpg");
//    }
//
//    @Test
//    void getAllMenuItems_ReturnsListOfMenuItems() {
//        // Arrange
//        MenuItem pizza = new MenuItem();
//        pizza.setName(pizzaDTO.getName());
//        pizza.setPrice(pizzaDTO.getPrice());
//        List<MenuItem> menuItems = new ArrayList<>();
//        menuItems.add(pizza);
//
//        when(menuItemRepository.findAll()).thenReturn(menuItems);
//
//        // Act
//        List<MenuItemDTO> menuItemDTOList = menuItemService.getAllMenuItems();
//
//        // Assert
//        assertNotNull(menuItemDTOList);
//        assertEquals(1, menuItemDTOList.size());
//        assertEquals(pizzaDTO.getName(), menuItemDTOList.get(0).getName());
//        verify(menuItemRepository, times(1)).findAll();
//    }
//
//    @Test
//    void createMenuItem_WithIngredients_ReturnsCreatedMenuItem() {
//        // Arrange
//        MenuItemInputDTO newMenuItemDTO = new MenuItemInputDTO();
//        newMenuItemDTO.setName("New Pizza");
//        newMenuItemDTO.setPrice(12.99);
//        newMenuItemDTO.setDescription("Spicy pizza with extra cheese");
//        newMenuItemDTO.setImage("spicy_pizza.jpg");
//        newMenuItemDTO.setIngredientIds(Arrays.asList(1L, 2L));  // IDs of existing ingredients
//
//        Ingredient cheese = new Ingredient("Cheese", 100);
//        Ingredient pepper = new Ingredient("Pepper", 20);
//
//        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(cheese));
//        when(ingredientRepository.findById(2L)).thenReturn(Optional.of(pepper));
//        when(menuItemRepository.save(any(MenuItem.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        MenuItemDTO createdMenuItemDTO = menuItemService.createMenuItem(newMenuItemDTO);
//
//        // Assert
//        assertNotNull(createdMenuItemDTO);
//        assertEquals(newMenuItemDTO.getName(), createdMenuItemDTO.getName());
//        assertEquals(newMenuItemDTO.getPrice(), createdMenuItemDTO.getPrice());
//        assertEquals(2, createdMenuItemDTO.getIngredients().size()); // Check if two ingredients are added
//        verify(menuItemRepository).save(any(MenuItem.class));
//        verify(ingredientRepository).findById(1L);
//        verify(ingredientRepository).findById(2L);
//    }
//
//    @Test
//    void updateMenuItem_WithIngredients_ReturnsUpdatedMenuItem() {
//        // Arrange
//        Long menuItemId = 1L;
//        MenuItem existingMenuItem = new MenuItem("Old Pizza", 10.99, "Old description", "old_pizza.jpg");
//        existingMenuItem.getIngredients().add(new Ingredient("Old Ingredient", 10));
//
//        MenuItemInputDTO updateMenuItemDTO = new MenuItemInputDTO();
//        updateMenuItemDTO.setName("Updated Pizza");
//        updateMenuItemDTO.setPrice(11.99);
//        updateMenuItemDTO.setDescription("Updated spicy pizza with extra cheese");
//        updateMenuItemDTO.setImage("updated_spicy_pizza.jpg");
//        updateMenuItemDTO.setIngredientIds(Arrays.asList(3L));
//
//        Ingredient updatedIngredient = new Ingredient("Basil", 5);
//
//        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(existingMenuItem));
//        when(ingredientRepository.findById(3L)).thenReturn(Optional.of(updatedIngredient));
//        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(existingMenuItem);
//
//        // Act
//        MenuItemDTO updatedMenuItemDTO = menuItemService.updateMenuItem(menuItemId, updateMenuItemDTO);
//
//        // Assert
//        assertNotNull(updatedMenuItemDTO);
//        assertEquals(updateMenuItemDTO.getName(), updatedMenuItemDTO.getName());
//        assertEquals(updateMenuItemDTO.getPrice(), updatedMenuItemDTO.getPrice());
//        assertEquals(1, updatedMenuItemDTO.getIngredients().size());
//        verify(menuItemRepository).findById(menuItemId);
//        verify(menuItemRepository).save(any(MenuItem.class));
//        verify(ingredientRepository).findById(3L);
//    }
//
//
//    @Test
//    void deleteMenuItem_DeletesMenuItem() {
//        // Arrange
//        MenuItem existingMenuItem = new MenuItem();
//        existingMenuItem.setName(pizzaDTO.getName());
//        existingMenuItem.setPrice(pizzaDTO.getPrice());
//
//        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(existingMenuItem));
//
//        // Act
//        menuItemService.deleteMenuItem(1L);
//
//        // Assert
//        verify(menuItemRepository, times(1)).findById(1L);
//        verify(menuItemRepository, times(1)).delete(existingMenuItem);
//    }
//
//    @Test
//    void createMenuItem_ReturnsCreatedMenuItem() {
//        // Arrange
//        MenuItemInputDTO newMenuItemDTO = new MenuItemInputDTO();
//        newMenuItemDTO.setName("New Pizza");
//        newMenuItemDTO.setPrice(12.99);
//        newMenuItemDTO.setDescription("Spicy pizza with extra cheese");
//        newMenuItemDTO.setImage("spicy_pizza.jpg");
//
//        MenuItem newMenuItem = new MenuItem();
//        newMenuItem.setName(newMenuItemDTO.getName());
//        newMenuItem.setPrice(newMenuItemDTO.getPrice());
//
//        when(menuItemRepository.save(any(MenuItem.class))).thenReturn(newMenuItem);
//
//        // Act
//        MenuItemDTO createdMenuItemDTO = menuItemService.createMenuItem(newMenuItemDTO);
//
//        // Assert
//        assertNotNull(createdMenuItemDTO);
//        assertEquals(newMenuItemDTO.getName(), createdMenuItemDTO.getName());
//        assertEquals(newMenuItemDTO.getPrice(), createdMenuItemDTO.getPrice());
//        verify(menuItemRepository).save(any(MenuItem.class));
//    }
//
//    @Test
//    void findByNameIgnoreCase_ReturnsListOfMenuItems() {
//        // Arrange
//        List<MenuItem> menuItems = new ArrayList<>();
//        MenuItem pizza = new MenuItem();
//        pizza.setName("Pizza");
//        pizza.setPrice(9.99);
//        menuItems.add(pizza);
//
//        when(menuItemRepository.findByNameIgnoreCase("pizza")).thenReturn(menuItems);
//
//        // Act
//        List<MenuItemDTO> result = menuItemService.findByNameIgnoreCase("pizza");
//
//        // Assert
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//        assertEquals(1, result.size());
//        assertEquals("Pizza", result.get(0).getName());
//        verify(menuItemRepository).findByNameIgnoreCase("pizza");
//    }
//
//    @Test
//    void addIngredientToMenuItem_AddsIngredientToMenuItem() throws Exception {
//        // Arrange
//        Long menuItemId = 1L;
//        Long ingredientId = 1L;
//        MenuItem pizza = new MenuItem("Pizza", 9.99, "Delicious cheese pizza", "pizza.jpg");
//        Ingredient cheese = new Ingredient("Cheese", 100);
//
//        // Reflection to set ID's
//        Field fieldPizzaId = MenuItem.class.getDeclaredField("id");
//        fieldPizzaId.setAccessible(true);
//        fieldPizzaId.set(pizza, menuItemId);
//
//        Field fieldCheeseId = Ingredient.class.getDeclaredField("id");
//        fieldCheeseId.setAccessible(true);
//        fieldCheeseId.set(cheese, ingredientId);
//
//        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(pizza));
//        when(ingredientRepository.findById(ingredientId)).thenReturn(Optional.of(cheese));
//
//        // Act
//        menuItemService.addIngredientToMenuItem(menuItemId, ingredientId);
//
//        // Assert
//        assertTrue(pizza.getIngredients().contains(cheese), "Cheese should be added to pizza ingredients");
//        verify(menuItemRepository).save(pizza);
//        verify(menuItemRepository).findById(menuItemId);
//        verify(ingredientRepository).findById(ingredientId);
//    }
//
//}
