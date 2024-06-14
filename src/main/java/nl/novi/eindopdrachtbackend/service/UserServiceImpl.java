package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.model.*;
import nl.novi.eindopdrachtbackend.repository.*;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserMapper.toUserDTO(user);
    }

    @Override
    public UserDTO createUser(UserInputDTO userInputDTO) {
        User user = UserMapper.toUser(userInputDTO);
        List<Role> roles = userInputDTO.getRoles().stream()
                .map(roleRepository::findById)
                .map(role -> role.orElseThrow(() -> new ResourceNotFoundException("Role not found")))
                .collect(Collectors.toList());
        user.setRoles(roles);
        user = userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserInputDTO userInputDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        existingUser.setName(userInputDTO.getName());
        existingUser.setEmail(userInputDTO.getEmail());
        existingUser.setPassword(userInputDTO.getPassword());
        List<Role> roles = userInputDTO.getRoles().stream()
                .map(roleRepository::findById)
                .map(role -> role.orElseThrow(() -> new ResourceNotFoundException("Role not found")))
                .collect(Collectors.toList());
        existingUser.setRoles(roles);
        existingUser.setPhoneNumber(userInputDTO.getPhoneNumber());
        existingUser = userRepository.save(existingUser);
        return UserMapper.toUserDTO(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(existingUser);
    }

    @Override
    public List<UserDTO> findByNameIgnoreCase(String name) {
        return userRepository.findByNameIgnoreCase(name).stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByRole(UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryAddressDTO getAddressByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        DeliveryAddress address = user.getDeliveryAddress();
        if (address == null) {
            throw new ResourceNotFoundException("Address not found for user id: " + userId);
        }
        return DeliveryAddressMapper.toDeliveryAddressDTO(address);
    }
}
