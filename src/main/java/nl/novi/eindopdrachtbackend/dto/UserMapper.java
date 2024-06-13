package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.Role;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;

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
        dto.setDeliveryAddress(DeliveryAddressMapper.toDeliveryAddressDTO(user.getDeliveryAddress()));
        dto.setOrders(user.getOrders().stream().map(OrderMapper::toDTO).collect(Collectors.toList()));
        return dto;
    }

    // Convert UserInputDTO to User entity
    public static User toUser(UserInputDTO inputDTO) {
        User user = new User();
        user.setName(inputDTO.getName());
        user.setEmail(inputDTO.getEmail());
        user.setPassword(inputDTO.getPassword());
        user.setPhoneNumber(inputDTO.getPhoneNumber());

        if (inputDTO.getDeliveryAddress() != null) {
            DeliveryAddress address = new DeliveryAddress();
            address.setStreet(inputDTO.getDeliveryAddress().getStreet());
            address.setHouseNumber(inputDTO.getDeliveryAddress().getHouseNumber());
            address.setCity(inputDTO.getDeliveryAddress().getCity());
            address.setPostcode(inputDTO.getDeliveryAddress().getPostcode());
            address.setCountry(inputDTO.getDeliveryAddress().getCountry());
            user.setDeliveryAddress(address);
        }

        List<Role> roles = new ArrayList<>();
        if (inputDTO.getRoles() != null) {
            for (String roleName : inputDTO.getRoles()) {
                Role role = new Role(UserRole.valueOf(roleName));
                roles.add(role);
            }
        }
        user.setRoles(roles);
        return user;
    }
}
