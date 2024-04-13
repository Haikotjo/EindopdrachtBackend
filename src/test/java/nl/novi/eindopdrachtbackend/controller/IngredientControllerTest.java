package nl.novi.eindopdrachtbackend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientMapper;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new IngredientController(ingredientService)).build();
    }

    @Test
    public void testGetAllIngredients() throws Exception {
        // Arrange
        List<IngredientDTO> ingredients = new ArrayList<>();
        IngredientDTO ingredient1 = new IngredientDTO();
        setField(ingredient1, "id", 1L);
        ingredient1.setName("Tomato");
        ingredient1.setQuantity(10);

        IngredientDTO ingredient2 = new IngredientDTO();
        setField(ingredient2, "id", 2L);
        ingredient2.setName("Lettuce");
        ingredient2.setQuantity(5);

        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

        when(ingredientService.getAllIngredients()).thenReturn(ingredients);

        // Act & Assert
        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Tomato"))
                .andExpect(jsonPath("$[0].quantity").value(10))
                .andExpect(jsonPath("$[1].name").value("Lettuce"))
                .andExpect(jsonPath("$[1].quantity").value(5));
    }

    @Test
    public void testGetIngredientById() throws Exception {
        IngredientDTO mockIngredient = new IngredientDTO();
        mockIngredient.setName("Tomato");
        setField(mockIngredient, "id", 1L);

        when(ingredientService.getIngredientById(1L)).thenReturn(mockIngredient);

        mockMvc.perform(get("/ingredients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tomato"));
    }

    @Test
    public void testCreateIngredient() throws Exception {
        // Arrange

        Ingredient mockIngredient = new Ingredient();
        setField(mockIngredient, "id", 1L);
        mockIngredient.setName("Tomato");
        mockIngredient.setQuantity(10);

        when(ingredientService.createIngredient(any(IngredientInputDTO.class))).thenReturn(mockIngredient);

        IngredientInputDTO inputDTO = new IngredientInputDTO();
        inputDTO.setName("Tomato");
        inputDTO.setQuantity(10);

        // Act & Assert
        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Tomato"))
                .andExpect(jsonPath("$.data.quantity").value(10));
    }

    @Test
    public void testUpdateIngredient() throws Exception {
        // Arrange
        Long id = 1L;
        IngredientInputDTO inputDTO = new IngredientInputDTO();
        inputDTO.setName("Updated Tomato");
        inputDTO.setQuantity(20);

        Ingredient updatedIngredient = new Ingredient();
        updatedIngredient.setName("Updated Tomato");
        updatedIngredient.setQuantity(20);
        setField(updatedIngredient, "id", id);  // Zet het ID veld met reflectie

        IngredientDTO updatedIngredientDTO = new IngredientDTO();
        updatedIngredientDTO.setName(updatedIngredient.getName());
        updatedIngredientDTO.setQuantity(updatedIngredient.getQuantity());
        setField(updatedIngredientDTO, "id", id);  // Zet het ID veld met reflectie

        when(ingredientService.updateIngredient(eq(id), any(IngredientInputDTO.class))).thenReturn(updatedIngredient);

        // Act & Assert
        mockMvc.perform(put("/ingredients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Tomato"))
                .andExpect(jsonPath("$.data.quantity").value(20));
    }
    @Test
    public void testDeleteIngredient() throws Exception {
        // Arrange
        Long id = 1L;
        doNothing().when(ingredientService).deleteIngredient(id);

        // Act & Assert
        mockMvc.perform(delete("/ingredients/{id}", id))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Ingredient successfully deleted."));

        verify(ingredientService).deleteIngredient(id);
    }

    private void setField(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to set field value via reflection", e);
        }
    }
}
