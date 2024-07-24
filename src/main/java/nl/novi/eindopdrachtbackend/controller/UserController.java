package nl.novi.eindopdrachtbackend.controller;

import jakarta.validation.Valid;
import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Get all Users (Admin only)
     *
     * @return ResponseEntity containing a list of UserDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Get user by ID with full information (ADMIN only)
     *
     * @param id the ID of the user
     * @return ResponseEntity containing the UserDTO object for the specified ID
     */
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> getUserByIdForAdmin(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserByIdForAdmin(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * Get user by ID with basic information for authenticated users
     *
     * @return ResponseEntity containing the UserDTO object for the authenticated user
     */
    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER', 'CUSTOMER')")
    public ResponseEntity<UserDTO> getUserProfile() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        try {
            UserDTO userDTO = userService.getUserById(currentUserEmail);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Post new ADMIN user (ADMIN only)
     *
     * @param userInputDTO the user input data transfer object
     * @return ResponseEntity containing the created UserDTO object
     */
    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody @Valid UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createAdmin(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Post new CUSTOMER user (public access)
     *
     * @param userInputDTO the user input data transfer object
     * @return ResponseEntity containing the created UserDTO object
     */
    @PostMapping("/customer")
    public ResponseEntity<UserDTO> createCustomer(@RequestBody @Valid UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createCustomer(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Post new OWNER user (public access)
     *
     * @param userInputDTO the user input data transfer object
     * @return ResponseEntity containing the created UserDTO object
     */
    @PostMapping("/owner")
    public ResponseEntity<UserDTO> createOwner(@RequestBody @Valid UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createOwner(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Update user details for the current authenticated user
     *
     * @param userInputDTO the new user details
     * @return ResponseEntity containing the updated UserDTO object
     */
    @PutMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER', 'CUSTOMER')")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserInputDTO userInputDTO) {
        UserDTO updatedUser = userService.updateUser(userInputDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    /**
     * Update user for anny id (ADMIN only)
     *
     * @param id the ID of the user
     * @param userInputDTO the new user details
     * @return ResponseEntity containing the updated UserDTO object
     */
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUserForAdmin(@PathVariable Long id, @RequestBody @Valid UserInputDTO userInputDTO) {
        UserDTO updatedUser = userService.updateUserForAdmin(id, userInputDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Update user role for anny id (ADMIN only)
     *
     * @param id the ID of the user
     * @param userRoleUpdateDTO the new user role details
     * @return ResponseEntity containing the updated UserDTO object
     */
    @PutMapping("/role/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long id, @RequestBody @Valid UserRoleUpdateDTO userRoleUpdateDTO) {
        UserDTO updatedUser = userService.updateUserRole(id, userRoleUpdateDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Delete the currently authenticated user.
     *
     * @return ResponseEntity with status
     */
    @DeleteMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER', 'CUSTOMER')")
    public ResponseEntity<Void> deleteCurrentUser() {
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete user for anny id (ADMIN only)
     *
     * @param id the ID of the user to delete
     * @return ResponseEntity with status
     */
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUserForAdmin(@PathVariable Long id) {
        userService.deleteUserForAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Search for user by email (ADMIN only)
     *
     * @param email user email
     * @return ResponseEntity containing the UserDTO object for the specified email
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/search/by-email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        UserDTO user = userService.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Search by role (ADMIN only)
     *
     * @param role the role of the users
     * @return ResponseEntity containing a list of UserDTO objects matching the given role
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/search/by-role")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@RequestParam UserRole role) {
        List<UserDTO> users = userService.findByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Retrieve the currently authenticated user from the security context.
     *
     * This method fetches the email of the currently authenticated user from the security context,
     * retrieves the corresponding User entity from the user repository, and returns the User object.
     * If the user is not found, it throws a ResourceNotFoundException.
     *
     * @return the User object representing the currently authenticated user
     * @throws ResourceNotFoundException if no user is found with the current authenticated email
     */
    private User getCurrentUser() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
        System.out.println("Current User Email: " + currentUserEmail);
        System.out.println("Current User Roles: " + user.getRoles());
        return user;
    }
}
