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

    // Endpoint voor admin om alle ingrediënten van een specifieke eigenaar op te halen
    @GetMapping("/admin/{ownerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<IngredientDTO>> getAllIngredientsForOwner(@PathVariable Long ownerId) {
        List<IngredientDTO> ingredients = ingredientService.getAllIngredientsForOwner(ownerId);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    // Endpoint voor eigenaars om hun eigen ingrediënten op te halen
    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<List<IngredientDTO>> getAllIngredientsForLoggedInOwner() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        List<IngredientDTO> ingredients = ingredientService.getAllIngredientsForLoggedInOwner(currentUserEmail);
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) {
//        IngredientDTO ingredient = ingredientService.getIngredientById(id);
//        return new ResponseEntity<>(ingredient, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<ApiResponse> createIngredient(@RequestBody IngredientInputDTO ingredientInputDTO) {
//        IngredientDTO createdIngredientDto = ingredientService.createIngredient(ingredientInputDTO);
//        ApiResponse apiResponse = new ApiResponse(true, "Ingredient successfully created.", createdIngredientDto);
//        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse> updateIngredient(@PathVariable Long id, @RequestBody IngredientInputDTO ingredientInputDTO) {
//        IngredientDTO updatedIngredientDTO = ingredientService.updateIngredient(id, ingredientInputDTO);
//        ApiResponse apiResponse = new ApiResponse(true, "Ingredient successfully updated.", updatedIngredientDTO);
//        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
//        ingredientService.deleteIngredient(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
