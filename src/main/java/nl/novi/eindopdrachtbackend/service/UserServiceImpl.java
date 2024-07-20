package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.exception.RoleNotFoundException;
import nl.novi.eindopdrachtbackend.model.*;
import nl.novi.eindopdrachtbackend.repository.*;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toFullUserDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO getUserByIdForAdmin(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserMapper.toFullUserDTO(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO getUserById(String email) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return UserMapper.toUserDTO(currentUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO createAdmin(UserInputDTO userInputDTO) {
        User user = UserMapper.toUser(userInputDTO);
        Role role = roleRepository.findById(UserRole.ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: ADMIN"));
        user.setRoles(List.of(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO createCustomer(UserInputDTO userInputDTO) {
        User user = UserMapper.toUser(userInputDTO);
        Role role = roleRepository.findById(UserRole.CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: CUSTOMER"));
        user.setRoles(List.of(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO createOwner(UserInputDTO userInputDTO) {
        User user = UserMapper.toUser(userInputDTO);
        Role role = roleRepository.findById(UserRole.OWNER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: OWNER"));
        user.setRoles(List.of(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO updateUser(Long id, UserInputDTO userInputDTO) {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("You do not have permission to update this user");
        }

        user.setName(userInputDTO.getName());
        user.setEmail(userInputDTO.getEmail());
        user.setPhoneNumber(userInputDTO.getPhoneNumber());
        user.setPassword(userInputDTO.getPassword()); // Assuming password is already encoded
        userRepository.save(user);

        return UserMapper.toUserDTO(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO updateUserForAdmin(Long id, UserInputDTO userInputDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        user.setName(userInputDTO.getName());
        user.setEmail(userInputDTO.getEmail());
        user.setPhoneNumber(userInputDTO.getPhoneNumber());
        user.setPassword(userInputDTO.getPassword()); // Assuming password is already encoded
        userRepository.save(user);

        return UserMapper.toUserDTO(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO updateUserRole(Long id, UserRoleUpdateDTO userRoleUpdateDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        UserRole newRoleEnum = UserRole.valueOf(userRoleUpdateDTO.getRole());
        Role newRole = roleRepository.findById(newRoleEnum)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + userRoleUpdateDTO.getRole()));
        existingUser.getRoles().clear();
        existingUser.getRoles().add(newRole);
        userRepository.save(existingUser);
        return UserMapper.toUserDTO(existingUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUser(Long id) {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        User currentUser = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Current user not found"));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (!user.getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("You do not have permission to delete this user");
        }

        userRepository.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUserForAdmin(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper::toUserDTO)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserDTO> findByRole(UserRole role) {
        List<UserDTO> users = userRepository.findByRole(role).stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new RoleNotFoundException("No users found with role: " + role);
        }

        return users;
    }
}
