package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.service.DeliveryAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/address")
public class DeliveryAddressController {

    @Autowired
    private DeliveryAddressService deliveryAddressService;

    @PutMapping
    public ResponseEntity<DeliveryAddressDTO> updateOrCreateDeliveryAddress(@PathVariable Long userId, @RequestBody DeliveryAddressInputDTO addressDTO) {
        DeliveryAddressDTO updatedAddress = deliveryAddressService.updateOrCreateDeliveryAddress(userId, addressDTO);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }
}
