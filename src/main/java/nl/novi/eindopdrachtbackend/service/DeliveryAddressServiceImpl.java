package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressMapper;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @Override
    @Transactional
    public DeliveryAddressDTO updateOrCreateDeliveryAddress(Long userId, DeliveryAddressInputDTO addressDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        DeliveryAddress address = user.getDeliveryAddress();
        if (address == null) {
            address = new DeliveryAddress();
            user.setDeliveryAddress(address);
            address.setUser(user);
        }

        address.setStreet(addressDTO.getStreet());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setCity(addressDTO.getCity());
        address.setPostcode(addressDTO.getPostcode());
        address.setCountry(addressDTO.getCountry());

        deliveryAddressRepository.save(address);
        return DeliveryAddressMapper.toDeliveryAddressDTO(address);
    }
}
