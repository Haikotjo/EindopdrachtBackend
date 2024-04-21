package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.MenuDTO;
import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
import nl.novi.eindopdrachtbackend.model.UserRole;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserInputDTO userInputDTO);
    UserDTO updateUser(Long id, UserInputDTO userInputDTO);
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    void deleteUser(Long id);
    List<UserDTO> findByNameIgnoreCase(String name);
    List<UserDTO> findByRole(UserRole role);

}
