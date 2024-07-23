package nl.novi.eindopdrachtbackend.integration;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.model.MenuItem;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.MenuItemRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MenuItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    private User adminUser;
    private User ownerUser;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        // Maak rollen aan
        Role ownerRole = new Role(UserRole.OWNER);

        // Maak eigenaar gebruiker aan
        ownerUser = new User();
        ownerUser.setEmail("owner@example.com");
        ownerUser.setPassword("password");
        ownerUser.setName("Owner User");
        ownerUser.setRoles(new HashSet<>(Collections.singletonList(ownerRole)));
        userRepository.save(ownerUser);

        // Maak restaurant aan en koppel deze aan de eigenaar
        restaurant = new Restaurant();
        restaurant.setName("Owner's Restaurant");
        restaurant.setOwner(ownerUser);
        restaurantRepository.save(restaurant);

        // Maak menu-item aan en koppel het aan het restaurant
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test MenuItem");
        menuItem.setPrice(10.0);
        menuItem.setDescription("Test Description");
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);
    }



    @Test
    void testGetMenuItemById() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test MenuItem");
        menuItem.setPrice(10.0);
        menuItem.setDescription("Test Description");
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);

        this.mockMvc.perform(get("/menu-items/{id}", menuItem.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test MenuItem"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
    void testCreateMenuItemForRestaurantByAdmin() throws Exception {
        this.mockMvc.perform(post("/menu-items/admin/{restaurantId}/menu-item", restaurant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"New MenuItem\",\"price\":15.0,\"description\":\"New Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New MenuItem"));
    }

    @Test
    @WithMockUser(username = "admin@example.com", authorities = {"ADMIN"})
    void testDeleteMenuItemByAdmin() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Test MenuItem");
        menuItem.setPrice(10.0);
        menuItem.setDescription("Test Description");
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);

        this.mockMvc.perform(delete("/menu-items/admin/{menuItemId}", menuItem.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByNameIgnoreCase() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setName("Unique MenuItem");
        menuItem.setPrice(10.0);
        menuItem.setDescription("Test Description");
        menuItem.setRestaurant(restaurant);
        menuItemRepository.save(menuItem);

        this.mockMvc.perform(get("/menu-items/search").param("name", "unique menuitem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Unique MenuItem"));
    }
}
