package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.dto.UserMapper;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


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
        user = userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserInputDTO userInputDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        existingUser.setName(userInputDTO.getName());
        existingUser.setEmail(userInputDTO.getEmail());
        existingUser.setRole(userInputDTO.getRole());
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

}
