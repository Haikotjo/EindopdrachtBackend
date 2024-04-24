//package nl.novi.eindopdrachtbackend.controller;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
//import static org.mockito.Mockito.*;
//import static org.mockito.BDDMockito.*;
//
//import nl.novi.eindopdrachtbackend.controller.UserController;
//import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
//import nl.novi.eindopdrachtbackend.dto.UserDTO;
//import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
//import nl.novi.eindopdrachtbackend.model.UserRole;
//import nl.novi.eindopdrachtbackend.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import javax.management.relation.Role;
//import java.util.Arrays;
//import java.util.List;
//
//public class UserControllerTest {
//
//    private MockMvc mockMvc;
//    private UserService userService;
//
//    @BeforeEach
//    public void setup() {
//        userService = mock(UserService.class);
//        UserController userController = new UserController(userService);
//        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//    }
//
//    @Test
//    public void testGetAllUsers() throws Exception {
//        List<UserDTO> users = Arrays.asList(new UserDTO(), new UserDTO());
//        given(userService.getAllUsers()).willReturn(users);
//
//        mockMvc.perform(get("/users"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(users.size()));
//    }
//
//    @Test
//    public void testGetUserById() throws Exception {
//        UserDTO user = new UserDTO();
//        user.setId(1L);
//        given(userService.getUserById(1L)).willReturn(user);
//
//        mockMvc.perform(get("/users/{id}", 1))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L));
//    }
//
//    @Test
//    public void testCreateUser() throws Exception {
//        UserInputDTO userInputDTO = new UserInputDTO();
//        UserDTO returnedUser = new UserDTO();
//        given(userService.createUser(any(UserInputDTO.class))).willReturn(returnedUser);
//
//        mockMvc.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"John Doe\"}"))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    public void testUpdateUser() throws Exception {
//        UserInputDTO userInputDTO = new UserInputDTO();
//        UserDTO updatedUser = new UserDTO();
//        given(userService.updateUser(eq(1L), any(UserInputDTO.class))).willReturn(updatedUser);
//
//        mockMvc.perform(put("/users/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\":\"John Doe\"}"))
//                .andExpect(status().isOk());
//    }
//
////    @Test
////    public void testUpdateUserAndAddress() throws Exception {
////        UserInputDTO userInputDTO = new UserInputDTO();
////        userInputDTO.setName("John Doe");
////        userInputDTO.setEmail("john@example.com");
////        userInputDTO.setPassword("password");
////        userInputDTO.setRole(UserRole.CUSTOMER);
////
////        userInputDTO.setPhoneNumber("123456789");
////
////        DeliveryAddressInputDTO addressInputDTO = new DeliveryAddressInputDTO();
////        addressInputDTO.setStreet("New Street");
////        addressInputDTO.setHouseNumber(123);
////        addressInputDTO.setCity("New City");
////        addressInputDTO.setPostcode("54321");
////        addressInputDTO.setCountry("USA");
////
////        UserDTO updatedUser = new UserDTO();
////        given(userService.updateUserAndAddress(eq(1L), any(UserInputDTO.class), any(DeliveryAddressInputDTO.class))).willReturn(updatedUser);
////
////        mockMvc.perform(put("/users/{id}/address", 1)
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content("{\"name\": \"John Doe\", \"email\": \"john@example.com\", \"password\": \"password\", \"role\": \"CUSTOMER\", \"phoneNumber\": \"123456789\", \"addressInputDTO\": {\"street\": \"New Street\", \"houseNumber\": 123, \"city\": \"New City\", \"postcode\": \"54321\", \"country\": \"USA\"}}"))
////                .andExpect(status().isOk());
////    }
//
//
//    @Test
//    public void testDeleteUser() throws Exception {
//        doNothing().when(userService).deleteUser(1L);
//
//        mockMvc.perform(delete("/users/{id}", 1))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    public void testFindByNameIgnoreCase() throws Exception {
//        List<UserDTO> users = Arrays.asList(new UserDTO(), new UserDTO());
//        given(userService.findByNameIgnoreCase("john")).willReturn(users);
//
//        mockMvc.perform(get("/users/search")
//                        .param("name", "john"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(users.size()));
//    }
//
//    @Test
//    public void testFindByRole() throws Exception {
//        List<UserDTO> users = Arrays.asList(new UserDTO(), new UserDTO());
//        given(userService.findByRole(UserRole.CUSTOMER)).willReturn(users);
//
//        mockMvc.perform(get("/users/role/{role}", UserRole.CUSTOMER))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(users.size()));
//    }
//}
