package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    private Ingredient tomato;
    private Ingredient lettuce;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tomato = new Ingredient("Tomato", 10);
        lettuce = new Ingredient("Lettuce", 20);
    }

    @Test
    void getAllIngredients_ReturnsListOfIngredients() {
        // Preparation
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(tomato, lettuce));

        // Action
        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        // Verification
        assertNotNull(ingredients);
        assertEquals(2, ingredients.size());
        verify(ingredientRepository).findAll();
    }

    @Test
    void getIngredientById_ReturnsIngredient() {
        // Preparation
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(tomato));

        // Action
        Ingredient foundIngredient = ingredientService.getIngredientById(1L);

        // Verification
        assertNotNull(foundIngredient);
        assertEquals("Tomato", foundIngredient.getName());
        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void getIngredientById_ThrowsResourceNotFoundException() {
        // Preparation
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action
        assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.getIngredientById(1L);
        });

        // Verification
        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void createIngredient_ReturnsCreatedIngredient() {
        // Preparation
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(tomato);

        // Action
        Ingredient createdIngredient = ingredientService.createIngredient(tomato);

        // Verification
        assertNotNull(createdIngredient);
        assertEquals("Tomato", createdIngredient.getName());
        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    void updateIngredient_ReturnsUpdatedIngredient() {
        // Preparation
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(tomato));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(tomato);

        // Action
        tomato.setQuantity(15);
        Ingredient updatedIngredient = ingredientService.updateIngredient(1L, tomato);

        // Verification
        assertNotNull(updatedIngredient);
        assertEquals(15, updatedIngredient.getQuantity());
        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    void updateIngredient_ThrowsResourceNotFoundException() {
        // Preparation
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.updateIngredient(1L, new Ingredient("UpdatedName", 20));
        });

        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void deleteIngredient_DeletesIngredient() {
        // Preparation
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(tomato));

        // Action
        ingredientService.deleteIngredient(1L);

        // Verification
        verify(ingredientRepository).delete(any(Ingredient.class));
    }

    @Test
    void deleteIngredient_ThrowsResourceNotFoundException() {
        // Preparation
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.deleteIngredient(1L);
        });

        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void getIngredientByName_ReturnsListOfIngredients() {
        // Preparation
        when(ingredientRepository.findByNameIgnoreCase("tomato")).thenReturn(Arrays.asList(tomato));

        // Action
        List<Ingredient> ingredients = ingredientService.findByNameIgnoreCase("tomato");

        // Verification
        assertNotNull(ingredients);
        assertFalse(ingredients.isEmpty());
        assertEquals("Tomato", ingredients.get(0).getName());
        verify(ingredientRepository).findByNameIgnoreCase(anyString());
    }

    @Test
    void getIngredientByName_ThrowsResourceNotFoundExceptionWhenNotFound() {
        // Preparation
        when(ingredientRepository.findByNameIgnoreCase("nonexistent")).thenReturn(Collections.emptyList());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.findByNameIgnoreCase("nonexistent");
        });

        verify(ingredientRepository).findByNameIgnoreCase("nonexistent");
    }

}
