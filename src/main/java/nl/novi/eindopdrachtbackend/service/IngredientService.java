package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;

import java.util.List;

public interface IngredientService {

    IngredientDTO createIngredient(IngredientInputDTO ingredientInputDTO);

    IngredientDTO updateIngredient(Long id, IngredientInputDTO ingredientInputDTO);

    List<IngredientDTO> getAllIngredients();

    IngredientDTO getIngredientById(Long id);

    void deleteIngredient(Long id);

    List<IngredientDTO> findByNameIgnoreCase(String name);
}