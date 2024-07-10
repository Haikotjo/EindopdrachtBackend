package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<IngredientDTO> getAllIngredients() {
        try {
            List<Ingredient> ingredients = ingredientRepository.findAll();
            if (ingredients.isEmpty()) {
                throw new ResourceNotFoundException("No ingredients found");
            }
            return ingredients.stream()
                    .map(IngredientMapper::toOwnerIngredientDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Log de fout voor debugdoeleinden
            throw new RuntimeException("Failed to retrieve ingredients", e);
        }
    }

    @Override
    public List<IngredientDTO> getAllIngredientsForOwner(Long ownerId) {
        try {
            List<Ingredient> ingredients = ingredientRepository.findByMenuItems_Menus_Restaurant_Owner_Id(ownerId);
            if (ingredients.isEmpty()) {
                throw new ResourceNotFoundException("No ingredients found for owner with ID " + ownerId);
            }
            return ingredients.stream()
                    .map(IngredientMapper::toOwnerIngredientDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Log de fout voor debugdoeleinden
            throw new RuntimeException("Failed to retrieve ingredients for owner with ID " + ownerId, e);
        }
    }

    @Override
    public List<IngredientDTO> getAllIngredientsForLoggedInOwner(String email) {
        try {
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
            return getAllIngredientsForOwner(currentUser.getId());
        } catch (ResourceNotFoundException e) {
            // Log de fout voor debugdoeleinden
            throw e;  // Gooi de exception opnieuw om deze door de controller te laten afhandelen
        } catch (Exception e) {
            // Log de fout voor debugdoeleinden
            throw new RuntimeException("Failed to retrieve ingredients for user with email " + email, e);
        }
    }

    @Override
    public IngredientDTO getIngredientByIdForAdmin(Long id, Long ownerId) {
        try {
            Ingredient ingredient = ingredientRepository.findByIdAndMenuItems_Menus_Restaurant_Owner_Id(id, ownerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id + " and owner id :: " + ownerId));
            return IngredientMapper.toOwnerIngredientDTO(ingredient);
        } catch (ResourceNotFoundException e) {
            // Log de fout voor debugdoeleinden
            throw e;  // Gooi de exception opnieuw om deze door de controller te laten afhandelen
        } catch (Exception e) {
            // Log de fout voor debugdoeleinden
            throw new RuntimeException("Failed to retrieve ingredient with id " + id + " for owner with id " + ownerId, e);
        }
    }

    @Override
    public IngredientDTO getIngredientByIdForOwner(Long id, Long ownerId) {
        try {
            Ingredient ingredient = ingredientRepository.findByIdAndMenuItems_Menus_Restaurant_Owner_Id(id, ownerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id + " and owner id :: " + ownerId));
            return IngredientMapper.toOwnerIngredientDTO(ingredient);
        } catch (ResourceNotFoundException e) {
            // Log de fout voor debugdoeleinden
            throw e;  // Gooi de exception opnieuw om deze door de controller te laten afhandelen
        } catch (Exception e) {
            // Log de fout voor debugdoeleinden
            throw new RuntimeException("Failed to retrieve ingredient with id " + id + " for owner with id " + ownerId, e);
        }
    }

    @Override
    public IngredientDTO createIngredient(IngredientInputDTO ingredientInputDTO) {
        Ingredient ingredient = IngredientMapper.toIngredient(ingredientInputDTO);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toOwnerIngredientDTO(savedIngredient);
    }

    @Override
    public IngredientDTO updateIngredient(Long id, IngredientInputDTO ingredientInputDTO) {
        Ingredient existingIngredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id));
        existingIngredient.setName(ingredientInputDTO.getName());
        existingIngredient.setQuantity(ingredientInputDTO.getQuantity());
        Ingredient updatedIngredient = ingredientRepository.save(existingIngredient);
        return IngredientMapper.toOwnerIngredientDTO(updatedIngredient);
    }


    @Override
    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id));
        ingredientRepository.delete(ingredient);
    }
}
