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

    /**
     * {@inheritDoc}
     */
    @Override
    public IngredientDTO createIngredient(IngredientInputDTO ingredientInputDTO) {
        Ingredient ingredient = IngredientMapper.toIngredient(ingredientInputDTO);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toOwnerIngredientDTO(savedIngredient);
    }

    /**
     * {@inheritDoc}
     */
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
    public List<IngredientDTO> getAllIngredientsForOwner(Long ownerId) {
        List<Ingredient> ingredients = ingredientRepository.findByMenuItems_Menus_Restaurant_Owner_Id(ownerId);
        return ingredients.stream()
                .map(IngredientMapper::toOwnerIngredientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<IngredientDTO> getAllIngredientsForLoggedInOwner(String email) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return getAllIngredientsForOwner(currentUser.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IngredientDTO getIngredientByIdForOwner(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id));
        return IngredientMapper.toOwnerIngredientDTO(ingredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IngredientDTO getIngredientByIdForCustomer(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id));
        return IngredientMapper.toCustomerIngredientDTO(ingredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id));
        ingredientRepository.delete(ingredient);
    }
}
