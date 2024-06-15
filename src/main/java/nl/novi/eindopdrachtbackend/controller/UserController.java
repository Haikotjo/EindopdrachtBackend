package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<UserDTO> createAdmin(@RequestBody UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createAdmin(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/customer")
    public ResponseEntity<UserDTO> createCustomer(@RequestBody UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createCustomer(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/owner")
    public ResponseEntity<UserDTO> createOwner(@RequestBody UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createOwner(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserInputDTO userInputDTO) {
        UserDTO updatedUser = userService.updateUser(id, userInputDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long id, @RequestBody UserRoleUpdateDTO userRoleUpdateDTO) {
        UserDTO updatedUser = userService.updateUserRole(id, userRoleUpdateDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search/by-name")
    public ResponseEntity<List<UserDTO>> getUsersByName(@RequestParam String name) {
        List<UserDTO> users = userService.findByNameIgnoreCase(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/search/by-role")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@RequestParam UserRole role) {
        List<UserDTO> users = userService.findByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}/address")
    public ResponseEntity<DeliveryAddressDTO> getAddressByUserId(@PathVariable Long userId) {
        DeliveryAddressDTO addressDTO = userService.getAddressByUserId(userId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }
}
