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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    // Endpoint voor admin om alle ingrediënten van een specifieke eigenaar op te halen
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

    // Endpoint voor eigenaars om hun eigen ingrediënten op te halen
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


    // Endpoint voor admin om een specifiek ingrediënt van een eigenaar op te halen
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

    // Endpoint voor eigenaars om een specifiek ingrediënt op te halen
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

    // Endpoint voor eigenaars om een ingrediënt aan te maken
    @PostMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<IngredientDTO> createIngredientForOwner(@RequestBody IngredientInputDTO ingredientInputDTO) {
        User currentUser = getCurrentUser();
        IngredientDTO newIngredient = ingredientService.createIngredientForOwner(ingredientInputDTO, currentUser);
        return new ResponseEntity<>(newIngredient, HttpStatus.CREATED);
    }

    // Endpoint voor admins om een ingrediënt aan te maken voor een specifieke owner
    @PostMapping("/admin/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IngredientDTO> createIngredientForAdmin(@RequestBody IngredientInputDTO ingredientInputDTO) {
        try {
            Long ownerId = ingredientInputDTO.getOwnerId(); // Verondersteld dat ownerId is toegevoegd aan IngredientInputDTO
            IngredientDTO ingredientDTO = ingredientService.createIngredientForAdmin(ingredientInputDTO, ownerId);
            return new ResponseEntity<>(ingredientDTO, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint voor eigenaars om een ingrediënt bij te werken
    @PutMapping("/owner/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<IngredientDTO> updateIngredientForOwner(@PathVariable Long id, @RequestBody IngredientInputDTO ingredientInputDTO) {
        User currentUser = getCurrentUser();
        IngredientDTO updatedIngredient = ingredientService.updateIngredientForOwner(id, ingredientInputDTO, currentUser.getId());
        return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
    }

    // Endpoint voor admins om een ingrediënt bij te werken voor een specifieke eigenaar
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IngredientDTO> updateIngredientForAdmin(@PathVariable Long id, @RequestBody IngredientInputDTO ingredientInputDTO) {
        Long ownerId = ingredientInputDTO.getOwnerId(); // Verondersteld dat ownerId is toegevoegd aan IngredientInputDTO
        IngredientDTO updatedIngredient = ingredientService.updateIngredientForAdmin(id, ingredientInputDTO, ownerId);
        return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
    }

    // Private method to get the currently authenticated user
    private User getCurrentUser() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        return userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
    }

    // Endpoint voor eigenaars om een ingrediënt te verwijderen
    @DeleteMapping("/owner/{id}")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<Void> deleteIngredientForOwner(@PathVariable Long id) {
        User currentUser = getCurrentUser();
        ingredientService.deleteIngredientForOwner(id, currentUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint voor admin om een ingrediënt te verwijderen
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteIngredientForAdmin(@PathVariable Long id) {
        ingredientService.deleteIngredientForAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
