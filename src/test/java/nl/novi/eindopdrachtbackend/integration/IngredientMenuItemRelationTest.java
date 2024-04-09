package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class IngredientMenuItemRelationTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Test
    @Transactional
    public void testIngredientMenuItemRelation() {
        // Make Ingredient
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Kaas");
        ingredient.setQuantity(50);
        ingredient = ingredientRepository.save(ingredient);

        // Make MenuItem
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Pizza Margherita");
        menuItem.setPrice(9.99);
        menuItem.setDescription("Classic pizza");
        menuItem.setImage("image.jpg");

        // Insert Ingredient to MenuItem
        menuItem.setIngredients(Set.of(ingredient));
        menuItem = menuItemRepository.save(menuItem);

        // Get saved MenuItem and verify relation
        MenuItem savedMenuItem = menuItemRepository.findById(menuItem.getId()).get();
        assertTrue(savedMenuItem.getIngredients().contains(ingredient));
    }
}

