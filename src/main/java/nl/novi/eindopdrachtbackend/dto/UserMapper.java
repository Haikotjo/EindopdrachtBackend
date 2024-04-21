package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;

public class UserMapper {

    // Converteer User entiteit naar UserDTO
    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setAddress(user.getAddress());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    // Converteer UserInputDTO naar User entiteit
    public static User toUser(UserInputDTO inputDTO) {
        User user = new User();
        user.setName(inputDTO.getName());
        user.setEmail(inputDTO.getEmail());
        user.setPassword(inputDTO.getPassword());
        user.setRole(inputDTO.getRole());
        user.setAddress(inputDTO.getAddress());
        user.setPhoneNumber(inputDTO.getPhoneNumber());
        return user;
    }
}
