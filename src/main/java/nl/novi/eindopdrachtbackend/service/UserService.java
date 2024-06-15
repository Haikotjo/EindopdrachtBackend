package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.*;
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
    UserDTO updateUserRole(Long id, UserRoleUpdateDTO userRoleUpdateDTO);
}

