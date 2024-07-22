package nl.novi.eindopdrachtbackend.repository;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IngredientRepositoryTest {

    @Mock
    private IngredientRepository ingredientRepository;

    private Ingredient ingredient;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        ingredient = new Ingredient();
        ingredient.setName("Salt");
        ingredient.setQuantity(5);
        ingredient.setExpirationDate(LocalDate.of(2025, 1, 1));

        // Use reflection to set the id
        Field idField = Ingredient.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(ingredient, 1L);
    }

    @Test
    public void testFindLowStockIngredients() {
        when(ingredientRepository.findLowStockIngredients(10)).thenReturn(Arrays.asList(ingredient));
        List<Ingredient> lowStockIngredients = ingredientRepository.findLowStockIngredients(10);
        assertNotNull(lowStockIngredients);
        assertEquals(1, lowStockIngredients.size());
        assertEquals("Salt", lowStockIngredients.get(0).getName());
    }

    @Test
    public void testFindExpiringIngredients() {
        LocalDate date = LocalDate.parse("2025-01-01");
        when(ingredientRepository.findExpiringIngredients(date)).thenReturn(Arrays.asList(ingredient));
        List<Ingredient> expiringIngredients = ingredientRepository.findExpiringIngredients(date);
        assertNotNull(expiringIngredients);
        assertEquals(1, expiringIngredients.size());
        assertEquals("Salt", expiringIngredients.get(0).getName());
    }

    @Test
    public void testFindByOwner_Id() {
        Long ownerId = 1L;
        when(ingredientRepository.findByOwner_Id(ownerId)).thenReturn(Arrays.asList(ingredient));
        List<Ingredient> ingredients = ingredientRepository.findByOwner_Id(ownerId);
        assertNotNull(ingredients);
        assertEquals(1, ingredients.size());
        assertEquals("Salt", ingredients.get(0).getName());
    }

    @Test
    public void testFindByIdAndOwner_Id() {
        Long ownerId = 1L;
        when(ingredientRepository.findByIdAndOwner_Id(1L, ownerId)).thenReturn(Optional.of(ingredient));
        Optional<Ingredient> foundIngredient = ingredientRepository.findByIdAndOwner_Id(1L, ownerId);
        assertTrue(foundIngredient.isPresent());
        assertEquals("Salt", foundIngredient.get().getName());
    }
}
