//package nl.novi.eindopdrachtbackend.integration;
//
//import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
//import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
//import nl.novi.eindopdrachtbackend.model.Ingredient;
//import nl.novi.eindopdrachtbackend.model.User;
//import nl.novi.eindopdrachtbackend.repository.IngredientRepository;
//import nl.novi.eindopdrachtbackend.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.lang.reflect.Field;
//import java.time.LocalDate;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//class IngredientControllerIntegrationTest {
//
//    @Autowired
//    private WebApplicationContext wac;
//
//    @Autowired
//    private IngredientRepository ingredientRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private MockMvc mockMvc;
//    private User adminUser;
//    private User ownerUser;
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//
//        adminUser = new User();
//        adminUser.setEmail("admin@example.com");
//        adminUser.setPassword("password");
//        adminUser.setRoles(Collections.singletonList("ADMIN"));
//        userRepository.save(adminUser);
//
//        ownerUser = new User();
//        ownerUser.setEmail("owner@example.com");
//        ownerUser.setPassword("password");
//        ownerUser.setRoles(Collections.singletonList("OWNER"));
//        userRepository.save(ownerUser);
//    }
//
//    private void setId(Object target, Long id) throws Exception {
//        Field field = target.getClass().getDeclaredField("id");
//        field.setAccessible(true);
//        field.set(target, id);
//    }
//
//    @Test
//    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
//    void testGetAllIngredients() throws Exception {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Test Ingredient");
//        ingredient.setQuantity(10);
//        ingredient.setCost(5.0);
//        ingredient.setOwner(adminUser);
//        ingredientRepository.save(ingredient);
//
//        this.mockMvc.perform(get("/ingredients/admin/all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].name").value("Test Ingredient"));
//    }
//
//    @Test
//    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
//    void testGetAllIngredientsForOwner() throws Exception {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Test Ingredient");
//        ingredient.setQuantity(10);
//        ingredient.setCost(5.0);
//        ingredient.setOwner(ownerUser);
//        ingredientRepository.save(ingredient);
//
//        this.mockMvc.perform(get("/ingredients/admin/{ownerId}", ownerUser.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].name").value("Test Ingredient"));
//    }
//
//    @Test
//    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
//    void testGetAllIngredientsForLoggedInOwner() throws Exception {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Test Ingredient");
//        ingredient.setQuantity(10);
//        ingredient.setCost(5.0);
//        ingredient.setOwner(ownerUser);
//        ingredientRepository.save(ingredient);
//
//        this.mockMvc.perform(get("/ingredients/owner"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[0].name").value("Test Ingredient"));
//    }
//
//    @Test
//    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
//    void testGetIngredientByIdForAdmin() throws Exception {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Test Ingredient");
//        ingredient.setQuantity(10);
//        ingredient.setCost(5.0);
//        ingredient.setOwner(ownerUser);
//        ingredientRepository.save(ingredient);
//
//        this.mockMvc.perform(get("/ingredients/admin/{ownerId}/ingredient/{id}", ownerUser.getId(), ingredient.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Test Ingredient"));
//    }
//
//    @Test
//    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
//    void testGetIngredientByIdForOwner() throws Exception {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Test Ingredient");
//        ingredient.setQuantity(10);
//        ingredient.setCost(5.0);
//        ingredient.setOwner(ownerUser);
//        ingredientRepository.save(ingredient);
//
//        this.mockMvc.perform(get("/ingredients/owner/ingredient/{id}", ingredient.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Test Ingredient"));
//    }
//
//    @Test
//    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
//    void testCreateIngredientForOwner() throws Exception {
//        IngredientInputDTO ingredientInputDTO = new IngredientInputDTO();
//        ingredientInputDTO.setName("New Ingredient");
//        ingredientInputDTO.setQuantity(20);
//        ingredientInputDTO.setCost(10.0);
//        ingredientInputDTO.setExpirationDate(LocalDate.now());
//
//        this.mockMvc.perform(post("/ingredients/owner")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"New Ingredient\",\"quantity\":20,\"cost\":10.0,\"expirationDate\":\"" + LocalDate.now() + "\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("New Ingredient"));
//    }
//
//    @Test
//    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
//    void testCreateIngredientForAdmin() throws Exception {
//        IngredientInputDTO ingredientInputDTO = new IngredientInputDTO();
//        ingredientInputDTO.setName("New Ingredient");
//        ingredientInputDTO.setQuantity(20);
//        ingredientInputDTO.setCost(10.0);
//        ingredientInputDTO.setExpirationDate(LocalDate.now());
//        ingredientInputDTO.setOwnerId(ownerUser.getId());
//
//        this.mockMvc.perform(post("/ingredients/admin/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"New Ingredient\",\"quantity\":20,\"cost\":10.0,\"expirationDate\":\"" + LocalDate.now() + "\",\"ownerId\":" + ownerUser.getId() + "}"))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("New Ingredient"));
//    }
//
//    @Test
//    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
//    void testUpdateIngredientForOwner() throws Exception {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Old Ingredient");
//        ingredient.setQuantity(10);
//        ingredient.setCost(5.0);
//        ingredient.setOwner(ownerUser);
//        ingredientRepository.save(ingredient);
//
//        this.mockMvc.perform(put("/ingredients/owner/{id}", ingredient.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"Updated Ingredient\",\"quantity\":15,\"cost\":7.0,\"expirationDate\":\"" + LocalDate.now() + "\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Updated Ingredient"));
//    }
//
//    @Test
//    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
//    void testUpdateIngredientForAdmin() throws Exception {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Old Ingredient");
//        ingredient.setQuantity(10);
//        ingredient.setCost(5.0);
//        ingredient.setOwner(ownerUser);
//        ingredientRepository.save(ingredient);
//
//        this.mockMvc.perform(put("/ingredients/admin/{id}", ingredient.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"Updated Ingredient\",\"quantity\":15,\"cost\":7.0,\"expirationDate\":\"" + LocalDate.now() + "\",\"ownerId\":" + ownerUser.getId() + "}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Updated Ingredient"));
//    }
//
//    @Test
//    @WithMockUser(username = "owner@example.com", authorities = {"OWNER"})
//    void testDeleteIngredientForOwner() throws Exception {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Test Ingredient");
//        ingredient.setQuantity(10);
//        ingredient.setCost(5.0);
//        ingredient.setOwner(ownerUser);
//        ingredientRepository.save(ingredient);
//
//        this.mockMvc.perform(delete("/ingredients/owner/{id}", ingredient.getId()))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
//    void testDeleteIngredientForAdmin() throws Exception {
//        Ingredient ingredient = new Ingredient();
//        ingredient.setName("Test Ingredient");
//        ingredient.setQuantity(10);
//        ingredient.setCost(5.0);
//        ingredient.setOwner(ownerUser);
//        ingredientRepository.save(ingredient);
//
//        this.mockMvc.perform(delete("/ingredients/admin/{id}", ingredient.getId()))
//                .andExpect(status().isNoContent());
//    }
//}
