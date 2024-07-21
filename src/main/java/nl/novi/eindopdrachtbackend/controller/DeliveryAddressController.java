package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;
    private final UserRepository userRepository;

    @Autowired
    public DeliveryAddressController(DeliveryAddressService deliveryAddressService, UserRepository userRepository) {
        this.deliveryAddressService = deliveryAddressService;
        this.userRepository = userRepository;
    }

    /**
     * Get all delivery addresses (Admin only).
     *
     * @return ResponseEntity containing a list of DeliveryAddressDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<DeliveryAddressDTO>> getAllDeliveryAddresses() {
        List<DeliveryAddressDTO> addresses = deliveryAddressService.getAllDeliveryAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    /**
     * Endpoint to get a specific delivery address by its ID with full details (Admin only).
     *
     * @param id the ID of the delivery address
     * @return ResponseEntity containing the DeliveryAddressDTO object for the specified ID
     */
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeliveryAddressDTO> getDeliveryAddressByIdForAdmin(@PathVariable Long id) {
        try {
            DeliveryAddressDTO address = deliveryAddressService.getDeliveryAddressByIdForAdmin(id);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the delivery address for the logged-in customer.
     *
     * @return ResponseEntity containing the DeliveryAddressDTO object
     */
    @GetMapping("/customer")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<DeliveryAddressDTO> getDeliveryAddressForCustomer() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        try {
            User currentUser = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
            DeliveryAddressDTO address = deliveryAddressService.getDeliveryAddressForCustomer(currentUser.getId());
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new delivery address for a specific user by an admin.
     *
     * @param userId the ID of the user
     * @param addressDTO the delivery address data transfer object
     * @return ResponseEntity containing the created DeliveryAddressDTO object
     */
    @PostMapping("/admin/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeliveryAddressDTO> createDeliveryAddressForUserByAdmin(@PathVariable Long userId, @RequestBody DeliveryAddressInputDTO addressDTO) {
        DeliveryAddressDTO newAddress = deliveryAddressService.createDeliveryAddressForUser(userId, addressDTO);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    /**
     * Create a new delivery address for the logged-in customer.
     *
     * @param addressDTO the delivery address data transfer object
     * @return ResponseEntity containing the created DeliveryAddressDTO object
     */
    @PostMapping("/customer")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<DeliveryAddressDTO> createDeliveryAddressForCustomer(@RequestBody DeliveryAddressInputDTO addressDTO) {
        User currentUser = getCurrentUser();
        DeliveryAddressDTO newAddress = deliveryAddressService.createDeliveryAddressForCustomer(currentUser.getId(), addressDTO);
        return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
    }

    /**
     * Update delivery address for a specific user by an admin.
     *
     * @param id the ID of the delivery address to update
     * @param addressDTO the new delivery address details
     * @return ResponseEntity containing the updated DeliveryAddressDTO object
     */
    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<DeliveryAddressDTO> updateDeliveryAddressForAdmin(@PathVariable Long id, @RequestBody DeliveryAddressInputDTO addressDTO) {
        DeliveryAddressDTO updatedAddress = deliveryAddressService.updateDeliveryAddressForAdmin(id, addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    /**
     * Update the delivery address for the logged-in customer.
     *
     * @param addressDTO the new delivery address details
     * @return ResponseEntity containing the updated DeliveryAddressDTO object
     */
    @PutMapping("/customer")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<DeliveryAddressDTO> updateDeliveryAddressForCustomer(@RequestBody DeliveryAddressInputDTO addressDTO) {
        User currentUser = getCurrentUser();
        DeliveryAddressDTO updatedAddress = deliveryAddressService.updateDeliveryAddressForCustomer(currentUser.getId(), addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    /**
     * Delete delivery address by ID (Admin only).
     *
     * @param id the ID of the delivery address to delete
     * @return ResponseEntity with status
     */
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteDeliveryAddressForAdmin(@PathVariable Long id) {
        try {
            deliveryAddressService.deleteDeliveryAddressForAdmin(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete delivery address for the logged-in customer.
     *
     * @return ResponseEntity with status
     */
    @DeleteMapping("/customer")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Void> deleteDeliveryAddressForCustomer() {
        try {
            User currentUser = getCurrentUser();
            deliveryAddressService.deleteDeliveryAddressForCustomer(currentUser.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve the currently authenticated user from the security context.
     *
     * This method fetches the email of the currently authenticated user from the security context,
     * retrieves the corresponding User entity from the user repository, and returns the User object.
     * If the user is not found, it throws a ResourceNotFoundException.
     *
     * @return the User object representing the currently authenticated user
     * @throws ResourceNotFoundException if no user is found with the current authenticated email
     */
    private User getCurrentUser() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentUserEmail));
        System.out.println("Current User Email: " + currentUserEmail);
        System.out.println("Current User Roles: " + user.getRoles());
        return user;
    }
}
