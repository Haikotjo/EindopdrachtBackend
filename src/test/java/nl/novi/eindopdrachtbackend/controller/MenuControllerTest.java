package nl.novi.eindopdrachtbackend.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.MenuInputDTO;
import nl.novi.eindopdrachtbackend.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
    }

    @Test
    public void testGetAllMenus() throws Exception {
        List<MenuDTO> menus = new ArrayList<>();
        MenuDTO lunchMenuDTO = new MenuDTO();
        lunchMenuDTO.setId(1L);
        lunchMenuDTO.setName("Lunch Menu");

        menus.add(lunchMenuDTO);

        when(menuService.getAllMenus()).thenReturn(menus);

        mockMvc.perform(get("/menus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Lunch Menu"));
    }

    @Test
    void testGetMenuById() throws Exception {
        MenuDTO lunchMenuDTO = new MenuDTO();
        lunchMenuDTO.setId(1L);
        lunchMenuDTO.setName("Lunch Menu");

        when(menuService.getMenuById(anyLong())).thenReturn(lunchMenuDTO);

        mockMvc.perform(get("/menus/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lunch Menu"));
    }

    @Test
    public void testCreateMenu() throws Exception {
        MenuInputDTO inputDTO = new MenuInputDTO();
        inputDTO.setName("Dinner Menu");

        MenuDTO createdMenuDTO = new MenuDTO();
        createdMenuDTO.setId(2L);
        createdMenuDTO.setName("Dinner Menu");

        when(menuService.createMenu(any(MenuInputDTO.class))).thenReturn(createdMenuDTO);

        mockMvc.perform(post("/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("Dinner Menu"));
    }

    @Test
    public void testUpdateMenu() throws Exception {
        Long id = 1L;
        MenuInputDTO inputDTO = new MenuInputDTO();
        inputDTO.setName("Updated Lunch Menu");

        MenuDTO updatedMenuDTO = new MenuDTO();
        updatedMenuDTO.setId(id);
        updatedMenuDTO.setName("Updated Lunch Menu");

        when(menuService.updateMenu(eq(id), any(MenuInputDTO.class))).thenReturn(updatedMenuDTO);

        mockMvc.perform(put("/menus/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Lunch Menu"));
    }

    @Test
    public void testDeleteMenu() throws Exception {
        Long id = 1L;
        doNothing().when(menuService).deleteMenu(id);

        mockMvc.perform(delete("/menus/{id}", id))
                .andExpect(status().isNoContent());

        verify(menuService).deleteMenu(id);
    }

    @Test
    public void testFindByNameIgnoreCase() throws Exception {
        List<MenuDTO> menus = new ArrayList<>();
        MenuDTO dinnerMenuDTO = new MenuDTO();
        dinnerMenuDTO.setId(2L);
        dinnerMenuDTO.setName("Dinner Menu");
        menus.add(dinnerMenuDTO);

        when(menuService.findByNameIgnoreCase("dinner")).thenReturn(menus);

        mockMvc.perform(get("/menus/search?name=dinner"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].name").value("Dinner Menu"));
    }

    @Test
    public void testAddMenuItemToMenu() throws Exception {
        Long menuId = 1L;
        Long menuItemId = 2L;

        doNothing().when(menuService).addMenuItemToMenu(menuId, menuItemId);

        mockMvc.perform(post("/menus/{menuId}/addMenuItem/{menuItemId}", menuId, menuItemId))
                .andExpect(status().isOk());

        verify(menuService).addMenuItemToMenu(menuId, menuItemId);
    }
}
