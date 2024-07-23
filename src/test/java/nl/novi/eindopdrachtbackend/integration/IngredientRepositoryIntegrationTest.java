package nl.novi.eindopdrachtbackend.integration;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class IngredientRepositoryIntegrationTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;

    private Ingredient ingredient;
    private User owner;

    @BeforeEach
    public void setup() {
        // We gebruiken een bestaande eigenaar uit data.sql
        owner = userRepository.findByEmail("owner.one@example.com").orElseThrow(() -> new RuntimeException("Owner not found"));

        ingredient = new Ingredient();
        ingredient.setName("New Ingredient");
        ingredient.setQuantity(5);
        ingredient.setUnit("kg");
        ingredient.setCost(2.0);
        ingredient.setSupplier("Supplier2");
        ingredient.setExpirationDate(LocalDate.of(2025, 1, 1));
        ingredient.setDescription("New ingredient description");
        ingredient.setOwner(owner);

        ingredientRepository.save(ingredient);
    }

    @Test
    public void testFindLowStockIngredients() {
        List<Ingredient> lowStockIngredients = ingredientRepository.findLowStockIngredients(10);
        assertNotNull(lowStockIngredients);
        assertFalse(lowStockIngredients.isEmpty());
    }

    @Test
    public void testFindExpiringIngredients() {
        LocalDate expirationWarningDate = LocalDate.of(2025, 1, 1);
        List<Ingredient> expiringIngredients = ingredientRepository.findExpiringIngredients(expirationWarningDate);
        assertNotNull(expiringIngredients);
        assertFalse(expiringIngredients.isEmpty());
    }

    @Test
    public void testFindByOwner_Id() {
        List<Ingredient> ingredients = ingredientRepository.findByOwner_Id(owner.getId());
        assertNotNull(ingredients);
        assertFalse(ingredients.isEmpty());
    }

    @Test
    public void testFindByIdAndOwner_Id() {
        Optional<Ingredient> foundIngredient = ingredientRepository.findByIdAndOwner_Id(ingredient.getId(), owner.getId());
        assertTrue(foundIngredient.isPresent());
        assertEquals("New Ingredient", foundIngredient.get().getName());
    }
}
