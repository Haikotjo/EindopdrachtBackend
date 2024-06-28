package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.model.UserRole;
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

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get user by ID with full information (ADMIN only)
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> getUserByIdForAdmin(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserByIdForAdmin(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Get user by ID with basic information
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER', 'CUSTOMER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Post new ADMIN user (ADMIN only)
    @PostMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createAdmin(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Post new CUSTOMER user
    @PostMapping("/customer")
    public ResponseEntity<UserDTO> createCustomer(@RequestBody UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createCustomer(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Post new OWNER user
    @PostMapping("/owner")
    public ResponseEntity<UserDTO> createOwner(@RequestBody UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createOwner(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Update user for own id
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER', 'CUSTOMER')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserInputDTO userInputDTO) {
        UserDTO updatedUser = userService.updateUser(id, userInputDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Update user for all id's (ADMIN only)
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUserForAdmin(@PathVariable Long id, @RequestBody UserInputDTO userInputDTO) {
        UserDTO updatedUser = userService.updateUserForAdmin(id, userInputDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Update user role for all id's (ADMIN only)
    @PutMapping("/{id}/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long id, @RequestBody UserRoleUpdateDTO userRoleUpdateDTO) {
        UserDTO updatedUser = userService.updateUserRole(id, userRoleUpdateDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Delete user for own id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER', 'CUSTOMER')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete user for all id's (ADMIN only)
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUserForAdmin(@PathVariable Long id) {
        userService.deleteUserForAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Search for user by email (ADMIN only)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/search/by-email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        UserDTO user = userService.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Search by role (ADMIN only)
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/search/by-role")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@RequestParam UserRole role) {
        List<UserDTO> users = userService.findByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
