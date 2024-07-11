package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;

import java.util.List;

/**
 * Service interface for managing ingredients.
 */
public interface IngredientService {
    List<IngredientDTO> getAllIngredients();
    List<IngredientDTO> getAllIngredientsForOwner(Long ownerId);
    List<IngredientDTO> getAllIngredientsForLoggedInOwner(String email);
    IngredientDTO getIngredientByIdForOwner(Long id, Long ownerId);
    IngredientDTO getIngredientByIdForAdmin(Long id, Long ownerId);
//    IngredientDTO createIngredient(IngredientInputDTO ingredientInputDTO);
//    IngredientDTO updateIngredient(Long id, IngredientInputDTO ingredientInputDTO);

    void deleteIngredient(Long id);
}
