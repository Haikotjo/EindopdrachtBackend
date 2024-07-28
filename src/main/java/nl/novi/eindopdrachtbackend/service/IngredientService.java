package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.model.User;

import java.util.List;

/**
 * Service interface for managing ingredients.
 */
public interface IngredientService {

    /**
     * Get all ingredients (Admin only).
     *
     * @return list of all IngredientDTOs
     */
    List<IngredientDTO> getAllIngredients();

    /**
     * Get all ingredients for a specific owner (Admin only).
     *
     * @param ownerId owner ID
     * @return list of IngredientDTOs for the specified owner
     */
    List<IngredientDTO> getAllIngredientsForOwner(Long ownerId);

    /**
     * Get all ingredients for the logged-in owner.
     *
     * @param email email of the logged-in owner
     * @return list of IngredientDTOs for the logged-in owner
     */
    List<IngredientDTO> getAllIngredientsForLoggedInOwner(String email);

    /**
     * Get ingredient by ID for Admin.
     *
     * @param id ingredient ID
     * @param ownerId owner ID
     * @return IngredientDTO for the specified ID and owner
     */
    IngredientDTO getIngredientByIdForAdmin(Long id, Long ownerId);

    /**
     * Create a new ingredient for the logged-in owner.
     *
     * @param ingredientInputDTO ingredient input data
     * @param owner owner entity
     * @return created IngredientDTO
     */
    IngredientDTO createIngredientForOwner(IngredientInputDTO ingredientInputDTO, User owner);

    /**
     * Create a new ingredient for a specific owner (Admin only).
     *
     * @param ingredientInputDTO ingredient input data
     * @param ownerId owner ID
     * @return created IngredientDTO
     */
    IngredientDTO createIngredientForAdmin(IngredientInputDTO ingredientInputDTO, Long ownerId);

    /**
     * Update an ingredient for the logged-in owner.
     *
     * @param id ingredient ID
     * @param ingredientInputDTO ingredient input data
     * @param ownerId owner ID
     * @return updated IngredientDTO
     */
    IngredientDTO updateIngredientForOwner(Long id, IngredientInputDTO ingredientInputDTO, Long ownerId);

    /**
     * Update an ingredient for a specific owner (Admin only).
     *
     * @param id ingredient ID
     * @param ingredientInputDTO ingredient input data
     * @param ownerId owner ID
     * @return updated IngredientDTO
     */
    IngredientDTO updateIngredientForAdmin(Long id, IngredientInputDTO ingredientInputDTO, Long ownerId);

    /**
     * Delete an ingredient for the logged-in owner.
     *
     * @param id ingredient ID
     * @param owner owner entity
     */
    void deleteIngredientForOwner(Long id, User owner);

    /**
     * Delete an ingredient for admin.
     *
     * @param id ingredient ID
     */
    void deleteIngredientForAdmin(Long id);
}
