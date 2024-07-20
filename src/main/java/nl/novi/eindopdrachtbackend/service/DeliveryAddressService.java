package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;

import java.util.List;

public interface DeliveryAddressService {

    /**
     * Get all delivery addresses (Admin only).
     *
     * @return list of all DeliveryAddressDTOs
     */
    List<DeliveryAddressDTO> getAllDeliveryAddresses();

    /**
     * Get delivery address by ID for admin.
     *
     * @param id delivery address ID
     * @return DeliveryAddressDTO for the specified ID
     */
    DeliveryAddressDTO getDeliveryAddressByIdForAdmin(Long id);

    /**
     * Get delivery address for the logged-in customer.
     *
     * @param userId user ID of the logged-in customer
     * @return DeliveryAddressDTO for the specified user
     */
    DeliveryAddressDTO getDeliveryAddressForCustomer(Long userId);

    /**
     * Create a new delivery address for a specific user (Admin only).
     *
     * @param userId user ID
     * @param addressDTO delivery address data
     * @return created DeliveryAddressDTO
     */
    DeliveryAddressDTO createDeliveryAddressForUser(Long userId, DeliveryAddressInputDTO addressDTO);

    /**
     * Create a new delivery address for the logged-in customer.
     *
     * @param userId user ID of the logged-in customer
     * @param addressDTO delivery address data
     * @return created DeliveryAddressDTO
     */
    DeliveryAddressDTO createDeliveryAddressForCustomer(Long userId, DeliveryAddressInputDTO addressDTO);

    /**
     * Update delivery address for a specific user (Admin only).
     *
     * @param id delivery address ID
     * @param addressDTO the new delivery address data
     * @return the updated DeliveryAddressDTO
     * @throws ResourceNotFoundException if the delivery address is not found
     */
    DeliveryAddressDTO updateDeliveryAddressForAdmin(Long id, DeliveryAddressInputDTO addressDTO);

    /**
     * Update delivery address for the logged-in customer.
     *
     * @param userId user ID of the logged-in customer
     * @param addressDTO the new delivery address data
     * @return the updated DeliveryAddressDTO
     * @throws ResourceNotFoundException if the delivery address is not found
     */
    DeliveryAddressDTO updateDeliveryAddressForCustomer(Long userId, DeliveryAddressInputDTO addressDTO);

    /**
     * Delete delivery address by ID (Admin only).
     *
     * @param id the ID of the delivery address to delete
     */
    void deleteDeliveryAddressForAdmin(Long id);

    /**
     * Delete delivery address for the logged-in customer.
     *
     * @param userId user ID of the logged-in customer
     */
    void deleteDeliveryAddressForCustomer(Long userId);
}
