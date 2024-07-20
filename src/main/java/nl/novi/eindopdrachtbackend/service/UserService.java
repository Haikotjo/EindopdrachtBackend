package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
import nl.novi.eindopdrachtbackend.dto.UserRoleUpdateDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.UserRole;

import java.util.List;

public interface UserService {

    /**
     * Get all users (Admin only).
     *
     * @return list of all UserDTOs
     */
    List<UserDTO> getAllUsers();

    /**
     * Get user by ID.
     *
     * @param id user ID
     * @return UserDTO for the specified ID
     */
    UserDTO getUserById(Long id);

    /**
     * Get user by ID for admin.
     *
     * @param id user ID
     * @return UserDTO for the specified ID
     */
    UserDTO getUserByIdForAdmin(Long id);

    /**
     * Create a new admin user.
     *
     * @param userInputDTO user input data
     * @return created UserDTO
     */
    UserDTO createAdmin(UserInputDTO userInputDTO);

    /**
     * Create a new customer user.
     *
     * @param userInputDTO user input data
     * @return created UserDTO
     */
    UserDTO createCustomer(UserInputDTO userInputDTO);

    /**
     * Create a new owner user.
     *
     * @param userInputDTO user input data
     * @return created UserDTO
     */
    UserDTO createOwner(UserInputDTO userInputDTO);

    /**
     * Update user details.
     *
     * @param id user ID
     * @param userInputDTO the new user details
     * @return the updated UserDTO
     * @throws ResourceNotFoundException if the user is not found
     */
    UserDTO updateUser(Long id, UserInputDTO userInputDTO);

    /**
     * Update user details for admin.
     *
     * @param id user ID
     * @param userInputDTO the new user details
     * @return the updated UserDTO
     * @throws ResourceNotFoundException if the user is not found
     */
    UserDTO updateUserForAdmin(Long id, UserInputDTO userInputDTO);

    /**
     * Delete a user.
     *
     * @param id the ID of the user to delete
     */
    void deleteUser(Long id);

    /**
     * Delete a user for admin.
     *
     * @param id the ID of the user to delete
     */
    void deleteUserForAdmin(Long id);

    /**
     * Find user by email.
     *
     * @param email user email
     * @return UserDTO for the specified email
     */
    UserDTO findByEmail(String email);

    /**
     * Find users by role.
     *
     * @param role the role of the users
     * @return a list of UserDTO objects matching the given role
     */
    List<UserDTO> findByRole(UserRole role);

    /**
     * Update user role.
     *
     * @param id the ID of the user
     * @param userRoleUpdateDTO the new user role details
     * @return the updated UserDTO
     */
    UserDTO updateUserRole(Long id, UserRoleUpdateDTO userRoleUpdateDTO);
}
