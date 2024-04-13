package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.common.ApiResponse;
import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientMapper;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        List<IngredientDTO> ingredients = ingredientService.getAllIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) {
        IngredientDTO ingredient = ingredientService.getIngredientById(id);
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createIngredient(@RequestBody IngredientInputDTO ingredientInputDTO) {
        Ingredient ingredient = ingredientService.createIngredient(ingredientInputDTO);
        IngredientDTO createdIngredientDto = IngredientMapper.toIngredientDTO(ingredient);
        ApiResponse apiResponse = new ApiResponse(true, "Ingredient successfully created.", createdIngredientDto);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateIngredient(@PathVariable Long id, @RequestBody IngredientInputDTO ingredientInputDTO) {
        Ingredient updatedIngredient = ingredientService.updateIngredient(id, ingredientInputDTO);
        IngredientDTO updatedIngredientDTO = IngredientMapper.toIngredientDTO(updatedIngredient);
        ApiResponse apiResponse = new ApiResponse(true, "Ingredient successfully updated.", updatedIngredientDTO);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
