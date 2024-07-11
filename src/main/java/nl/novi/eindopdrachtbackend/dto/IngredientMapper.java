package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.User;

/**
 * Mapper class for converting between Ingredient entities and DTOs.
 */
public class IngredientMapper {

    /**
     * Converts an Ingredient entity to a detailed IngredientDTO for Owner.
     *
     * @param ingredient the Ingredient entity
     * @return the detailed IngredientDTO
     */
    public static IngredientDTO toOwnerIngredientDTO(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        dto.setQuantity(ingredient.getQuantity());
        dto.setUnit(ingredient.getUnit());
        dto.setCost(ingredient.getCost());
        dto.setSupplier(ingredient.getSupplier());
        dto.setExpirationDate(ingredient.getExpirationDate());
        dto.setDescription(ingredient.getDescription());
        ingredient.getOwner().getId();
        return dto;
    }

    /**
     * Converts an Ingredient entity to a simplified IngredientDTO for Customer.
     *
     * @param ingredient the Ingredient entity
     * @return the simplified IngredientDTO
     */
    public static IngredientDTO toCustomerIngredientDTO(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        dto.setCost(ingredient.getCost());
        dto.setDescription(ingredient.getDescription());
        return dto;
    }

    /**
     * Converts an IngredientInputDTO to an Ingredient entity.
     *
     * @param inputDTO the IngredientInputDTO
     * @return the Ingredient entity
     */
    public static Ingredient toIngredient(IngredientInputDTO inputDTO, User owner) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(inputDTO.getName());
        ingredient.setCost(inputDTO.getCost());
        ingredient.setQuantity(inputDTO.getQuantity());
        ingredient.setUnit(inputDTO.getUnit());
        ingredient.setSupplier(inputDTO.getSupplier());
        ingredient.setExpirationDate(inputDTO.getExpirationDate());
        ingredient.setDescription(inputDTO.getDescription());
        ingredient.setOwner(owner);
        return ingredient;
    }
}
