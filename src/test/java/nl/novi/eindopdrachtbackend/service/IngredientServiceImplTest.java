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
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(tomato, lettuce));

        List<Ingredient> ingredients = ingredientService.getAllIngredients();

        assertNotNull(ingredients);
        assertEquals(2, ingredients.size());
        verify(ingredientRepository).findAll();
    }

    @Test
    void getIngredientById_ReturnsIngredient() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(tomato));

        Ingredient foundIngredient = ingredientService.getIngredientById(1L);

        assertNotNull(foundIngredient);
        assertEquals("Tomato", foundIngredient.getName());
        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void getIngredientById_ThrowsResourceNotFoundException() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.getIngredientById(1L);
        });

        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void createIngredient_ReturnsCreatedIngredient() {
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(tomato);

        Ingredient createdIngredient = ingredientService.createIngredient(tomato);

        assertNotNull(createdIngredient);
        assertEquals("Tomato", createdIngredient.getName());
        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    void updateIngredient_ReturnsUpdatedIngredient() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(tomato));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(tomato);

        tomato.setQuantity(15);
        Ingredient updatedIngredient = ingredientService.updateIngredient(1L, tomato);

        assertNotNull(updatedIngredient);
        assertEquals(15, updatedIngredient.getQuantity());
        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    void updateIngredient_ThrowsResourceNotFoundException() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.updateIngredient(1L, new Ingredient("UpdatedName", 20));
        });

        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void deleteIngredient_DeletesIngredient() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(tomato));

        ingredientService.deleteIngredient(1L);

        verify(ingredientRepository).delete(any(Ingredient.class));
    }

    @Test
    void deleteIngredient_ThrowsResourceNotFoundException() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.deleteIngredient(1L);
        });

        verify(ingredientRepository).findById(anyLong());
    }

    @Test
    void getIngredientByName_ReturnsListOfIngredients() {
        when(ingredientRepository.findByNameIgnoreCase("tomato")).thenReturn(Arrays.asList(tomato));

        List<Ingredient> ingredients = ingredientService.findByNameIgnoreCase("tomato");

        assertNotNull(ingredients);
        assertFalse(ingredients.isEmpty());
        assertEquals("Tomato", ingredients.get(0).getName());
        verify(ingredientRepository).findByNameIgnoreCase(anyString());
    }

    @Test
    void getIngredientByName_ThrowsResourceNotFoundExceptionWhenNotFound() {
        when(ingredientRepository.findByNameIgnoreCase("nonexistent")).thenReturn(Collections.emptyList());

        assertThrows(ResourceNotFoundException.class, () -> {
            ingredientService.findByNameIgnoreCase("nonexistent");
        });

        verify(ingredientRepository).findByNameIgnoreCase("nonexistent");
    }

}
