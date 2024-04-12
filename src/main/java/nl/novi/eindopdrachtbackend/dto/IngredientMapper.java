package nl.novi.eindopdrachtbackend.dto;

        import nl.novi.eindopdrachtbackend.model.Ingredient;

public class IngredientMapper {

    public static Ingredient toEntity(IngredientInputDTO dto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(dto.getName());
        ingredient.setQuantity(dto.getQuantity());
        return ingredient;
    }

    public static IngredientDTO toDTO(Ingredient ingredient) {
        return new IngredientDTO(ingredient);
    }
}
