package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RoleControllerTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleController roleController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void testGetAllRoles() throws Exception {
        Role customerRole = new Role(UserRole.CUSTOMER);
        Role ownerRole = new Role(UserRole.OWNER);
        Role adminRole = new Role(UserRole.ADMIN);

        List<Role> roles = Arrays.asList(customerRole, ownerRole, adminRole);

        given(roleRepository.findAll()).willReturn(roles);

        mockMvc.perform(get("/roles")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rolename").value("CUSTOMER"))
                .andExpect(jsonPath("$[1].rolename").value("OWNER"))
                .andExpect(jsonPath("$[2].rolename").value("ADMIN"));
    }
}
