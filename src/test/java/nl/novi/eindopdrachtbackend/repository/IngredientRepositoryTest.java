package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class IngredientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IngredientRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        Ingredient tomato = new Ingredient("Tomato", 10);
        Ingredient lettuce = new Ingredient("Lettuce", 20);
        entityManager.persist(tomato);
        entityManager.persist(lettuce);
        entityManager.flush();
    }

    @Test
    void whenFindByNameIgnoreCase_thenReturnIngredient() {
        List<Ingredient> foundIngredients = ingredientRepository.findByNameIgnoreCase("tomato");
        assertEquals(1, foundIngredients.size());
        assertTrue(foundIngredients.stream().anyMatch(ingredient -> "Tomato".equals(ingredient.getName())));
    }

    @Test
    void whenFindByNameIgnoreCaseWithDifferentCase_thenReturnIngredient() {
        List<Ingredient> foundIngredients = ingredientRepository.findByNameIgnoreCase("TOMATO");
        assertEquals(1, foundIngredients.size());
        assertTrue(foundIngredients.stream().anyMatch(ingredient -> "Tomato".equals(ingredient.getName())));
    }
}
