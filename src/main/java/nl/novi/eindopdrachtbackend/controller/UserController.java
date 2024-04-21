package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.UserDTO;
import nl.novi.eindopdrachtbackend.dto.UserInputDTO;
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

    // Verwijder een gebruiker
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
