//package nl.novi.eindopdrachtbackend.integration;
//
//import jakarta.transaction.Transactional;
//import nl.novi.eindopdrachtbackend.model.Ingredient;
//import nl.novi.eindopdrachtbackend.model.MenuItem;
//import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
//import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//class MenuItemIngredientRelationTest {
//
//    @Autowired
//    private IngredientRepository ingredientRepository;
//
//    @Autowired
//    private MenuItemRepository menuItemRepository;
//
//    @Test
//    @Transactional
//    public void testMenuItemLinksBackToIngredientCorrectly() {
//        // Setup MenuItem
//        MenuItem menuItem = new MenuItem("Pizza Margherita", 9.99, "Classic pizza", "image.jpg");
//        menuItem = menuItemRepository.save(menuItem);
//
//        // Create Ingredient and link to MenuItem
//        Ingredient ingredient = new Ingredient("Cheese", 50);
//        menuItem.addIngredient(ingredient);
//        ingredient.getMenuItems().add(menuItem);
//
//        // Save de entities
//        ingredient = ingredientRepository.save(ingredient);
//        menuItem = menuItemRepository.save(menuItem);
//
//        // Refresh de entities
//        ingredientRepository.flush();
//        menuItemRepository.flush();
//
//        MenuItem savedMenuItem = menuItemRepository.findById(menuItem.getId()).orElseThrow();
//        Ingredient savedIngredient = ingredientRepository.findById(ingredient.getId()).orElseThrow();
//
//        // Verify relation
//        assertTrue(savedMenuItem.getIngredients().contains(savedIngredient), "MenuItem does not contain the expected ingredient");
//        assertTrue(savedIngredient.getMenuItems().contains(savedMenuItem), "Ingredient does not link back to the MenuItem correctly");
//    }
//}
