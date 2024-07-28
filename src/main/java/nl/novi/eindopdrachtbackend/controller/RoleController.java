package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.repository.RoleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RoleController is responsible for handling requests related to roles.
 * It provides an endpoint to retrieve all roles from the repository.
 */
@RestController
public class RoleController {

    private final RoleRepository roleRepository;

    /**
     * Constructs a RoleController with the given RoleRepository.
     *
     * @param roleRepository the RoleRepository to use for accessing role data
     */
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Retrieves all roles from the repository.
     *
     * @return a list of all roles
     */
    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
