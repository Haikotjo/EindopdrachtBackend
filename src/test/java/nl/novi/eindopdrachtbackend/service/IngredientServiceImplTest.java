package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    private Ingredient tomato;
    private Ingredient lettuce;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tomato = new Ingredient();
        tomato.setName("Tomato");
        tomato.setQuantity(10);

        lettuce = new Ingredient();
        lettuce.setName("Lettuce");
        lettuce.setQuantity(20);
    }

    @Test
    void testGetAllIngredients() {
        // Arrange
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(tomato);
        ingredients.add(lettuce);
        when(ingredientRepository.findAll()).thenReturn(ingredients);

        // Act
        List<IngredientDTO> ingredientDTOList = ingredientService.getAllIngredients();

        // Assert
        assertNotNull(ingredientDTOList);
        assertEquals(2, ingredientDTOList.size());
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    void testGetIngredientById() {
        // Arrange
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(tomato));

        // Act
        IngredientDTO ingredientDTO = ingredientService.getIngredientById(1L);

        // Assert
        assertNotNull(ingredientDTO);
        assertEquals("Tomato", ingredientDTO.getName());
        assertEquals(10, ingredientDTO.getQuantity());
        verify(ingredientRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateIngredient() {
        // Arrange
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(tomato));
        IngredientInputDTO updatedTomatoDto = new IngredientInputDTO();
        updatedTomatoDto.setName("Updated Tomato");
        updatedTomatoDto.setQuantity(15);

        // Act
        Ingredient updatedTomato = ingredientService.updateIngredient(1L, updatedTomatoDto);

        // Assert
        assertNotNull(updatedTomato);
        assertEquals("Updated Tomato", updatedTomato.getName());
        assertEquals(15, updatedTomato.getQuantity());
        verify(ingredientRepository, times(1)).findById(1L);
        verify(ingredientRepository, times(1)).save(any());
    }

    @Test
    void testDeleteIngredient() {
        // Arrange
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(tomato));

        // Act
        ingredientService.deleteIngredient(1L);

        // Assert
        verify(ingredientRepository, times(1)).findById(1L);
        verify(ingredientRepository, times(1)).delete(any());
    }


}
