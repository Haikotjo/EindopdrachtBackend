package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    private Ingredient ingredient;
    private User owner;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        owner = new User();
        setField(owner, "id", 1L);
        owner.setEmail("test@example.com");

        ingredient = new Ingredient();
        setField(ingredient, "id", 1L);
        ingredient.setName("Salt");
        ingredient.setQuantity(5);
        ingredient.setUnit("kg");
        ingredient.setCost(2.0);
        ingredient.setSupplier("Supplier2");
        ingredient.setExpirationDate(LocalDate.of(2025, 1, 1));
        ingredient.setDescription("Flavor enhancer");
        ingredient.setOwner(owner);
    }

    private void setField(Object targetObject, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = targetObject.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(targetObject, value);
    }

    @Test
    public void testGetAllIngredients() {
        when(ingredientRepository.findAll()).thenReturn(Collections.singletonList(ingredient));

        List<IngredientDTO> ingredients = ingredientService.getAllIngredients();

        assertNotNull(ingredients);
        assertEquals(1, ingredients.size());
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllIngredientsForOwner() {
        when(ingredientRepository.findByOwner_Id(anyLong())).thenReturn(Collections.singletonList(ingredient));

        List<IngredientDTO> ingredients = ingredientService.getAllIngredientsForOwner(owner.getId());

        assertNotNull(ingredients);
        assertEquals(1, ingredients.size());
        verify(ingredientRepository, times(1)).findByOwner_Id(owner.getId());
    }

    @Test
    public void testGetAllIngredientsForLoggedInOwner() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(owner));
        when(ingredientRepository.findByOwner_Id(anyLong())).thenReturn(Collections.singletonList(ingredient));

        List<IngredientDTO> ingredients = ingredientService.getAllIngredientsForLoggedInOwner(owner.getEmail());

        assertNotNull(ingredients);
        assertEquals(1, ingredients.size());
        verify(userRepository, times(1)).findByEmail(owner.getEmail());
        verify(ingredientRepository, times(1)).findByOwner_Id(owner.getId());
    }

    @Test
    public void testGetIngredientByIdForAdmin() {
        when(ingredientRepository.findByIdAndOwner_Id(anyLong(), anyLong())).thenReturn(Optional.of(ingredient));

        IngredientDTO ingredientDTO = ingredientService.getIngredientByIdForAdmin(ingredient.getId(), owner.getId());

        assertNotNull(ingredientDTO);
        assertEquals("Salt", ingredientDTO.getName());
        verify(ingredientRepository, times(1)).findByIdAndOwner_Id(ingredient.getId(), owner.getId());
    }

    @Test
    public void testGetIngredientByIdForOwner() {
        when(ingredientRepository.findByIdAndOwner_Id(anyLong(), anyLong())).thenReturn(Optional.of(ingredient));

        IngredientDTO ingredientDTO = ingredientService.getIngredientByIdForOwner(ingredient.getId(), owner.getId());

        assertNotNull(ingredientDTO);
        assertEquals("Salt", ingredientDTO.getName());
        verify(ingredientRepository, times(1)).findByIdAndOwner_Id(ingredient.getId(), owner.getId());
    }

    @Test
    public void testCreateIngredientForOwner() {
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        IngredientInputDTO inputDTO = new IngredientInputDTO();
        inputDTO.setName("Salt");
        inputDTO.setQuantity(5);
        inputDTO.setUnit("kg");
        inputDTO.setCost(2.0);
        inputDTO.setSupplier("Supplier2");
        inputDTO.setExpirationDate(LocalDate.of(2025, 1, 1));
        inputDTO.setDescription("Flavor enhancer");
        inputDTO.setOwnerId(owner.getId());

        IngredientDTO ingredientDTO = ingredientService.createIngredientForOwner(inputDTO, owner);

        assertNotNull(ingredientDTO);
        assertEquals("Salt", ingredientDTO.getName());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    public void testCreateIngredientForAdmin() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        IngredientInputDTO inputDTO = new IngredientInputDTO();
        inputDTO.setName("Salt");
        inputDTO.setQuantity(5);
        inputDTO.setUnit("kg");
        inputDTO.setCost(2.0);
        inputDTO.setSupplier("Supplier2");
        inputDTO.setExpirationDate(LocalDate.of(2025, 1, 1));
        inputDTO.setDescription("Flavor enhancer");
        inputDTO.setOwnerId(owner.getId());

        IngredientDTO ingredientDTO = ingredientService.createIngredientForAdmin(inputDTO, owner.getId());

        assertNotNull(ingredientDTO);
        assertEquals("Salt", ingredientDTO.getName());
        verify(userRepository, times(1)).findById(owner.getId());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    public void testUpdateIngredientForOwner() {
        when(ingredientRepository.findByIdAndOwner_Id(anyLong(), anyLong())).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        IngredientInputDTO inputDTO = new IngredientInputDTO();
        inputDTO.setName("Updated Salt");
        inputDTO.setQuantity(10);
        inputDTO.setUnit("kg");
        inputDTO.setCost(3.0);
        inputDTO.setSupplier("Updated Supplier");
        inputDTO.setExpirationDate(LocalDate.of(2026, 1, 1));
        inputDTO.setDescription("Updated Description");
        inputDTO.setOwnerId(owner.getId());

        IngredientDTO ingredientDTO = ingredientService.updateIngredientForOwner(ingredient.getId(), inputDTO, owner.getId());

        assertNotNull(ingredientDTO);
        assertEquals("Updated Salt", ingredientDTO.getName());
        verify(ingredientRepository, times(1)).findByIdAndOwner_Id(ingredient.getId(), owner.getId());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    public void testUpdateIngredientForAdmin() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);

        IngredientInputDTO inputDTO = new IngredientInputDTO();
        inputDTO.setName("Updated Salt");
        inputDTO.setQuantity(10);
        inputDTO.setUnit("kg");
        inputDTO.setCost(3.0);
        inputDTO.setSupplier("Updated Supplier");
        inputDTO.setExpirationDate(LocalDate.of(2026, 1, 1));
        inputDTO.setDescription("Updated Description");
        inputDTO.setOwnerId(owner.getId());

        IngredientDTO ingredientDTO = ingredientService.updateIngredientForAdmin(ingredient.getId(), inputDTO, owner.getId());

        assertNotNull(ingredientDTO);
        assertEquals("Updated Salt", ingredientDTO.getName());
        verify(userRepository, times(1)).findById(owner.getId());
        verify(ingredientRepository, times(1)).findById(ingredient.getId());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    public void testDeleteIngredientForOwner() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));

        ingredientService.deleteIngredientForOwner(ingredient.getId(), owner);

        verify(ingredientRepository, times(1)).findById(ingredient.getId());
        verify(ingredientRepository, times(1)).delete(ingredient);
    }

    @Test
    public void testDeleteIngredientForAdmin() {
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));

        ingredientService.deleteIngredientForAdmin(ingredient.getId());

        verify(ingredientRepository, times(1)).findById(ingredient.getId());
        verify(ingredientRepository, times(1)).delete(ingredient);
    }
}
