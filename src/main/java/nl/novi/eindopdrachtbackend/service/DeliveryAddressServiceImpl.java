package nl.novi.eindopdrachtbackend.service;

import jakarta.transaction.Transactional;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import nl.novi.eindopdrachtbackend.repository.NotificationRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {


    private final UserRepository userRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public DeliveryAddressServiceImpl(DeliveryAddressRepository deliveryAddressRepository, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.deliveryAddressRepository = deliveryAddressRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DeliveryAddressDTO> getAllDeliveryAddresses() {
        return deliveryAddressRepository.findAll().stream()
                .map(DeliveryAddressMapper::toDeliveryAddressDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeliveryAddressDTO getDeliveryAddressByIdForAdmin(Long id) {
        try {
            DeliveryAddress address = deliveryAddressRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found for this id :: " + id));
            return DeliveryAddressMapper.toDeliveryAddressDTO(address);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve delivery address with id " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeliveryAddressDTO getDeliveryAddressForCustomer(Long userId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        DeliveryAddress address = currentUser.getDeliveryAddress();
        if (address == null) {
            throw new ResourceNotFoundException("No delivery address found for the current user.");
        }
        return DeliveryAddressMapper.toDeliveryAddressDTO(address);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeliveryAddressDTO createDeliveryAddressForUser(Long userId, DeliveryAddressInputDTO addressDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        DeliveryAddress address = new DeliveryAddress();
        address.setStreet(addressDTO.getStreet());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setCity(addressDTO.getCity());
        address.setPostcode(addressDTO.getPostcode());
        address.setCountry(addressDTO.getCountry());
        address.setUser(user);

        DeliveryAddress savedAddress = deliveryAddressRepository.save(address);
        return DeliveryAddressMapper.toDeliveryAddressDTO(savedAddress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeliveryAddressDTO createDeliveryAddressForCustomer(Long userId, DeliveryAddressInputDTO addressDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        DeliveryAddress address = new DeliveryAddress();
        address.setStreet(addressDTO.getStreet());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setCity(addressDTO.getCity());
        address.setPostcode(addressDTO.getPostcode());
        address.setCountry(addressDTO.getCountry());
        address.setUser(user);

        DeliveryAddress savedAddress = deliveryAddressRepository.save(address);
        return DeliveryAddressMapper.toDeliveryAddressDTO(savedAddress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeliveryAddressDTO updateDeliveryAddressForAdmin(Long id, DeliveryAddressInputDTO addressDTO) {
        DeliveryAddress address = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found for this id :: " + id));

        address.setStreet(addressDTO.getStreet());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setCity(addressDTO.getCity());
        address.setPostcode(addressDTO.getPostcode());
        address.setCountry(addressDTO.getCountry());

        DeliveryAddress updatedAddress = deliveryAddressRepository.save(address);
        return DeliveryAddressMapper.toDeliveryAddressDTO(updatedAddress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DeliveryAddressDTO updateDeliveryAddressForCustomer(Long userId, DeliveryAddressInputDTO addressDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        DeliveryAddress address = user.getDeliveryAddress();
        if (address == null) {
            throw new ResourceNotFoundException("No delivery address found for the current user.");
        }

        address.setStreet(addressDTO.getStreet());
        address.setHouseNumber(addressDTO.getHouseNumber());
        address.setCity(addressDTO.getCity());
        address.setPostcode(addressDTO.getPostcode());
        address.setCountry(addressDTO.getCountry());

        DeliveryAddress updatedAddress = deliveryAddressRepository.save(address);
        return DeliveryAddressMapper.toDeliveryAddressDTO(updatedAddress);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void deleteDeliveryAddressForAdmin(Long id) {
        DeliveryAddress address = deliveryAddressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery address not found for this id :: " + id));

        User user = address.getUser();

        // Remove references to the user in notifications, if applicable
        notificationRepository.findByUser(user).forEach(notification -> {
            notification.setUser(null);
            notificationRepository.save(notification);
        });

        deliveryAddressRepository.delete(address);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    public void deleteDeliveryAddressForCustomer(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        DeliveryAddress address = deliveryAddressRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No delivery address found for the current user."));

        // Remove references to the user in notifications, if applicable
        notificationRepository.findByUser(user).forEach(notification -> {
            notification.setUser(null);
            notificationRepository.save(notification);
        });

        deliveryAddressRepository.delete(address);
    }

}
