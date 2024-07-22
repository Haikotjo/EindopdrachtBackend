//package nl.novi.eindopdrachtbackend.controller;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.BDDMockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
//import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
//import nl.novi.eindopdrachtbackend.dto.IngredientMapper;
//import nl.novi.eindopdrachtbackend.model.Ingredient;
//import nl.novi.eindopdrachtbackend.service.IngredientService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//public class IngredientControllerTest {
//
//    @Mock
//    private IngredientService ingredientService;
//
//    @InjectMocks
//    private IngredientController ingredientController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
//    }
//
//    @Test
//    public void testGetAllIngredients() throws Exception {
//        List<IngredientDTO> ingredients = new ArrayList<>();
//        IngredientDTO tomatoDTO = new IngredientDTO();
//        tomatoDTO.setId(1L);
//        tomatoDTO.setName("Tomato");
//        tomatoDTO.setQuantity(10);
//
//        ingredients.add(tomatoDTO);
//
//        when(ingredientService.getAllIngredients()).thenReturn(ingredients);
//
//        mockMvc.perform(get("/ingredients"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[0].name").value("Tomato"))
//                .andExpect(jsonPath("$[0].quantity").value(10));
//    }
//
//    @Test
//    void testGetIngredientById() throws Exception {
//        // Arrange
//        IngredientDTO mockIngredientDTO = new IngredientDTO();
//        mockIngredientDTO.setId(1L);
//        mockIngredientDTO.setName("Tomato");
//        mockIngredientDTO.setQuantity(10);
//
//        when(ingredientService.getIngredientById(anyLong())).thenReturn(mockIngredientDTO);
//
//        // Act & Assert
//        mockMvc.perform(get("/ingredients/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Tomato"));
//    }
//
//    @Test
//    void testCreateIngredient() throws Exception {
//        IngredientDTO tomatoDTO = new IngredientDTO();
//        tomatoDTO.setName("Tomato");
//        tomatoDTO.setQuantity(10);
//
//        IngredientInputDTO inputDTO = new IngredientInputDTO();
//        inputDTO.setName("Tomato");
//        inputDTO.setQuantity(10);
//
//        when(ingredientService.createIngredient(any(IngredientInputDTO.class))).thenReturn(tomatoDTO);
//
//        mockMvc.perform(post("/ingredients")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(inputDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.name").value("Tomato"))
//                .andExpect(jsonPath("$.data.quantity").value(10));
//    }
//
//    @Test
//    public void testUpdateIngredient() throws Exception {
//        // Arrange
//        Long id = 1L;
//        IngredientInputDTO inputDTO = new IngredientInputDTO();
//        inputDTO.setName("Updated Tomato");
//        inputDTO.setQuantity(20);
//
//        IngredientDTO updatedIngredientDTO = new IngredientDTO();
//        updatedIngredientDTO.setId(id);
//        updatedIngredientDTO.setName("Updated Tomato");
//        updatedIngredientDTO.setQuantity(20);
//
//        when(ingredientService.updateIngredient(eq(id), any(IngredientInputDTO.class))).thenReturn(updatedIngredientDTO);
//
//        // Act & Assert
//        mockMvc.perform(put("/ingredients/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(inputDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.name").value("Updated Tomato"))
//                .andExpect(jsonPath("$.data.quantity").value(20));
//    }
//
//    @Test
//    public void testDeleteIngredient() throws Exception {
//        // Arrange
//        Long id = 1L;
//        doNothing().when(ingredientService).deleteIngredient(id);
//
//        // Act & Assert
//        mockMvc.perform(delete("/ingredients/{id}", id))
//                .andExpect(status().isNoContent());  // Verwacht No Content status
//
//        verify(ingredientService).deleteIngredient(id);
//    }
//
//
//    private void setField(Object object, String fieldName, Object value) {
//        try {
//            Field field = object.getClass().getDeclaredField(fieldName);
//            field.setAccessible(true);
//            field.set(object, value);
//        } catch (NoSuchFieldException | IllegalAccessException e) {
//            throw new RuntimeException("Failed to set field value via reflection", e);
//        }
//    }
//}
