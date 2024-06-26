package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.*;

import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class UserMapper {

    // Convert User entity to UserDTO
    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles().stream().map(role -> role.getRolename().name()).collect(Collectors.toList()));
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    // Convert UserInputDTO to User entity
    public static User toUser(UserInputDTO inputDTO) {
        List<Role> roles = new ArrayList<>();
        if (inputDTO.getRoles() != null) {
            for (String roleName : inputDTO.getRoles()) {
                Role role = new Role(UserRole.valueOf(roleName));
                roles.add(role);
            }
        }

        return new User(inputDTO.getName(), inputDTO.getEmail(), inputDTO.getPassword(), roles, inputDTO.getPhoneNumber());
    }
}
