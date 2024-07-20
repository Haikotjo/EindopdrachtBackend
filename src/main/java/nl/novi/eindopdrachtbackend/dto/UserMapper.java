package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.*;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

/**
 * Mapper class for converting between User entities and DTOs.
 */
public class UserMapper {

    /**
     * Converts a User entity to a basic UserDTO.
     *
     * @param user the User entity
     * @return the basic UserDTO
     */
    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    /**
     * Converts a User entity to a detailed UserDTO.
     *
     * @param user the User entity
     * @return the detailed UserDTO
     */
    public static UserDTO toFullUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles().stream().map(role -> role.getRolename().name()).collect(Collectors.toList()));
        dto.setPhoneNumber(user.getPhoneNumber());

        if (user.getDeliveryAddress() != null) {
            dto.setDeliveryAddress(DeliveryAddressMapper.toDeliveryAddressDTO(user.getDeliveryAddress()));
        }

        if (user.getRestaurant() != null) {
            dto.setRestaurant(RestaurantMapper.toRestaurantDTO(user.getRestaurant()));
        }

        if (user.getOrders() != null && !user.getOrders().isEmpty()) {
            dto.setOrders(user.getOrders().stream()
                    .map(OrderMapper::toOrderDTO)
                    .collect(Collectors.toList()));
        }

        if (user.getIngredients() != null) {
            dto.setIngredients(user.getIngredients().stream()
                    .map(IngredientMapper::toOwnerIngredientDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    /**
     * Converts a UserInputDTO to a User entity.
     *
     * @param inputDTO the UserInputDTO
     * @return the User entity
     */
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
