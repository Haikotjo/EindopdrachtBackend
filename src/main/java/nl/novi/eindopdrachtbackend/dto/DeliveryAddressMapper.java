package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;

public class DeliveryAddressMapper {

    public static DeliveryAddressDTO toDeliveryAddressDTO(DeliveryAddress address) {
        if (address == null) {
            return null;
        }
        DeliveryAddressDTO dto = new DeliveryAddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setHouseNumber(address.getHouseNumber());
        dto.setCity(address.getCity());
        dto.setPostcode(address.getPostcode());
        dto.setCountry(address.getCountry());
        dto.setUserId(address.getUser() != null ? address.getUser().getId() : null);
        return dto;
    }

    public static DeliveryAddress toDeliveryAddress(DeliveryAddressInputDTO inputDTO) {
        if (inputDTO == null) {
            return null;
        }
        DeliveryAddress address = new DeliveryAddress();
        address.setStreet(inputDTO.getStreet());
        address.setHouseNumber(inputDTO.getHouseNumber());
        address.setCity(inputDTO.getCity());
        address.setPostcode(inputDTO.getPostcode());
        address.setCountry(inputDTO.getCountry());
        return address;
    }
}
