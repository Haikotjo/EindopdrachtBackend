package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
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
        dto.setDeliveryAddress(DeliveryAddressMapper.toDeliveryAddressDTO(user.getDeliveryAddress()));
//        dto.setOrders(OrderMapper.toOrderDTOList(user.getOrders()));
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
        return user;
    }
}
