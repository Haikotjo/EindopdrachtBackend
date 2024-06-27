package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.model.UserRole;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    UserDTO getUserByIdForAdmin(Long id);
    UserDTO createAdmin(UserInputDTO userInputDTO);
    UserDTO createCustomer(UserInputDTO userInputDTO);
    UserDTO createOwner(UserInputDTO userInputDTO);
    UserDTO updateUser(Long id, UserInputDTO userInputDTO);
    UserDTO updateUserForAdmin(Long id, UserInputDTO userInputDTO);
    void deleteUser(Long id);
    void deleteUserForAdmin(Long id);
    UserDTO findByEmail(String email);
    List<UserDTO> findByRole(UserRole role);
    DeliveryAddressDTO getAddressByUserId(Long userId);
    RestaurantDTO getRestaurantsByUserId(Long userId);
    UserDTO updateUserRole(Long id, UserRoleUpdateDTO userRoleUpdateDTO);
}

