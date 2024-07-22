//package nl.novi.eindopdrachtbackend.service;
//
//import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
//import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
//import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
//import nl.novi.eindopdrachtbackend.model.Ingredient;
//import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class IngredientServiceImplTest {
//
//    @Mock
//    private IngredientRepository ingredientRepository;
//
//    @InjectMocks
//    private IngredientServiceImpl ingredientService;
//
//    private IngredientDTO tomatoDTO;
//    private IngredientInputDTO updateTomatoDTO;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        tomatoDTO = new IngredientDTO();
//        tomatoDTO.setId(1L);
//        tomatoDTO.setName("Tomato");
//        tomatoDTO.setQuantity(10);
//
//        updateTomatoDTO = new IngredientInputDTO();
//        updateTomatoDTO.setName("Updated Tomato");
//        updateTomatoDTO.setQuantity(15);
//    }
//
//    @Test
//    void testGetAllIngredients() {
//        // Arrange
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName(tomatoDTO.getName());
//        ingredient.setQuantity(tomatoDTO.getQuantity());
//        List<Ingredient> ingredients = new ArrayList<>();
//        ingredients.add(ingredient);
//
//        when(ingredientRepository.findAll()).thenReturn(ingredients);
//
//        // Act
//        List<IngredientDTO> ingredientDTOList = ingredientService.getAllIngredients();
//
//        // Assert
//        assertNotNull(ingredientDTOList);
//        assertEquals(1, ingredientDTOList.size());
//        assertEquals(tomatoDTO.getName(), ingredientDTOList.get(0).getName());
//        verify(ingredientRepository, times(1)).findAll();
//    }
//
//    @Test
//    void testGetIngredientById() {
//        // Arrange
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName(tomatoDTO.getName());
//        ingredient.setQuantity(tomatoDTO.getQuantity());
//
//        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient));
//
//        // Act
//        IngredientDTO result = ingredientService.getIngredientById(1L);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(tomatoDTO.getName(), result.getName());
//        assertEquals(tomatoDTO.getQuantity(), result.getQuantity());
//        verify(ingredientRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testUpdateIngredient() {
//        // Arrange
//        Ingredient existingIngredient = new Ingredient();
//        existingIngredient.setName(tomatoDTO.getName());
//        existingIngredient.setQuantity(tomatoDTO.getQuantity());
//
//        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(existingIngredient));
//        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(existingIngredient);
//
//        // Act
//        IngredientDTO updated = ingredientService.updateIngredient(1L, updateTomatoDTO);
//
//        // Assert
//        assertNotNull(updated);
//        assertEquals(updateTomatoDTO.getName(), updated.getName());
//        assertEquals(updateTomatoDTO.getQuantity(), updated.getQuantity());
//        verify(ingredientRepository, times(1)).findById(1L);
//        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
//    }
//
//    @Test
//    void testDeleteIngredient() {
//        // Arrange
//        Ingredient existingIngredient = new Ingredient();
//        existingIngredient.setName(tomatoDTO.getName());
//        existingIngredient.setQuantity(tomatoDTO.getQuantity());
//
//        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(existingIngredient));
//
//        // Act
//        ingredientService.deleteIngredient(1L);
//
//        // Assert
//        verify(ingredientRepository, times(1)).findById(1L);
//        verify(ingredientRepository, times(1)).delete(existingIngredient);
//    }
//}
