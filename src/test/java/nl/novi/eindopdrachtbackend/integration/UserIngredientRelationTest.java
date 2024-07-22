package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserIngredientRelationTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setName("Jane Doe");
        user.setEmail("jane.doe" + System.currentTimeMillis() + "@example.com");
        user.setPassword("securepassword");
        user = userRepository.save(user);
    }

    @Test
    @Transactional
    public void testUserIngredientRelation() {
        // Maak een Ingredient aan
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Tomato");
        ingredient.setQuantity(100);
        ingredient.setUnit("kg");
        ingredient.setCost(10.0);
        ingredient.setSupplier("SupplierY");
        ingredient.setExpirationDate(LocalDate.of(2024, 12, 31));
        ingredient.setDescription("Fresh tomatoes");
        ingredient.setOwner(user);
        ingredient = ingredientRepository.save(ingredient);

        // Controleer de relatie
        List<Ingredient> ingredients = ingredientRepository.findByOwner_Id(user.getId());
        assertTrue(ingredients.contains(ingredient));
        assertEquals(1, ingredients.size());
        assertEquals("Tomato", ingredients.get(0).getName());
    }
}
