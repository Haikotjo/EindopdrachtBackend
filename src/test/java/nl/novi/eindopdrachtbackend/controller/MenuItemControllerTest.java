package nl.novi.eindopdrachtbackend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.eindopdrachtbackend.dto.MenuItemDTO;
import nl.novi.eindopdrachtbackend.dto.MenuItemInputDTO;
import nl.novi.eindopdrachtbackend.service.MenuItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

public class MenuItemControllerTest {

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private MenuItemController menuItemController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(menuItemController).build();
    }

    @Test
    public void testGetAllMenuItems() throws Exception {
        List<MenuItemDTO> menuItems = new ArrayList<>();
        MenuItemDTO pizzaDTO = new MenuItemDTO();
        pizzaDTO.setId(1L);
        pizzaDTO.setName("Pizza");
        pizzaDTO.setPrice(15.99);
        pizzaDTO.setDescription("Delicious cheese pizza");
        pizzaDTO.setImage("pizza.jpg");

        menuItems.add(pizzaDTO);

        when(menuItemService.getAllMenuItems()).thenReturn(menuItems);

        mockMvc.perform(get("/menuItems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Pizza"))
                .andExpect(jsonPath("$[0].price").value(15.99))
                .andExpect(jsonPath("$[0].description").value("Delicious cheese pizza"));
    }

    @Test
    void testGetMenuItemById() throws Exception {
        MenuItemDTO pizzaDTO = new MenuItemDTO();
        pizzaDTO.setId(1L);
        pizzaDTO.setName("Pizza");
        pizzaDTO.setPrice(15.99);
        pizzaDTO.setDescription("Delicious cheese pizza");

        when(menuItemService.getMenuItemById(anyLong())).thenReturn(pizzaDTO);

        mockMvc.perform(get("/menuItems/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza"))
                .andExpect(jsonPath("$.price").value(15.99))
                .andExpect(jsonPath("$.description").value("Delicious cheese pizza"));
    }

    @Test
    public void testCreateMenuItem() throws Exception {
        MenuItemInputDTO inputDTO = new MenuItemInputDTO();
        inputDTO.setName("Burger");
        inputDTO.setPrice(12.99);
        inputDTO.setDescription("Beef burger");

        MenuItemDTO createdMenuItemDTO = new MenuItemDTO();
        createdMenuItemDTO.setId(2L);
        createdMenuItemDTO.setName("Burger");
        createdMenuItemDTO.setPrice(12.99);
        createdMenuItemDTO.setDescription("Beef burger");

        when(menuItemService.createMenuItem(any(MenuItemInputDTO.class))).thenReturn(createdMenuItemDTO);

        mockMvc.perform(post("/menuItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Burger"))
                .andExpect(jsonPath("$.data.price").value(12.99))
                .andExpect(jsonPath("$.data.description").value("Beef burger"));
    }

    @Test
    public void testUpdateMenuItem() throws Exception {
        Long id = 1L;
        MenuItemInputDTO inputDTO = new MenuItemInputDTO();
        inputDTO.setName("Updated Pizza");
        inputDTO.setPrice(16.99);
        inputDTO.setDescription("Pizza with extra cheese");

        MenuItemDTO updatedMenuItemDTO = new MenuItemDTO();
        updatedMenuItemDTO.setId(id);
        updatedMenuItemDTO.setName("Updated Pizza");
        updatedMenuItemDTO.setPrice(16.99);
        updatedMenuItemDTO.setDescription("Pizza with extra cheese");

        when(menuItemService.updateMenuItem(eq(id), any(MenuItemInputDTO.class))).thenReturn(updatedMenuItemDTO);

        mockMvc.perform(put("/menuItems/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Pizza"))
                .andExpect(jsonPath("$.data.price").value(16.99))
                .andExpect(jsonPath("$.data.description").value("Pizza with extra cheese"));
    }

    @Test
    public void testDeleteMenuItem() throws Exception {
        Long id = 1L;
        doNothing().when(menuItemService).deleteMenuItem(id);

        mockMvc.perform(delete("/menuItems/{id}", id))
                .andExpect(status().isNoContent());

        verify(menuItemService).deleteMenuItem(id);
    }

    @Test
    public void testFindByNameIgnoreCase() throws Exception {
        List<MenuItemDTO> menuItems = new ArrayList<>();
        MenuItemDTO burgerDTO = new MenuItemDTO();
        burgerDTO.setId(2L);
        burgerDTO.setName("Burger");
        burgerDTO.setPrice(12.99);
        burgerDTO.setDescription("Beef burger");
        burgerDTO.setImage("burger.jpg");
        menuItems.add(burgerDTO);

        when(menuItemService.findByNameIgnoreCase("burger")).thenReturn(menuItems);

        mockMvc.perform(get("/menuItems/search?name=burger"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].name").value("Burger"))
                .andExpect(jsonPath("$[0].price").value(12.99))
                .andExpect(jsonPath("$[0].description").value("Beef burger"));
    }

    @Test
    public void testAddIngredientToMenuItem() throws Exception {
        Long menuItemId = 1L;
        Long ingredientId = 1L;

        doNothing().when(menuItemService).addIngredientToMenuItem(menuItemId, ingredientId);

        mockMvc.perform(post("/menuItems/{menuItemId}/addIngredient/{ingredientId}", menuItemId, ingredientId))
                .andExpect(status().isOk());

        verify(menuItemService).addIngredientToMenuItem(menuItemId, ingredientId);
    }
}
