package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldSaveUser() {
        // Arrange
        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setName("John Doe");
        userInputDTO.setEmail("john.doe@example.com");
        userInputDTO.setPassword("strongpassword");
        userInputDTO.setRole(UserRole.CUSTOMER);
        userInputDTO.setPhoneNumber("123-456-7890");

        User userToBeSaved = new User();
        userToBeSaved.setName(userInputDTO.getName());
        userToBeSaved.setEmail(userInputDTO.getEmail());
        userToBeSaved.setPassword(userInputDTO.getPassword());
        userToBeSaved.setRole(userInputDTO.getRole());
        userToBeSaved.setPhoneNumber(userInputDTO.getPhoneNumber());

        when(userRepository.save(any(User.class))).thenReturn(userToBeSaved);

        // Act
        UserDTO result = userService.createUser(userInputDTO);

        // Assert
        assertNotNull(result);
        assertEquals(userToBeSaved.getName(), result.getName());
        assertEquals(userToBeSaved.getEmail(), result.getEmail());
        assertEquals(userToBeSaved.getPhoneNumber(), result.getPhoneNumber());
        verify(userRepository).save(any(User.class));
    }



    @Test
    void updateUser_ShouldUpdateUser() {
        // Arrange
        User existingUser = new User();
        existingUser.setName("John Doe");

        UserInputDTO userInputDTO = new UserInputDTO();
        userInputDTO.setName("Jane Doe");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        UserDTO result = userService.updateUser(1L, userInputDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        verify(userRepository).findById(1L);
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUserAndAddress_ShouldUpdateUserAndAddress() {
        // Arrange
        User existingUser = new User();
        DeliveryAddress address = new DeliveryAddress();
        existingUser.setDeliveryAddress(address);

        UserInputDTO userInputDTO = new UserInputDTO();
        DeliveryAddressInputDTO addressInputDTO = new DeliveryAddressInputDTO();
        addressInputDTO.setStreet("New Street");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        UserDTO result = userService.updateUserAndAddress(1L, userInputDTO, addressInputDTO);

        // Assert
        assertNotNull(result);
        assertEquals("New Street", existingUser.getDeliveryAddress().getStreet());
        verify(userRepository).findById(1L);
        verify(userRepository).save(existingUser);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        when(userRepository.findAll()).thenReturn(Arrays.asList(new User()));

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertFalse(result.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser() {
        // Arrange
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setRole(UserRole.CUSTOMER);
        user.setPhoneNumber("555-1234");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserDTO result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getPhoneNumber(), result.getPhoneNumber());
        verify(userRepository).findById(1L);
    }


    @Test
    void deleteUser_ShouldInvokeDelete() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository).deleteById(1L);
    }
}
