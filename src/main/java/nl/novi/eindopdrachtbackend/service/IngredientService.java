package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.model.User;

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
    IngredientDTO createIngredientForOwner(IngredientInputDTO ingredientInputDTO, User owner);
    IngredientDTO createIngredientForAdmin(IngredientInputDTO ingredientInputDTO, Long ownerId);
    IngredientDTO updateIngredientForOwner(Long id, IngredientInputDTO ingredientInputDTO, Long ownerId);
    IngredientDTO updateIngredientForAdmin(Long id, IngredientInputDTO ingredientInputDTO, Long ownerId);
    void deleteIngredientForOwner(Long id, User owner);
    void deleteIngredientForAdmin(Long id);
}
