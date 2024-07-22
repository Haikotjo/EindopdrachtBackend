package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.IngredientDTO;
import nl.novi.eindopdrachtbackend.dto.IngredientInputDTO;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;


@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private IngredientController ingredientController;

    private User user;
    private IngredientDTO ingredientDTO;
    private IngredientInputDTO ingredientInputDTO;

    @BeforeEach
    void setUp() throws Exception {
        user = new User();
        setId(user, 1L);
        user.setEmail("test@example.com");

        ingredientDTO = new IngredientDTO();
        setId(ingredientDTO, 1L);
        ingredientDTO.setName("Test Ingredient");

        ingredientInputDTO = new IngredientInputDTO();
        ingredientInputDTO.setName("Test Ingredient Input");
        ingredientInputDTO.setOwnerId(1L);
    }

    private void setId(Object target, Long id) throws Exception {
        Field field = target.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(target, id);
    }

    @Test
    void testGetAllIngredients() {
        when(ingredientService.getAllIngredients()).thenReturn(List.of(ingredientDTO));

        ResponseEntity<List<IngredientDTO>> response = ingredientController.getAllIngredients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ingredientService, times(1)).getAllIngredients();
    }

    @Test
    void testGetAllIngredientsForOwner() {
        when(ingredientService.getAllIngredientsForOwner(anyLong())).thenReturn(List.of(ingredientDTO));

        ResponseEntity<List<IngredientDTO>> response = ingredientController.getAllIngredientsForOwner(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ingredientService, times(1)).getAllIngredientsForOwner(anyLong());
    }

    @Test
    void testGetAllIngredientsForLoggedInOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Lenient mocking for UserRepository.findByEmail() method
            lenient().when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

            // Mocking IngredientService.getAllIngredientsForLoggedInOwner() method
            when(ingredientService.getAllIngredientsForLoggedInOwner(anyString())).thenReturn(List.of(ingredientDTO));

            // Calling the controller method
            ResponseEntity<List<IngredientDTO>> response = ingredientController.getAllIngredientsForLoggedInOwner();

            // Verifying the response
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(1, response.getBody().size());

            // Verifying the mocked interactions
            verify(ingredientService, times(1)).getAllIngredientsForLoggedInOwner(anyString());
        }
    }

    @Test
    void testGetIngredientByIdForAdmin() {
        when(ingredientService.getIngredientByIdForAdmin(anyLong(), anyLong())).thenReturn(ingredientDTO);

        ResponseEntity<IngredientDTO> response = ingredientController.getIngredientByIdForAdmin(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ingredientDTO, response.getBody());
        verify(ingredientService, times(1)).getIngredientByIdForAdmin(anyLong(), anyLong());
    }

    @Test
    void testGetIngredientByIdForOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Mocking UserRepository.findByEmail() method
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

            // Mocking IngredientService.getIngredientByIdForOwner() method
            when(ingredientService.getIngredientByIdForOwner(anyLong(), anyLong())).thenReturn(ingredientDTO);

            // Calling the controller method
            ResponseEntity<IngredientDTO> response = ingredientController.getIngredientByIdForOwner(1L);

            // Verifying the response
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(ingredientDTO, response.getBody());

            // Verifying the mocked interactions
            verify(ingredientService, times(1)).getIngredientByIdForOwner(anyLong(), anyLong());
        }
    }

    @Test
    void testCreateIngredientForOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Mocking UserRepository.findByEmail() method
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

            // Mocking IngredientService.createIngredientForOwner() method
            when(ingredientService.createIngredientForOwner(any(IngredientInputDTO.class), any(User.class))).thenReturn(ingredientDTO);

            // Calling the controller method
            ResponseEntity<IngredientDTO> response = ingredientController.createIngredientForOwner(ingredientInputDTO);

            // Verifying the response
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(ingredientDTO, response.getBody());

            // Verifying the mocked interactions
            verify(ingredientService, times(1)).createIngredientForOwner(any(IngredientInputDTO.class), any(User.class));
        }
    }


    @Test
    void testCreateIngredientForAdmin() {
        when(ingredientService.createIngredientForAdmin(any(IngredientInputDTO.class), anyLong())).thenReturn(ingredientDTO);

        ResponseEntity<IngredientDTO> response = ingredientController.createIngredientForAdmin(ingredientInputDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ingredientDTO, response.getBody());
        verify(ingredientService, times(1)).createIngredientForAdmin(any(IngredientInputDTO.class), anyLong());
    }

    @Test
    void testUpdateIngredientForOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Mocking UserRepository.findByEmail() method
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

            // Mocking IngredientService.updateIngredientForOwner() method
            when(ingredientService.updateIngredientForOwner(anyLong(), any(IngredientInputDTO.class), anyLong())).thenReturn(ingredientDTO);

            // Calling the controller method
            ResponseEntity<IngredientDTO> response = ingredientController.updateIngredientForOwner(1L, ingredientInputDTO);

            // Verifying the response
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(ingredientDTO, response.getBody());

            // Verifying the mocked interactions
            verify(ingredientService, times(1)).updateIngredientForOwner(anyLong(), any(IngredientInputDTO.class), anyLong());
        }
    }


    @Test
    void testUpdateIngredientForAdmin() {
        when(ingredientService.updateIngredientForAdmin(anyLong(), any(IngredientInputDTO.class), anyLong())).thenReturn(ingredientDTO);

        ResponseEntity<IngredientDTO> response = ingredientController.updateIngredientForAdmin(1L, ingredientInputDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ingredientDTO, response.getBody());
        verify(ingredientService, times(1)).updateIngredientForAdmin(anyLong(), any(IngredientInputDTO.class), anyLong());
    }

    @Test
    void testDeleteIngredientForOwner() {
        // Mocking SecurityUtils.getCurrentAuthenticatedUserEmail() static method
        try (MockedStatic<SecurityUtils> mockedSecurityUtils = mockStatic(SecurityUtils.class)) {
            mockedSecurityUtils.when(SecurityUtils::getCurrentAuthenticatedUserEmail).thenReturn("test@example.com");

            // Mocking UserRepository.findByEmail() method
            when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

            // Mocking IngredientService.deleteIngredientForOwner() method
            doNothing().when(ingredientService).deleteIngredientForOwner(anyLong(), any(User.class));

            // Calling the controller method
            ResponseEntity<Void> response = ingredientController.deleteIngredientForOwner(1L);

            // Verifying the response
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

            // Verifying the mocked interactions
            verify(ingredientService, times(1)).deleteIngredientForOwner(anyLong(), any(User.class));
        }
    }


    @Test
    void testDeleteIngredientForAdmin() {
        doNothing().when(ingredientService).deleteIngredientForAdmin(anyLong());

        ResponseEntity<Void> response = ingredientController.deleteIngredientForAdmin(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ingredientService, times(1)).deleteIngredientForAdmin(anyLong());
    }
}
