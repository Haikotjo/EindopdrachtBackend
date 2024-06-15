package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.RoleRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
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
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserInputDTO userInputDTO;
    private DeliveryAddressInputDTO addressInputDTO;
    private UserRoleUpdateDTO userRoleUpdateDTO;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        user = new User("John Doe", "john.doe@example.com", "password123", new ArrayList<>(), "555-1234");

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
        userInputDTO.setRoles(Arrays.asList("OWNER"));
        userInputDTO.setPhoneNumber("555-6789");
        userInputDTO.setDeliveryAddress(addressInputDTO);

        addressInputDTO = new DeliveryAddressInputDTO();
        addressInputDTO.setStreet("123 Main St");
        addressInputDTO.setHouseNumber(456);
        addressInputDTO.setCity("Anytown");
        addressInputDTO.setPostcode("12345");
        addressInputDTO.setCountry("USA");

        userRoleUpdateDTO = new UserRoleUpdateDTO();
        userRoleUpdateDTO.setRole("OWNER");
    }

    @Test
    void getAllUsersTest() {
        user.setRoles(new ArrayList<>());  // Zorg ervoor dat de rollenlijst niet null is
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<UserDTO> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void getUserByIdTest() {
        user.setRoles(new ArrayList<>());  // Zorg ervoor dat de rollenlijst niet null is
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO result = userService.getUserById(1L);
        assertEquals("John Doe", result.getName());
    }

    @Test
    void createUserTest() {
        User newUser = new User();
        newUser.setName(userInputDTO.getName());
        newUser.setEmail(userInputDTO.getEmail());
        newUser.setPassword(userInputDTO.getPassword());
        newUser.setPhoneNumber(userInputDTO.getPhoneNumber());
        newUser.setDeliveryAddress(new DeliveryAddress()); // Simplified for test
        newUser.setRoles(new ArrayList<>());  // Zorg ervoor dat de rollenlijst niet null is

        Role role = new Role(UserRole.OWNER);
        when(roleRepository.findById(UserRole.OWNER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.getRoles().add(role);
            return savedUser;
        });

        UserDTO result = userService.createUser(userInputDTO);

        assertEquals("Jane Doe", result.getName());
        assertEquals("OWNER", result.getRoles().get(0));
    }

    @Test
    void updateUserTest() {
        user.setRoles(new ArrayList<>());  // Zorg ervoor dat de rollenlijst niet null is
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Role role = new Role(UserRole.OWNER);
        user.getRoles().add(role);  // Voeg de rol toe aan de gebruiker
        when(roleRepository.findById(UserRole.OWNER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.updateUser(1L, userInputDTO);

        assertEquals("Jane Doe", result.getName());
        assertEquals("OWNER", result.getRoles().get(0));
    }

    @Test
    void updateUserRoleTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Role role = new Role(UserRole.OWNER);
        when(roleRepository.findById(UserRole.OWNER)).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.updateUserRole(1L, userRoleUpdateDTO);

        assertEquals(1, result.getRoles().size());
        assertEquals("OWNER", result.getRoles().get(0));
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
        user.setRoles(new ArrayList<>());  // Zorg ervoor dat de rollenlijst niet null is
        when(userRepository.findByNameIgnoreCase("john")).thenReturn(Arrays.asList(user));
        List<UserDTO> result = userService.findByNameIgnoreCase("john");
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void findByRoleTest() {
        user.setRoles(new ArrayList<>());  // Zorg ervoor dat de rollenlijst niet null is
        when(userRepository.findByRole(UserRole.CUSTOMER)).thenReturn(Arrays.asList(user));
        List<UserDTO> result = userService.findByRole(UserRole.CUSTOMER);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void getAddressByUserId_ShouldReturnAddress_WhenUserHasAddress() throws NoSuchFieldException, IllegalAccessException {
        User user = new User("John Doe", "john@example.com", "password", new ArrayList<>(), "555-1234");

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
        user.setRoles(new ArrayList<>());  // Zorg ervoor dat de rollenlijst niet null is

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        DeliveryAddressDTO result = userService.getAddressByUserId(1L);

        assertNotNull(result, "Address should not be null");
        assertEquals("123 Main St", result.getStreet());
    }

    @Test
    void getAddressByUserId_ShouldThrowException_WhenUserHasNoAddress() throws NoSuchFieldException, IllegalAccessException {
        User user = new User("John Doe", "john@example.com", "password", new ArrayList<>(), "555-1234");

        Field idField = User.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(user, 1L);

        user.setDeliveryAddress(null);
        user.setRoles(new ArrayList<>());  // Zorg ervoor dat de rollenlijst niet null is

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(ResourceNotFoundException.class, () -> userService.getAddressByUserId(1L),
                "Should throw exception when address is null");
    }
}
