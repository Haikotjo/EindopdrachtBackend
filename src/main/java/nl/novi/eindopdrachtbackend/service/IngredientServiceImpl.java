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
import org.springframework.security.access.AccessDeniedException;
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
    public List<IngredientDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        if (ingredients.isEmpty()) {
            throw new ResourceNotFoundException("No ingredients found");
        }
        return ingredients.stream()
                .map(IngredientMapper::toOwnerIngredientDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IngredientDTO> getAllIngredientsForOwner(Long ownerId) {
        List<Ingredient> ingredients = ingredientRepository.findByOwner_Id(ownerId);
        if (ingredients.isEmpty()) {
            throw new ResourceNotFoundException("No ingredients found for owner with ID " + ownerId);
        }
        return ingredients.stream()
                .map(IngredientMapper::toOwnerIngredientDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
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
    public IngredientDTO getIngredientByIdForAdmin(Long id, Long ownerId) {
        Ingredient ingredient = ingredientRepository.findByIdAndOwner_Id(id, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id + " and owner id :: " + ownerId));
        return IngredientMapper.toOwnerIngredientDTO(ingredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IngredientDTO getIngredientByIdForOwner(Long id, Long ownerId) {
        Ingredient ingredient = ingredientRepository.findByIdAndOwner_Id(id, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + id + " and owner id :: " + ownerId));
        return IngredientMapper.toOwnerIngredientDTO(ingredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IngredientDTO createIngredientForOwner(IngredientInputDTO ingredientInputDTO, User owner) {
        Ingredient ingredient = IngredientMapper.toIngredient(ingredientInputDTO, owner);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toOwnerIngredientDTO(savedIngredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IngredientDTO createIngredientForAdmin(IngredientInputDTO ingredientInputDTO, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + ownerId));
        Ingredient ingredient = IngredientMapper.toIngredient(ingredientInputDTO, owner);
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toOwnerIngredientDTO(savedIngredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IngredientDTO updateIngredientForOwner(Long id, IngredientInputDTO ingredientInputDTO, Long ownerId) {
        Ingredient ingredient = ingredientRepository.findByIdAndOwner_Id(id, ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + id + " for owner id: " + ownerId));

        ingredient.setName(ingredientInputDTO.getName());
        ingredient.setQuantity(ingredientInputDTO.getQuantity());
        ingredient.setUnit(ingredientInputDTO.getUnit());
        ingredient.setCost(ingredientInputDTO.getCost());
        ingredient.setSupplier(ingredientInputDTO.getSupplier());
        ingredient.setExpirationDate(ingredientInputDTO.getExpirationDate());
        ingredient.setDescription(ingredientInputDTO.getDescription());

        Ingredient updatedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toOwnerIngredientDTO(updatedIngredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IngredientDTO updateIngredientForAdmin(Long id, IngredientInputDTO ingredientInputDTO, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + ownerId));

        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + id));

        ingredient.setName(ingredientInputDTO.getName());
        ingredient.setQuantity(ingredientInputDTO.getQuantity());
        ingredient.setUnit(ingredientInputDTO.getUnit());
        ingredient.setCost(ingredientInputDTO.getCost());
        ingredient.setSupplier(ingredientInputDTO.getSupplier());
        ingredient.setExpirationDate(ingredientInputDTO.getExpirationDate());
        ingredient.setDescription(ingredientInputDTO.getDescription());
        ingredient.setOwner(owner);

        Ingredient updatedIngredient = ingredientRepository.save(ingredient);
        return IngredientMapper.toOwnerIngredientDTO(updatedIngredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteIngredientForOwner(final Long id, final User owner) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + id));
        if (!ingredient.getOwner().getId().equals(owner.getId())) {
            throw new AccessDeniedException("You do not have permission to delete this ingredient");
        }
        ingredientRepository.delete(ingredient);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteIngredientForAdmin(final Long id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + id));
        ingredientRepository.delete(ingredient);
    }
}
