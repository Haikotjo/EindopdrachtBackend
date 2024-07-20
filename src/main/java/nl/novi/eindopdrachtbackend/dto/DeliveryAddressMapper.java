package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;

/**
 * Mapper class for converting between DeliveryAddress entities and DTOs.
 */
public class DeliveryAddressMapper {

    /**
     * Converts a DeliveryAddress entity to a DeliveryAddressDTO.
     *
     * @param address the DeliveryAddress entity
     * @return the DeliveryAddressDTO
     */
    public static DeliveryAddressDTO toDeliveryAddressDTO(DeliveryAddress address) {
        if (address == null) {
            return null;
        }
        return new DeliveryAddressDTO(
                address.getId(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getCity(),
                address.getPostcode(),
                address.getCountry(),
                address.getUser() != null ? address.getUser().getId() : null
        );
    }

    /**
     * Converts a DeliveryAddressInputDTO to a DeliveryAddress entity.
     *
     * @param inputDTO the DeliveryAddressInputDTO
     * @return the DeliveryAddress entity
     */
    public static DeliveryAddress toDeliveryAddress(DeliveryAddressInputDTO inputDTO) {
        if (inputDTO == null) {
            return null;
        }
        return new DeliveryAddress(
                inputDTO.getStreet(),
                inputDTO.getHouseNumber(),
                inputDTO.getCity(),
                inputDTO.getPostcode(),
                inputDTO.getCountry()
        );
    }
}
