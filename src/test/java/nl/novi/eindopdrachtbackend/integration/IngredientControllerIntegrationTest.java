package nl.novi.eindopdrachtbackend.integration;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class IngredientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IngredientRepository ingredientRepository;

    private Long ingredientId;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        entityManager.createNativeQuery("DELETE FROM menu_item_ingredients").executeUpdate();

        ingredientRepository.deleteAll();

        Ingredient ingredient = new Ingredient();
        ingredient.setName("Original Ingredient");
        ingredient.setQuantity(5);
        ingredient = ingredientRepository.save(ingredient);
        ingredientId = ingredient.getId();
    }


    @Test
    void testGetAllIngredients() throws Exception {
        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetIngredientById() throws Exception {
        mockMvc.perform(get("/ingredients/{id}", ingredientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    void testCreateIngredient() throws Exception {
        IngredientInputDTO newIngredient = new IngredientInputDTO();
        newIngredient.setName("New Ingredient");
        newIngredient.setQuantity(5);

        mockMvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newIngredient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("New Ingredient"));
    }

    @Test
    void testUpdateIngredient() throws Exception {
        IngredientInputDTO updatedIngredient = new IngredientInputDTO();
        updatedIngredient.setName("Updated Ingredient");
        updatedIngredient.setQuantity(10);

        mockMvc.perform(put("/ingredients/{id}", ingredientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedIngredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Ingredient"));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        mockMvc.perform(delete("/ingredients/{id}", ingredientId))
                .andExpect(status().isNoContent());
    }
}
