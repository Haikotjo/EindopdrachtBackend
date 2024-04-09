package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public DeliveryAddressServiceImpl(DeliveryAddressRepository deliveryAddressRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    @Override
    public List<DeliveryAddress> getAllAddresses() {
        return deliveryAddressRepository.findAll();
    }

    @Override
    public DeliveryAddress getAddressById(Long id) {
        return deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DeliveryAddress not found for this id :: " + id));
    }

    @Override
    public DeliveryAddress createAddress(DeliveryAddress address) {
        return deliveryAddressRepository.save(address);
    }

    @Override
    public DeliveryAddress updateAddress(Long id, DeliveryAddress addressDetails) {
        DeliveryAddress address = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DeliveryAddress not found for this id :: " + id));

        address.setStreet(addressDetails.getStreet());
        address.setHouseNumber(addressDetails.getHouseNumber());
        address.setCity(addressDetails.getCity());
        address.setPostcode(addressDetails.getPostcode());
        address.setPostcodeNumber(addressDetails.getPostcodeNumber());
        address.setCountry(addressDetails.getCountry());

        return deliveryAddressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        DeliveryAddress address = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DeliveryAddress not found for this id :: " + id));
        deliveryAddressRepository.delete(address);
    }
}
