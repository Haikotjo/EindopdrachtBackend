package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.common.ApiResponse;
import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    private final UserRepository userRepository;

    @Autowired
    public IngredientController(IngredientService ingredientService, UserRepository userRepository) {
        this.ingredientService = ingredientService;
        this.userRepository = userRepository;
    }

    /**
     * Get all Ingredients (Admin only)
     *
     * @return ResponseEntity containing a list of IngredientDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        try {
            List<IngredientDTO> ingredients = ingredientService.getAllIngredients();
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for admin to retrieve all ingredients for a specific owner.
     *
     * @param ownerId the ID of the owner
     * @return ResponseEntity containing a list of IngredientDTO objects
     */
    @GetMapping("/admin/{ownerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<IngredientDTO>> getAllIngredientsForOwner(@PathVariable Long ownerId) {
        try {
            List<IngredientDTO> ingredients = ingredientService.getAllIngredientsForOwner(ownerId);
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for the owner to retrieve all ingredients for themselves.
     *
     * @return ResponseEntity containing a list of IngredientDTO objects
     */
    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<IngredientDTO>> getAllIngredientsForLoggedInOwner() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        try {
            List<IngredientDTO> ingredients = ingredientService.getAllIngredientsForLoggedInOwner(currentUserEmail);
            return new ResponseEntity<>(ingredients, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for admin to retrieve specific ingredient of a specific owner.
     *
     * @param ownerId the ID of the owner
     * @param id the ID of the ingredient
     * @return  ResponseEntity containing the IngredientDTO object for the specific ID.
     */
    @GetMapping("/admin/{ownerId}/ingredient/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IngredientDTO> getIngredientByIdForAdmin(@PathVariable Long ownerId, @PathVariable Long id) {
        try {
            IngredientDTO ingredient = ingredientService.getIngredientByIdForAdmin(id, ownerId);
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get a specific ingredient for the logged-in owner.
     *
     * @param id the ID of the ingredient
     * @return ResponseEntity containing the IngredientDTO object
     */
    @GetMapping("/owner/ingredient/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<IngredientDTO> getIngredientByIdForOwner(@PathVariable Long id) {
        try {
            String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
            User currentUser = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
            IngredientDTO ingredient = ingredientService.getIngredientByIdForOwner(id, currentUser.getId());
            return new ResponseEntity<>(ingredient, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create an ingredient for the logged-in owner.
     *
     * @param ingredientInputDTO the ingredient input data transfer object
     * @return ResponseEntity containing the created IngredientDTO object
     */
    @PostMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<IngredientDTO> createIngredientForOwner(@RequestBody IngredientInputDTO ingredientInputDTO) {
        try {
            User currentUser = getCurrentUser();
            IngredientDTO newIngredient = ingredientService.createIngredientForOwner(ingredientInputDTO, currentUser);
            return new ResponseEntity<>(newIngredient, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create an ingredient for a specific owner (Admin only).
     *
     * @param ingredientInputDTO the ingredient input data transfer object
     * @return ResponseEntity containing the created IngredientDTO object
     */
    @PostMapping("/admin/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IngredientDTO> createIngredientForAdmin(@RequestBody IngredientInputDTO ingredientInputDTO) {
        try {
            Long ownerId = ingredientInputDTO.getOwnerId();
            IngredientDTO ingredientDTO = ingredientService.createIngredientForAdmin(ingredientInputDTO, ownerId);
            return new ResponseEntity<>(ingredientDTO, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an ingredient for the logged-in owner.
     *
     * @param id the ID of the ingredient
     * @param ingredientInputDTO the ingredient input data transfer object
     * @return ResponseEntity containing the updated IngredientDTO object
     */
    @PutMapping("/owner/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<IngredientDTO> updateIngredientForOwner(@PathVariable Long id, @RequestBody IngredientInputDTO ingredientInputDTO) {
        try {
            User currentUser = getCurrentUser();
            IngredientDTO updatedIngredient = ingredientService.updateIngredientForOwner(id, ingredientInputDTO, currentUser.getId());
            return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Update an ingredient for a specific owner (Admin only).
     *
     * @param id the ID of the ingredient
     * @param ingredientInputDTO the ingredient input data transfer object
     * @return ResponseEntity containing the updated IngredientDTO object
     */
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IngredientDTO> updateIngredientForAdmin(@PathVariable Long id, @RequestBody IngredientInputDTO ingredientInputDTO) {
        try {
            Long ownerId = ingredientInputDTO.getOwnerId(); // Verondersteld dat ownerId is toegevoegd aan IngredientInputDTO
            IngredientDTO updatedIngredient = ingredientService.updateIngredientForAdmin(id, ingredientInputDTO, ownerId);
            return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete an ingredient for the logged-in owner.
     *
     * @param id the ID of the ingredient
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/owner/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<Void> deleteIngredientForOwner(@PathVariable Long id) {
        try {
            User currentUser = getCurrentUser();
            ingredientService.deleteIngredientForOwner(id, currentUser);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete an ingredient (Admin only).
     *
     * @param id the ID of the ingredient
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteIngredientForAdmin(@PathVariable Long id) {
        try {
            ingredientService.deleteIngredientForAdmin(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve the currently authenticated user from the security context.
     *
     * This method fetches the email of the currently authenticated user from the security context,
     * retrieves the corresponding User entity from the user repository, and returns the User object.
     * If the user is not found, it throws a ResourceNotFoundException.
     *
     * @return the User object representing the currently authenticated user
     * @throws ResourceNotFoundException if no user is found with the current authenticated email
     */
    private User getCurrentUser() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
        System.out.println("Current User Email: " + currentUserEmail);
        System.out.println("Current User Roles: " + user.getRoles());
        return user;
    }
}
