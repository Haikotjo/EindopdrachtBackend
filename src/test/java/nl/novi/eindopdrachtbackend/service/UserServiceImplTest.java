package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
import nl.novi.eindopdrachtbackend.dto.UserMapper;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDTO userDTO;
    private UserInputDTO userInputDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setRole(UserRole.CUSTOMER);
        userDTO.setAddress("123 Main St");
        userDTO.setPhoneNumber("555-1234");

        userInputDTO = new UserInputDTO();
        userInputDTO.setName("Jane Doe");
        userInputDTO.setEmail("jane.doe@example.com");
        userInputDTO.setPassword("newPassword123");
        userInputDTO.setRole(UserRole.OWNER);
        userInputDTO.setAddress("124 Main St");
        userInputDTO.setPhoneNumber("555-5678");
    }

    @Test
    void createUser_ShouldSaveUser() {
        // Arrange
        UserInputDTO newUserDTO = new UserInputDTO();
        newUserDTO.setName("Billy Bane");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDTO result = userService.createUser(userInputDTO);

        // Assert
        assertNotNull(result);
        assertEquals(userInputDTO.getName(), result.getName());
        verify(userRepository).save(user);
    }


//    @Test
//    void updateUser_ShouldUpdateAndReturnUser() {
//        User existingUser = new User();
//        existingUser.setId(1L);
//        existingUser.setName("John Doe");
//        existingUser.setEmail("john@example.com");
//        existingUser.setPassword("password123");
//        existingUser.setRole(UserRole.CUSTOMER);
//        existingUser.setAddress("123 Main St");
//        existingUser.setPhoneNumber("555-1234");
//
//        UserInputDTO userInputDTO = new UserInputDTO();
//        userInputDTO.setName("Jane Doe");
//        userInputDTO.setEmail("jane@example.com");
//        userInputDTO.setPassword("newPassword123");
//        userInputDTO.setRole(UserRole.OWNER);
//        userInputDTO.setAddress("124 Main St");
//        userInputDTO.setPhoneNumber("555-5678");
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
//        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        UserDTO result = userService.updateUser(1L, userInputDTO);
//
//        assertNotNull(result);
//        assertEquals(userInputDTO.getName(), result.getName());
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    void getAllUsers_ShouldReturnListOfUserDTO() {
//        List<User> users = Arrays.asList(
//                new User(1L, "John Doe", "john@example.com", UserRole.CUSTOMER, "123 Main St", "555-1234", null, null),
//                new User(2L, "Jane Doe", "jane@example.com", UserRole.OWNER, "124 Main St", "555-5678", null, null)
//        );
//        when(userRepository.findAll()).thenReturn(users);
//
//        List<UserDTO> result = userService.getAllUsers();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals(users.get(0).getName(), result.get(0).getName());
//        assertEquals(users.get(1).getName(), result.get(1).getName());
//    }
//
//    @Test
//    void getUserById_ShouldReturnUserDTO() {
//        User user = new User(1L, "John Doe", "john@example.com", UserRole.CUSTOMER, "123 Main St", "555-1234", null, null);
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        UserDTO result = userService.getUserById(1L);
//
//        assertNotNull(result);
//        assertEquals(user.getName(), result.getName());
//    }
//
//    @Test
//    void deleteUser_ShouldInvokeRepositoryDelete() {
//        User user = new User(1L, "John Doe", "john@example.com", UserRole.CUSTOMER, "123 Main St", "555-1234", null, null);
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        userService.deleteUser(1L);
//
//        verify(userRepository).delete(user);
//    }
//
//    @Test
//    void findByNameIgnoreCase_ShouldReturnListOfUserDTO() {
//        List<User> users = Arrays.asList(
//                new User(1L, "john doe", "john@example.com", UserRole.CUSTOMER, "123 Main St", "555-1234", null, null)
//        );
//        when(userRepository.findByNameIgnoreCase("john doe")).thenReturn(users);
//
//        List<UserDTO> result = userService.findByNameIgnoreCase("john doe");
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals("john doe", result.get(0).getName());
//    }
//
//    @Test
//    void findByRole_ShouldReturnListOfUserDTO() {
//        List<User> users = Arrays.asList(
//                new User(1L, "John Doe", "john@example.com", UserRole.CUSTOMER, "123 Main St", "555-1234", null, null)
//        );
//        when(userRepository.findByRole(UserRole.CUSTOMER)).thenReturn(users);
//
//        List<UserDTO> result = userService.findByRole(UserRole.CUSTOMER);
//
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(UserRole.CUSTOMER, result.get(0).getRole());
//    }
}
