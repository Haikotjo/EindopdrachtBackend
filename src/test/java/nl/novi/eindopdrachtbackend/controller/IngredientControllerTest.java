package nl.novi.eindopdrachtbackend.controller;

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
    public void testGetIngredientById() throws Exception {
        IngredientDTO mockIngredient = new IngredientDTO();
        mockIngredient.setName("Tomato");
        setField(mockIngredient, "id", 1L);  // Reflectie om 'id' te zetten

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
