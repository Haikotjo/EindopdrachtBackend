package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;

public interface DeliveryAddressService {
    DeliveryAddressDTO updateOrCreateDeliveryAddress(Long userId, DeliveryAddressInputDTO addressDTO);
}
