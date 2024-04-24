package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeliveryAddressRepository deliveryAddressRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserInputDTO userInputDTO;
    private DeliveryAddressInputDTO addressInputDTO;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        user = new User("John Doe", "john.doe@example.com", "password123", UserRole.CUSTOMER, "555-1234");

        // Reflectively setting the ID
        Field field = User.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(user, 1L);

        DeliveryAddress address = new DeliveryAddress();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setCountry("USA");
        address.setPostcode("12345");
        address.setHouseNumber(456);
        user.setDeliveryAddress(address);

        userInputDTO = new UserInputDTO();
        userInputDTO.setName("Jane Doe");
        userInputDTO.setEmail("jane.doe@example.com");
        userInputDTO.setPassword("securepassword");
        userInputDTO.setRole(UserRole.OWNER);
        userInputDTO.setPhoneNumber("555-6789");
        userInputDTO.setDeliveryAddress(addressInputDTO);

        addressInputDTO = new DeliveryAddressInputDTO();
        addressInputDTO.setStreet("123 Main St");
        addressInputDTO.setHouseNumber(456);
        addressInputDTO.setCity("Anytown");
        addressInputDTO.setPostcode("12345");
        addressInputDTO.setCountry("USA");
    }


    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<UserDTO> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void getUserByIdTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO result = userService.getUserById(1L);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void createUserTest() {
        User newUser = new User();
        newUser.setName(userInputDTO.getName()); // Verifying this will set correctly in UserMapper
        newUser.setEmail(userInputDTO.getEmail());
        newUser.setRole(userInputDTO.getRole());
        newUser.setPhoneNumber(userInputDTO.getPhoneNumber());
        newUser.setDeliveryAddress(new DeliveryAddress()); // Simplified for test

        when(userRepository.save(any(User.class))).thenReturn(newUser);
        UserDTO result = userService.createUser(userInputDTO);
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void updateUserTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO result = userService.updateUser(1L, userInputDTO);
        assertEquals("Jane Doe", result.getName());
    }

    @Test
    void deleteUserTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void findByNameIgnoreCaseTest() {
        when(userRepository.findByNameIgnoreCase("john")).thenReturn(Arrays.asList(user));
        List<UserDTO> result = userService.findByNameIgnoreCase("john");
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void findByRoleTest() {
        when(userRepository.findByRole(UserRole.CUSTOMER)).thenReturn(Arrays.asList(user));
        List<UserDTO> result = userService.findByRole(UserRole.CUSTOMER);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }
    @Test
    void getAddressByUserId_ShouldReturnAddress_WhenUserHasAddress() throws NoSuchFieldException, IllegalAccessException {
        User user = new User("John Doe", "john@example.com", "password", UserRole.CUSTOMER, "555-1234");

        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, 1L);

        DeliveryAddress address = new DeliveryAddress();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setCountry("USA");
        address.setPostcode("12345");
        address.setHouseNumber(456);

        user.setDeliveryAddress(address);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        DeliveryAddressDTO result = userService.getAddressByUserId(1L);

        assertNotNull(result, "Address should not be null");
        assertEquals("123 Main St", result.getStreet());
    }

    @Test
    void getAddressByUserId_ShouldThrowException_WhenUserHasNoAddress() throws NoSuchFieldException, IllegalAccessException {
        User user = new User("John Doe", "john@example.com", "password", UserRole.CUSTOMER, "555-1234");

        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, 1L);

        user.setDeliveryAddress(null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(ResourceNotFoundException.class, () -> userService.getAddressByUserId(1L),
                "Should throw exception when address is null");
    }


}
