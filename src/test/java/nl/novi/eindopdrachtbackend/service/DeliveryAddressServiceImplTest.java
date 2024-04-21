//package nl.novi.eindopdrachtbackend.service;
//
//import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
//import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
//import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
//import nl.novi.eindopdrachtbackend.service.DeliveryAddressServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class DeliveryAddressServiceImplTest {
//
//    @Mock
//    private DeliveryAddressRepository deliveryAddressRepository;
//
//    @InjectMocks
//    private DeliveryAddressServiceImpl deliveryAddressService;
//
//    private DeliveryAddress address1;
//    private DeliveryAddress address2;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        address1 = new DeliveryAddress("TestStreet1", 1, "TestCity1", 1234, "1234AB", "TestCountry1");
//        address2 = new DeliveryAddress("TestStreet2", 2, "TestCity2", 2345, "2345BC", "TestCountry2");
//    }
//
//    @Test
//    void getAllAddresses_ReturnsListOfAddresses() {
//        // Preparation
//        when(deliveryAddressRepository.findAll()).thenReturn(Arrays.asList(address1, address2));
//
//        // Action
//        List<DeliveryAddress> result = deliveryAddressService.getAllAddresses();
//
//        // Verification
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(deliveryAddressRepository).findAll();
//    }
//
//    @Test
//    void getAddressById_ReturnsAddress() {
//        // Preparation
//        when(deliveryAddressRepository.findById(1L)).thenReturn(Optional.of(address1));
//
//        // Action
//        DeliveryAddress foundAddress = deliveryAddressService.getAddressById(1L);
//
//        // Verification
//        assertNotNull(foundAddress);
//        assertEquals("TestStreet1", foundAddress.getStreet());
//        verify(deliveryAddressRepository).findById(1L);
//    }
//
//    @Test
//    void getAddressById_ThrowsResourceNotFoundException() {
//        // Preparation
//        when(deliveryAddressRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        // Action & Verification
//        assertThrows(ResourceNotFoundException.class, () -> deliveryAddressService.getAddressById(1L));
//        verify(deliveryAddressRepository).findById(anyLong());
//    }
//
//    @Test
//    void createAddress_ReturnsCreatedAddress() {
//        // Preparation
//        when(deliveryAddressRepository.save(any(DeliveryAddress.class))).thenReturn(address1);
//
//        // Action
//        DeliveryAddress createdAddress = deliveryAddressService.createAddress(address1);
//
//        // Verification
//        assertNotNull(createdAddress);
//        assertEquals("TestStreet1", createdAddress.getStreet());
//        verify(deliveryAddressRepository).save(address1);
//    }
//
//    @Test
//    void updateAddress_ReturnsUpdatedAddress() {
//        // Preparation
//        DeliveryAddress originalAddress = new DeliveryAddress("OriginalStreet", 1, "OriginalCity", 1234, "1234AB", "OriginalCountry");
//
//        DeliveryAddress updatedDetails = new DeliveryAddress("UpdatedStreet", 2, "UpdatedCity", 5678, "5678CD", "UpdatedCountry");
//
//        when(deliveryAddressRepository.findById(1L)).thenReturn(Optional.of(originalAddress));
//        when(deliveryAddressRepository.save(any(DeliveryAddress.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Action
//        DeliveryAddress updatedAddress = deliveryAddressService.updateAddress(1L, updatedDetails);
//
//        // Verification
//        assertNotNull(updatedAddress);
//        assertEquals("UpdatedStreet", updatedAddress.getStreet());
//        assertEquals(2, updatedAddress.getHouseNumber());
//        assertEquals("UpdatedCity", updatedAddress.getCity());
//        assertEquals(5678, updatedAddress.getPostcodeNumber());
//        assertEquals("5678CD", updatedAddress.getPostcode());
//        assertEquals("UpdatedCountry", updatedAddress.getCountry());
//
//        verify(deliveryAddressRepository).findById(1L);
//        verify(deliveryAddressRepository).save(any(DeliveryAddress.class));
//    }
//
//
//    @Test
//    void deleteAddress_DeletesAddress() {
//        // Preparation
//        when(deliveryAddressRepository.findById(1L)).thenReturn(Optional.of(address1));
//
//        // Action
//        deliveryAddressService.deleteAddress(1L);
//
//        // Verification
//        verify(deliveryAddressRepository).delete(address1);
//    }
//}
