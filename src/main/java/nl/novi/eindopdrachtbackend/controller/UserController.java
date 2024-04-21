package nl.novi.eindopdrachtbackend.controller;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
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

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Haal alle gebruikers op
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Haal een specifieke gebruiker op met ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Maak een nieuwe gebruiker aan
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserInputDTO userInputDTO) {
        UserDTO newUser = userService.createUser(userInputDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Update een bestaande gebruiker
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserInputDTO userInputDTO) {
        UserDTO updatedUser = userService.updateUser(id, userInputDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Update gebruiker en zijn/haar adres
    @Transactional
    @PutMapping("/{id}/address")
    public ResponseEntity<UserDTO> updateUserAndAddress(@PathVariable Long id, @RequestBody UserInputDTO userInputDTO, @RequestBody DeliveryAddressInputDTO addressInputDTO) {
        UserDTO updatedUser = userService.updateUserAndAddress(id, userInputDTO, addressInputDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Verwijder een gebruiker
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Zoek gebruikers op naam, ongeacht hoofdletters of kleine letters
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> findByNameIgnoreCase(@RequestParam String name) {
        List<UserDTO> users = userService.findByNameIgnoreCase(name);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Zoek gebruikers op rol
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> findByRole(@PathVariable UserRole role) {
        List<UserDTO> users = userService.findByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}