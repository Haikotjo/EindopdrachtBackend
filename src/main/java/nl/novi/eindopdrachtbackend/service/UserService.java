package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.model.UserRole;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO createUser(UserInputDTO userInputDTO);
    UserDTO updateUser(Long id, UserInputDTO userInputDTO);
    void deleteUser(Long id);
    List<UserDTO> findByNameIgnoreCase(String name);
    List<UserDTO> findByRole(UserRole role);
    DeliveryAddressDTO getAddressByUserId(Long userId);
}

