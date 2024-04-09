package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.model.DeliveryAddress;

import java.util.List;

public interface DeliveryAddressService {
    List<DeliveryAddress> getAllAddresses();
    DeliveryAddress getAddressById(Long id);
    DeliveryAddress createAddress(DeliveryAddress address);
    DeliveryAddress updateAddress(Long id, DeliveryAddress addressDetails);
    void deleteAddress(Long id);
}
