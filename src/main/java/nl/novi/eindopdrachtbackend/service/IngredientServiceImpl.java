package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<IngredientDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream()
                .map(IngredientMapper::toIngredientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDTO getIngredientById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id));
        return IngredientMapper.toIngredientDTO(ingredient);
    }



    public Ingredient updateIngredient(Long id, IngredientInputDTO ingredientInputDTO) {
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id));
        existingIngredient.setName(ingredientInputDTO.getName());
        existingIngredient.setQuantity(ingredientInputDTO.getQuantity());
        return ingredientRepository.save(existingIngredient);
    }


    @Override
    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id));
        ingredientRepository.delete(ingredient);
    }

    @Override
    public Ingredient createIngredient(IngredientInputDTO ingredientInputDTO) {
        Ingredient ingredient = IngredientMapper.toIngredient(ingredientInputDTO);
        return ingredientRepository.save(ingredient);
    }

    @Override
    public List<IngredientDTO> findByNameIgnoreCase(String name) {
        List<Ingredient> ingredients = ingredientRepository.findByNameIgnoreCase(name);
        if (ingredients.isEmpty()) {
            throw new ResourceNotFoundException("Ingredient not found with name: " + name);
        }
        return ingredients.stream()
                .map(IngredientMapper::toIngredientDTO)
                .collect(Collectors.toList());
    }
}
