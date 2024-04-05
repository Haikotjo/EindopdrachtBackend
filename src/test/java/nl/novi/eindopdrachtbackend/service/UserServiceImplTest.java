package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User johnDoe;
    private User janeDoe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        johnDoe = new User("John Doe", "john.doe@example.com", "password123", "USER", "1234 Main St", "555-1234");
        janeDoe = new User("Jane Doe", "jane.doe@example.com", "securePassword", "ADMIN", "5678 Market St", "555-5678");
    }

    @Test
    void createUser_ReturnsCreatedUser() {
        // Preparation
        when(userRepository.save(any(User.class))).thenReturn(johnDoe);

        // Action
        User createdUser = userService.createUser(johnDoe);

        // Verification
        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_ReturnsUpdatedUser() {
        // Preparation
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(johnDoe));
        when(userRepository.save(any(User.class))).thenReturn(johnDoe);

        johnDoe.setEmail("new.email@example.com");
        // Action
        User updatedUser = userService.updateUser(1L, johnDoe);

        // Verification
        assertNotNull(updatedUser);
        assertEquals("new.email@example.com", updatedUser.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUser_ThrowsResourceNotFoundException() {
        // Preparation
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, new User()));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void getAllUsers_ReturnsListOfUsers() {
        // Preparation
        when(userRepository.findAll()).thenReturn(Arrays.asList(johnDoe, janeDoe));

        // Action
        List<User> users = userService.getAllUsers();

        // Verification
        assertNotNull(users);
        assertEquals(2, users.size());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_ReturnsUser() {
        // Preparation
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(johnDoe));

        // Action
        User foundUser = userService.getUserById(1L);

        // Verification
        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        verify(userRepository).findById(anyLong());
    }

    @Test
    void getUserById_ThrowsResourceNotFoundException() {
        // Preparation
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));

        verify(userRepository).findById(anyLong());
    }

    @Test
    void deleteUser_DeletesUser() {
        // Preparation
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(johnDoe));

        // Action
        userService.deleteUser(1L);

        // Verification
        verify(userRepository).delete(johnDoe);
    }

    @Test
    void deleteUser_ThrowsResourceNotFoundExceptionWhenNotFound() {
        // Preparation
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));

        verify(userRepository).findById(anyLong());
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void findByNameIgnoreCase_ReturnsListOfUsers() {
        // Preparation
        when(userRepository.findByNameIgnoreCase("doe")).thenReturn(Arrays.asList(johnDoe, janeDoe));

        // Action
        List<User> result = userService.findByNameIgnoreCase("doe");

        // Verification
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(user -> "John Doe".equals(user.getName())));
        assertTrue(result.stream().anyMatch(user -> "Jane Doe".equals(user.getName())));
        verify(userRepository).findByNameIgnoreCase("doe");
    }

    @Test
    void findByNameIgnoreCase_ThrowsResourceNotFoundExceptionWhenNotFound() {
        // Preparation
        when(userRepository.findByNameIgnoreCase("unknown")).thenReturn(Collections.emptyList());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> userService.findByNameIgnoreCase("unknown"));

        verify(userRepository).findByNameIgnoreCase("unknown");
    }

}
