package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
import nl.novi.eindopdrachtbackend.dto.UserMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserInputDTO userInputDTO) {
        User user = UserMapper.toUser(userInputDTO);
        // Hier kun je controleren of essentiële velden zoals rol niet null zijn
        if (user.getRole() == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }
        // Je zou hier een adres kunnen toewijzen als dat al in de UserInputDTO zit
        if (userInputDTO.getAddress() != null) {
            // Voeg logica toe om het adres te verwerken of te valideren
            DeliveryAddress address = new DeliveryAddress(); // Hypothetische Address class
            address.setDetails(userInputDTO.getAddress());
            user.setAddress(address);
        }
        User savedUser = userRepository.save(user);
        return UserMapper.toUserDTO(savedUser);
    }


    private void validateUserRole(UserRole role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        // Verdere validatie...
    }

    private void setDefaultPreferences(User user) {
        // Stel default voorkeuren in
    }


    @Transactional
    @Override
    public UserDTO updateUser(Long id, UserInputDTO userInputDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

        existingUser.setName(userInputDTO.getName());
        existingUser.setEmail(userInputDTO.getEmail());
        existingUser.setRole(userInputDTO.getRole());
        existingUser.setAddress(userInputDTO.getAddress());
        existingUser.setPhoneNumber(userInputDTO.getPhoneNumber());

        userRepository.save(existingUser);
        return UserMapper.toUserDTO(existingUser);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        return UserMapper.toUserDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        userRepository.delete(user);
    }

    @Override
    public List<UserDTO> findByNameIgnoreCase(String name) {
        List<User> users = userRepository.findByNameIgnoreCase(name);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("User not found with name: " + name);
        }
        return users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByRole(UserRole role) {
        List<User> users = userRepository.findByRole(role);  // Zorg dat je repository de UserRole enum ondersteunt
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with role: " + role.toString());
        }
        return users.stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }
}
