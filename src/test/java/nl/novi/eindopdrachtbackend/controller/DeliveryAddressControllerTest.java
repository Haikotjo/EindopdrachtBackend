//package nl.novi.eindopdrachtbackend.controller;
//
//import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
//import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
//import nl.novi.eindopdrachtbackend.service.DeliveryAddressService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class DeliveryAddressControllerTest {
//
//    @Mock
//    private DeliveryAddressService deliveryAddressService;
//
//    @InjectMocks
//    private DeliveryAddressController deliveryAddressController;
//
//    @Test
//    void updateOrCreateDeliveryAddress_ShouldReturnUpdatedAddress() {
//        Long userId = 1L;
//        DeliveryAddressInputDTO addressDTO = new DeliveryAddressInputDTO();
//        addressDTO.setStreet("New Street 123");
//        addressDTO.setHouseNumber(789);
//        addressDTO.setCity("Newtown");
//        addressDTO.setPostcode("54321");
//        addressDTO.setCountry("Utopia");
//
//        DeliveryAddressDTO expectedAddressDTO = new DeliveryAddressDTO();
//        expectedAddressDTO.setStreet("New Street 123");
//        expectedAddressDTO.setHouseNumber(789);
//        expectedAddressDTO.setCity("Newtown");
//        expectedAddressDTO.setPostcode("54321");
//        expectedAddressDTO.setCountry("Utopia");
//
//        when(deliveryAddressService.updateOrCreateDeliveryAddress(userId, addressDTO)).thenReturn(expectedAddressDTO);
//
//        ResponseEntity<DeliveryAddressDTO> response = deliveryAddressController.updateOrCreateDeliveryAddress(userId, addressDTO);
//
//        assertNotNull(response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("New Street 123", response.getBody().getStreet());
//    }
//
//    @Test
//    void createNewDeliveryAddress_WhenNoneExists_ShouldReturnNewAddress() {
//        Long userId = 1L;
//        DeliveryAddressInputDTO addressDTO = new DeliveryAddressInputDTO();
//        addressDTO.setStreet("New Street 456");
//        addressDTO.setHouseNumber(101);
//        addressDTO.setCity("Uptown");
//        addressDTO.setPostcode("67890");
//        addressDTO.setCountry("Dreamland");
//
//        DeliveryAddressDTO expectedNewAddressDTO = new DeliveryAddressDTO();
//        expectedNewAddressDTO.setStreet("New Street 456");
//        expectedNewAddressDTO.setHouseNumber(101);
//        expectedNewAddressDTO.setCity("Uptown");
//        expectedNewAddressDTO.setPostcode("67890");
//        expectedNewAddressDTO.setCountry("Dreamland");
//
//        when(deliveryAddressService.updateOrCreateDeliveryAddress(userId, addressDTO)).thenReturn(expectedNewAddressDTO);
//
//        ResponseEntity<DeliveryAddressDTO> response = deliveryAddressController.updateOrCreateDeliveryAddress(userId, addressDTO);
//
//        assertNotNull(response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("New Street 456", response.getBody().getStreet());
//        assertEquals(101, response.getBody().getHouseNumber());
//    }
//
//}
