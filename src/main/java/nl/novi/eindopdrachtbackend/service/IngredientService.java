package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.model.Ingredient;

import java.util.List;

public interface IngredientService {
    Ingredient createIngredient(Ingredient ingredient);
    Ingredient updateIngredient(Long id, Ingredient ingredientDetails);
    List<Ingredient> getAllIngredients();
    Ingredient getIngredientById(Long id);
    void deleteIngredient(Long id);
    List<Ingredient> findByNameIgnoreCase(String name);
}
