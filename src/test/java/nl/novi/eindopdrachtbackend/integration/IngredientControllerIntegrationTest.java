package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.model.Ingredient;
import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

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

    @Autowired
    private UserRepository userRepository;

    private User adminUser;
    private User ownerUser;

    @BeforeEach
    void setUp() {
        Role adminRole = new Role(UserRole.ADMIN);
        Role ownerRole = new Role(UserRole.OWNER);

        adminUser = new User();
        adminUser.setEmail("admin@example.com");
        adminUser.setPassword("password");
        adminUser.setName("Admin User");
        adminUser.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
        userRepository.save(adminUser);

        ownerUser = new User();
        ownerUser.setEmail("owner@example.com");
        ownerUser.setPassword("password");
        ownerUser.setName("Owner User");
        ownerUser.setRoles(new HashSet<>(Collections.singletonList(ownerRole)));
        userRepository.save(ownerUser);
    }

    @Test
    @WithMockUser(username = "admin@exampletest.com", authorities = {"ADMIN"})
    void testGetAllIngredients() throws Exception {
        String uniqueIngredientName = "Unique Test Ingredient";

        Ingredient ingredient = new Ingredient();
        ingredient.setName(uniqueIngredientName);
        ingredient.setQuantity(10);
        ingredient.setCost(5.0);
        ingredient.setOwner(adminUser);
        ingredientRepository.save(ingredient);

        this.mockMvc.perform(get("/ingredients/admin/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name == '%s')].name", uniqueIngredientName).value(uniqueIngredientName));
    }

    @Test
    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
    void testGetAllIngredientsForLoggedInOwner() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test Ingredient");
        ingredient.setQuantity(10);
        ingredient.setCost(5.0);
        ingredient.setOwner(ownerUser);
        ingredientRepository.save(ingredient);

        this.mockMvc.perform(get("/ingredients/owner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Test Ingredient"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
    void testGetIngredientByIdForAdmin() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test Ingredient");
        ingredient.setQuantity(10);
        ingredient.setCost(5.0);
        ingredient.setOwner(ownerUser);
        ingredientRepository.save(ingredient);

        this.mockMvc.perform(get("/ingredients/admin/{ownerId}/ingredient/{id}", ownerUser.getId(), ingredient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Ingredient"));
    }

    @Test
    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
    void testGetIngredientByIdForOwner() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test Ingredient");
        ingredient.setQuantity(10);
        ingredient.setCost(5.0);
        ingredient.setOwner(ownerUser);
        ingredientRepository.save(ingredient);

        this.mockMvc.perform(get("/ingredients/owner/ingredient/{id}", ingredient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Ingredient"));
    }

    @Test
    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
    void testCreateIngredientForOwner() throws Exception {
        IngredientInputDTO ingredientInputDTO = new IngredientInputDTO();
        ingredientInputDTO.setName("New Ingredient");
        ingredientInputDTO.setQuantity(20);
        ingredientInputDTO.setCost(10.0);
        ingredientInputDTO.setExpirationDate(LocalDate.now());

        this.mockMvc.perform(post("/ingredients/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Ingredient\",\"quantity\":20,\"cost\":10.0,\"expirationDate\":\"" + LocalDate.now() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Ingredient"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
    void testCreateIngredientForAdmin() throws Exception {
        IngredientInputDTO ingredientInputDTO = new IngredientInputDTO();
        ingredientInputDTO.setName("New Ingredient");
        ingredientInputDTO.setQuantity(20);
        ingredientInputDTO.setCost(10.0);
        ingredientInputDTO.setExpirationDate(LocalDate.now());
        ingredientInputDTO.setOwnerId(ownerUser.getId());

        this.mockMvc.perform(post("/ingredients/admin/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New Ingredient\",\"quantity\":20,\"cost\":10.0,\"expirationDate\":\"" + LocalDate.now() + "\",\"ownerId\":" + ownerUser.getId() + "}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Ingredient"));
    }

    @Test
    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
    void testUpdateIngredientForOwner() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Old Ingredient");
        ingredient.setQuantity(10);
        ingredient.setCost(5.0);
        ingredient.setOwner(ownerUser);
        ingredientRepository.save(ingredient);

        this.mockMvc.perform(put("/ingredients/owner/{id}", ingredient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Ingredient\",\"quantity\":15,\"cost\":7.0,\"expirationDate\":\"" + LocalDate.now() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Ingredient"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
    void testUpdateIngredientForAdmin() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Old Ingredient");
        ingredient.setQuantity(10);
        ingredient.setCost(5.0);
        ingredient.setOwner(ownerUser);
        ingredientRepository.save(ingredient);

        this.mockMvc.perform(put("/ingredients/admin/{id}", ingredient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Ingredient\",\"quantity\":15,\"cost\":7.0,\"expirationDate\":\"" + LocalDate.now() + "\",\"ownerId\":" + ownerUser.getId() + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Ingredient"));
    }

    @Test
    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
    void testDeleteIngredientForOwner() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test Ingredient");
        ingredient.setQuantity(10);
        ingredient.setCost(5.0);
        ingredient.setOwner(ownerUser);
        ingredientRepository.save(ingredient);

        this.mockMvc.perform(delete("/ingredients/owner/{id}", ingredient.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
    void testDeleteIngredientForAdmin() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test Ingredient");
        ingredient.setQuantity(10);
        ingredient.setCost(5.0);
        ingredient.setOwner(ownerUser);
        ingredientRepository.save(ingredient);

        this.mockMvc.perform(delete("/ingredients/admin/{id}", ingredient.getId()))
                .andExpect(status().isNoContent());
    }
}
