package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.model.Ingredient;

import java.util.List;

public interface IngredientService {

    Ingredient createIngredient(IngredientInputDTO ingredientInputDTO);

    Ingredient updateIngredient(Long id, IngredientInputDTO ingredientInputDTO);
    List<IngredientDTO> getAllIngredients();
    IngredientDTO getIngredientById(Long id);
    void deleteIngredient(Long id);
    List<IngredientDTO> findByNameIgnoreCase(String name);
}
