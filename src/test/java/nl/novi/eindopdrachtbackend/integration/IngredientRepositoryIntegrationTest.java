package nl.novi.eindopdrachtbackend.integration;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class IngredientRepositoryIntegrationTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    private Ingredient ingredient;
    private User owner;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        owner = new User();
        setField(owner, "id", 1L);  // Zet een ID voor de eigenaar

        ingredient = new Ingredient();
        ingredient.setName("Salt");
        ingredient.setQuantity(5);
        ingredient.setUnit("kg");
        ingredient.setCost(2.0);
        ingredient.setSupplier("Supplier2");
        ingredient.setExpirationDate(LocalDate.of(2025, 1, 1));  // Gebruik LocalDate
        ingredient.setDescription("Flavor enhancer");
        ingredient.setOwner(owner);

        // Gebruik reflectie om de id in te stellen voordat we opslaan
        setField(ingredient, "id", 1L);

        ingredientRepository.save(ingredient);
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    public void testFindLowStockIngredients() {
        List<Ingredient> lowStockIngredients = ingredientRepository.findLowStockIngredients(10);
        assertNotNull(lowStockIngredients);
        assertEquals(1, lowStockIngredients.size());
        assertEquals("Salt", lowStockIngredients.get(0).getName());
    }

    @Test
    public void testFindExpiringIngredients() {
        // Parse the expiration date to LocalDate
        LocalDate expirationWarningDate = LocalDate.of(2025, 1, 1);

        // Call the repository method with the correct type
        List<Ingredient> expiringIngredients = ingredientRepository.findExpiringIngredients(expirationWarningDate);

        // Perform assertions
        assertNotNull(expiringIngredients);
        assertEquals(6, expiringIngredients.size());
        assertEquals("Salt", expiringIngredients.get(0).getName());
    }

    @Test
    public void testFindByOwner_Id() {
        List<Ingredient> ingredients = ingredientRepository.findByOwner_Id(owner.getId());
        assertNotNull(ingredients);
        assertEquals(1, ingredients.size());
        assertEquals("Salt", ingredients.get(0).getName());
    }

    @Test
    public void testFindByIdAndOwner_Id() {
        Optional<Ingredient> foundIngredient = ingredientRepository.findByIdAndOwner_Id(ingredient.getId(), owner.getId());
        assertTrue(foundIngredient.isPresent());
        assertEquals("Salt", foundIngredient.get().getName());
    }
}
