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
    public UserDTO createUser(UserInputDTO userInputDTO) {
        User user = UserMapper.toUser(userInputDTO);
        User savedUser = userRepository.save(user);
        return UserMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserInputDTO userInputDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        user.setName(userInputDTO.getName());
        user.setEmail(userInputDTO.getEmail());
        user.setPassword(userInputDTO.getPassword());
        user.setRole(userInputDTO.getRole());
        user.setPhoneNumber(userInputDTO.getPhoneNumber());
        userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }

    @Transactional
    @Override
    public UserDTO updateUserAndAddress(Long userId, UserInputDTO userInputDTO, DeliveryAddressInputDTO addressInputDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        user.setName(userInputDTO.getName());
        user.setEmail(userInputDTO.getEmail());
        user.setPassword(userInputDTO.getPassword());
        user.setRole(userInputDTO.getRole());
        user.setPhoneNumber(userInputDTO.getPhoneNumber());

        DeliveryAddress address = user.getDeliveryAddress();
        if (address == null) {
            address = new DeliveryAddress();
            user.setDeliveryAddress(address);
        }
        address.setStreet(addressInputDTO.getStreet());
        address.setHouseNumber(addressInputDTO.getHouseNumber());
        address.setCity(addressInputDTO.getCity());
        address.setPostcode(addressInputDTO.getPostcode());
        address.setCountry(addressInputDTO.getCountry());
        userRepository.save(user);
        return UserMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
        return UserMapper.toUserDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> findByNameIgnoreCase(String name) {
        List<User> users = userRepository.findByNameIgnoreCase(name);
        return users.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findByRole(UserRole role) {
        List<User> users = userRepository.findByRole(role);  // Aangepast om UserRole te gebruiken
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No users found with role: " + role);
        }
        return users.stream().map(UserMapper::toUserDTO).collect(Collectors.toList());
    }
}
