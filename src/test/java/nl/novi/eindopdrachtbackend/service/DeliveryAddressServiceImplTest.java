package nl.novi.eindopdrachtbackend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressMapper;
import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class DeliveryAddressServiceImplTest {

    @Mock
    private DeliveryAddressRepository deliveryAddressRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeliveryAddressServiceImpl deliveryAddressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDeliveryAddress_ShouldSaveAddress() {
        // Arrange
        DeliveryAddressInputDTO inputDTO = new DeliveryAddressInputDTO();
        inputDTO.setStreet("Main Street");
        inputDTO.setHouseNumber(123);
        inputDTO.setCity("Springfield");
        inputDTO.setPostcode("12345");
        inputDTO.setCountry("USA");

        User user = new User();
        ReflectionTestUtils.setField(user, "id", 1L); // Stel de ID van de gebruiker in

        DeliveryAddress address = new DeliveryAddress();
        ReflectionTestUtils.setField(address, "id", 1L); // Stel de ID van het adres in, gelijk aan de gebruiker ID
        address.setUser(user); // De gebruiker koppelen aan het adres
        address.setStreet(inputDTO.getStreet());
        address.setHouseNumber(inputDTO.getHouseNumber());
        address.setCity(inputDTO.getCity());
        address.setPostcode(inputDTO.getPostcode());
        address.setCountry(inputDTO.getCountry());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(deliveryAddressRepository.save(any(DeliveryAddress.class))).thenReturn(address);

        // Act
        DeliveryAddressDTO result = deliveryAddressService.createDeliveryAddress(inputDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Main Street", result.getStreet());
        assertEquals(123, result.getHouseNumber());
        assertEquals("Springfield", result.getCity());
        assertEquals("12345", result.getPostcode());
        assertEquals("USA", result.getCountry());
        verify(deliveryAddressRepository).save(any(DeliveryAddress.class));
    }

    @Test
    void updateDeliveryAddress_ShouldUpdateAddress() {
        // Arrange
        Long addressId = 1L;
        DeliveryAddress existingAddress = new DeliveryAddress();
        ReflectionTestUtils.setField(existingAddress, "id", addressId); // Gebruik ReflectionTestUtils om de ID in te stellen
        existingAddress.setStreet("Old Street");
        existingAddress.setHouseNumber(123);
        existingAddress.setCity("Old City");
        existingAddress.setPostcode("12345");
        existingAddress.setCountry("USA");

        DeliveryAddressInputDTO inputDTO = new DeliveryAddressInputDTO();
        inputDTO.setStreet("New Street");
        inputDTO.setHouseNumber(124);
        inputDTO.setCity("New City");
        inputDTO.setPostcode("54321");
        inputDTO.setCountry("USA");

        when(deliveryAddressRepository.findById(addressId)).thenReturn(Optional.of(existingAddress));

        // Act
        DeliveryAddressDTO result = deliveryAddressService.updateDeliveryAddress(addressId, inputDTO);

        // Assert
        assertNotNull(result);
        assertEquals(inputDTO.getStreet(), result.getStreet());
        verify(deliveryAddressRepository).findById(addressId);
    }


    @Test
    void deleteDeliveryAddress_ShouldDeleteAddress() {
        // Arrange
        Long id = 1L;
        DeliveryAddress address = new DeliveryAddress();
        ReflectionTestUtils.setField(address, "id", id);

        when(deliveryAddressRepository.findById(id)).thenReturn(Optional.of(address));
        doNothing().when(deliveryAddressRepository).delete(address);

        // Act
        deliveryAddressService.deleteDeliveryAddress(id);

        // Assert
        verify(deliveryAddressRepository).findById(id);
        verify(deliveryAddressRepository).delete(address);
    }

    @Test
    void getDeliveryAddressById_ShouldReturnAddress() {
        // Arrange
        Long id = 1L;
        DeliveryAddress address = new DeliveryAddress();
        address.setStreet("Main Street");
        address.setHouseNumber(123);
        ReflectionTestUtils.setField(address, "id", id);

        when(deliveryAddressRepository.findById(id)).thenReturn(Optional.of(address));

        // Act
        DeliveryAddressDTO result = deliveryAddressService.getDeliveryAddressById(id);

        // Assert
        assertNotNull(result);
        assertEquals("Main Street", result.getStreet());
        assertEquals(123, result.getHouseNumber());
        verify(deliveryAddressRepository).findById(id);
    }

    @Test
    void getAllDeliveryAddresses_ShouldReturnAllAddresses() {
        // Arrange
        DeliveryAddress address1 = new DeliveryAddress();
        DeliveryAddress address2 = new DeliveryAddress();
        List<DeliveryAddress> addresses = Arrays.asList(address1, address2);

        when(deliveryAddressRepository.findAll()).thenReturn(addresses);

        // Act
        List<DeliveryAddressDTO> results = deliveryAddressService.getAllDeliveryAddresses();

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        verify(deliveryAddressRepository).findAll();
    }

}
