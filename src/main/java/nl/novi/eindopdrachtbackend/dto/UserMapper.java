package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;

public class UserMapper {

    // Convert User entity to UserDTO
    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    // Convert UserInputDTO to User entity
    public static User toUser(UserInputDTO inputDTO) {
        User user = new User();
        user.setName(inputDTO.getName());
        user.setEmail(inputDTO.getEmail());
        user.setPassword(inputDTO.getPassword());
        user.setRole(inputDTO.getRole());
        user.setPhoneNumber(inputDTO.getPhoneNumber());
        return user;
    }
}
