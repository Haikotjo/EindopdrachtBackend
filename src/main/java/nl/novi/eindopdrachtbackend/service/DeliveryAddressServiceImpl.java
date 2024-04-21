package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;

    public DeliveryAddressServiceImpl(DeliveryAddressRepository deliveryAddressRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    @Override
    public DeliveryAddressDTO createDeliveryAddress(DeliveryAddressInputDTO addressInputDTO) {
        DeliveryAddress address = DeliveryAddressMapper.toDeliveryAddress(addressInputDTO);
        DeliveryAddress savedAddress = deliveryAddressRepository.save(address);
        return DeliveryAddressMapper.toDeliveryAddressDTO(savedAddress);
    }

    @Transactional
    @Override
    public DeliveryAddressDTO updateDeliveryAddress(Long id, DeliveryAddressInputDTO addressInputDTO) {
        DeliveryAddress existingAddress = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found for this id :: " + id));
        existingAddress.setStreet(addressInputDTO.getStreet());
        existingAddress.setHouseNumber(addressInputDTO.getHouseNumber());
        existingAddress.setCity(addressInputDTO.getCity());
        existingAddress.setPostcode(addressInputDTO.getPostcode());
        existingAddress.setCountry(addressInputDTO.getCountry());
        return DeliveryAddressMapper.toDeliveryAddressDTO(existingAddress);
    }

    @Override
    public void deleteDeliveryAddress(Long id) {
        DeliveryAddress address = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found for this id :: " + id));
        deliveryAddressRepository.delete(address);
    }

    @Override
    public DeliveryAddressDTO getDeliveryAddressById(Long id) {
        DeliveryAddress address = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found for this id :: " + id));
        return DeliveryAddressMapper.toDeliveryAddressDTO(address);
    }

    @Override
    public List<DeliveryAddressDTO> getAllDeliveryAddresses() {
        List<DeliveryAddress> addresses = deliveryAddressRepository.findAll();
        return addresses.stream()
                .map(DeliveryAddressMapper::toDeliveryAddressDTO)
                .collect(Collectors.toList());
    }
}
