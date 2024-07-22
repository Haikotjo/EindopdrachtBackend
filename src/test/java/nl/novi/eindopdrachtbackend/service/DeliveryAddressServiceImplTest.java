//package nl.novi.eindopdrachtbackend.service;
//
//import nl.novi.eindopdrachtbackend.dto.DeliveryAddressDTO;
//import nl.novi.eindopdrachtbackend.dto.DeliveryAddressInputDTO;
//import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
//import nl.novi.eindopdrachtbackend.model.User;
//import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
//import nl.novi.eindopdrachtbackend.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.lang.reflect.Field;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class DeliveryAddressServiceImplTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private DeliveryAddressRepository deliveryAddressRepository;
//
//    @InjectMocks
//    private DeliveryAddressServiceImpl deliveryAddressService;
//
//    private User user;
//    private DeliveryAddress address;
//    private DeliveryAddressInputDTO addressDTO;
//
//    @BeforeEach
//    void setUp() throws NoSuchFieldException, IllegalAccessException {
//        user = new User();
//        address = new DeliveryAddress();
//        addressDTO = new DeliveryAddressInputDTO();
//        addressDTO.setStreet("New Street");
//        addressDTO.setHouseNumber(123);
//        addressDTO.setCity("New City");
//        addressDTO.setPostcode("12345");
//        addressDTO.setCountry("Newland");
//
//        // Setting ID using reflection
//        Field userIdField = User.class.getDeclaredField("id");
//        userIdField.setAccessible(true);
//        userIdField.set(user, 1L);
//
//        user.setDeliveryAddress(address);
//    }
//
//    @Test
//    void updateOrCreateDeliveryAddress_WithExistingAddress_ShouldUpdate() {
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(deliveryAddressRepository.save(any(DeliveryAddress.class))).thenReturn(address);
//
//        DeliveryAddressDTO result = deliveryAddressService.updateOrCreateDeliveryAddress(1L, addressDTO);
//
//        assertNotNull(result);
//        assertEquals("New Street", result.getStreet());
//        verify(deliveryAddressRepository).save(address);
//        assertEquals("New City", address.getCity());
//    }
//
//    @Test
//    void updateOrCreateDeliveryAddress_WithNoExistingAddress_ShouldCreate() {
//        user.setDeliveryAddress(null); // Setting no initial address
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(deliveryAddressRepository.save(any(DeliveryAddress.class))).thenAnswer(invocation -> invocation.getArgument(0));
//
//        DeliveryAddressDTO result = deliveryAddressService.updateOrCreateDeliveryAddress(1L, addressDTO);
//
//        assertNotNull(result);
//        assertEquals("New Street", result.getStreet());
//        verify(deliveryAddressRepository).save(any(DeliveryAddress.class));
//    }
//}
