package nl.novi.eindopdrachtbackend.dto;

        import nl.novi.eindopdrachtbackend.model.Ingredient;

public class IngredientMapper {
    public static IngredientDTO toIngredientDTO(Ingredient ingredient) {
        IngredientDTO dto = new IngredientDTO();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        dto.setQuantity(ingredient.getQuantity());
        return dto;
    }

    public static Ingredient toIngredient(IngredientInputDTO inputDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(inputDTO.getName());
        ingredient.setQuantity(inputDTO.getQuantity());
        return ingredient;
    }
}

