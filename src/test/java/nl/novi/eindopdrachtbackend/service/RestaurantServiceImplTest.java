package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.RestaurantDTO;
import nl.novi.eindopdrachtbackend.dto.RestaurantInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static java.nio.file.Files.delete;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private Restaurant restaurant;
    private RestaurantInputDTO restaurantInputDTO;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        restaurant = new Restaurant("Gourmet Place", "456 Food Ave", "555-6789");

        // Reflectively setting the ID for the restaurant
        Field idField = Restaurant.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(restaurant, 1L);

        restaurantInputDTO = new RestaurantInputDTO();
        restaurantInputDTO.setName("Gourmet Place");
        restaurantInputDTO.setAddress("456 Food Ave");
        restaurantInputDTO.setPhoneNumber("555-6789");
    }


    @Test
    void getAllRestaurantsTest() {
        // Preparation
        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant));

        // Action
        List<RestaurantDTO> result = restaurantService.getAllRestaurants();

        // Verification
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Gourmet Place", result.get(0).getName());
        verify(restaurantRepository).findAll();
    }

    @Test
    void getRestaurantByIdTest() {
        // Preparation
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        // Action
        RestaurantDTO result = restaurantService.getRestaurantById(1L);

        // Verification
        assertNotNull(result);
        assertEquals("Gourmet Place", result.getName());
        verify(restaurantRepository).findById(1L);
    }


    @Test
    void createRestaurantTest() {
        // Preparation
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(restaurantInputDTO.getName());
        newRestaurant.setAddress(restaurantInputDTO.getAddress());
        newRestaurant.setPhoneNumber(restaurantInputDTO.getPhoneNumber());

        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(newRestaurant);
        RestaurantDTO result = restaurantService.createRestaurant(restaurantInputDTO);
        assertEquals("Gourmet Place", result.getName());
        assertEquals("456 Food Ave", result.getAddress());
    }

    @Test
    void updateRestaurantTest() {
        // Preparation
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant);

        // Action
        RestaurantDTO updatedRestaurant = restaurantService.updateRestaurant(1L, restaurantInputDTO);

        // Verification
        assertEquals("Gourmet Place", updatedRestaurant.getName());
    }

    @Test
    void deleteRestaurant_DeletesRestaurant() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        doNothing().when(restaurantRepository).delete(any(Restaurant.class));
        restaurantService.deleteRestaurant(1L);
        verify(restaurantRepository, times(1)).delete(restaurant);
    }

    @Test
    void findByNameIgnoreCase_ReturnsRestaurants() {
        when(restaurantRepository.findByNameIgnoreCase("Gourmet Place")).thenReturn(Arrays.asList(restaurant));
        List<RestaurantDTO> result = restaurantService.findByNameIgnoreCase("Gourmet Place");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Gourmet Place", result.get(0).getName());
        verify(restaurantRepository).findByNameIgnoreCase("Gourmet Place");
    }
}
