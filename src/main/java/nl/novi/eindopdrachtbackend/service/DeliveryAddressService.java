package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;

import java.util.List;

public interface DeliveryAddressService {
    DeliveryAddressDTO createDeliveryAddress(DeliveryAddressInputDTO addressInputDTO);
    DeliveryAddressDTO updateDeliveryAddress(Long id, DeliveryAddressInputDTO addressInputDTO);
    void deleteDeliveryAddress(Long id);
    DeliveryAddressDTO getDeliveryAddressById(Long id);
    List<DeliveryAddressDTO> getAllDeliveryAddresses();
}
