package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        // Hier kun je initialisatie doen indien nodig
    }

    @Test
    void getAllUsers_ShouldReturnUsers() {
        List<UserDTO> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserDTO());
        when(userService.getAllUsers()).thenReturn(expectedUsers);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getUserById_ShouldReturnUser() {
        UserDTO expectedUser = new UserDTO();
        when(userService.getUserById(1L)).thenReturn(expectedUser);

        ResponseEntity<UserDTO> response = userController.getUserById(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createUser_ShouldCreateUser() {
        UserInputDTO inputDTO = new UserInputDTO();
        UserDTO expectedUser = new UserDTO();
        when(userService.createUser(inputDTO)).thenReturn(expectedUser);

        ResponseEntity<UserDTO> response = userController.createUser(inputDTO);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateUser_ShouldUpdateUser() {
        UserInputDTO inputDTO = new UserInputDTO();
        UserDTO expectedUser = new UserDTO();
        when(userService.updateUser(1L, inputDTO)).thenReturn(expectedUser);

        ResponseEntity<UserDTO> response = userController.updateUser(1L, inputDTO);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void updateUserDeliveryAddress_ShouldUpdateAddress() {
        DeliveryAddressInputDTO addressDTO = new DeliveryAddressInputDTO();
        UserDTO expectedUser = new UserDTO();
        when(userService.updateUserDeliveryAddress(1L, addressDTO)).thenReturn(expectedUser);

        ResponseEntity<UserDTO> response = userController.updateUserDeliveryAddress(1L, addressDTO);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
