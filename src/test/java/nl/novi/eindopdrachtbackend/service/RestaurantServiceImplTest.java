package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    private Restaurant restaurant1;
    private Restaurant restaurant2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurant1 = new Restaurant("Restaurant One", "123 Main St", "555-1234");
        restaurant2 = new Restaurant("Restaurant Two", "456 Elm St", "555-5678");
    }

    @Test
    void getAllRestaurants_ReturnsListOfRestaurants() {
        // Preparation
        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant1, restaurant2));

        // Action
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        // Verification
        assertNotNull(restaurants);
        assertEquals(2, restaurants.size());
        verify(restaurantRepository).findAll();
    }

    @Test
    void getRestaurantById_ReturnsRestaurant() {
        // Preparation
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));

        // Action
        Restaurant foundRestaurant = restaurantService.getRestaurantById(1L);

        // Verification
        assertNotNull(foundRestaurant);
        assertEquals("Restaurant One", foundRestaurant.getName());
        verify(restaurantRepository).findById(1L);
    }

    @Test
    void getRestaurantById_ThrowsResourceNotFoundException() {
        // Preparation
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> restaurantService.getRestaurantById(1L));
        verify(restaurantRepository).findById(1L);
    }

    @Test
    void createRestaurant_ReturnsCreatedRestaurant() {
        // Preparation
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);

        // Action
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant1);

        // Verification
        assertNotNull(createdRestaurant);
        assertEquals("Restaurant One", createdRestaurant.getName());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    void updateRestaurant_ReturnsUpdatedRestaurant() {
        // Preparation
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(restaurant1);

        // Action
        restaurant1.setAddress("New Address");
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(1L, restaurant1);

        // Verification
        assertNotNull(updatedRestaurant);
        assertEquals("New Address", updatedRestaurant.getAddress());
        verify(restaurantRepository).save(any(Restaurant.class));
        verify(restaurantRepository).findById(1L);
    }

    @Test
    void deleteRestaurant_DeletesRestaurant() {
        // Preparation
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant1));

        // Action
        restaurantService.deleteRestaurant(1L);

        // Verification
        verify(restaurantRepository).delete(any(Restaurant.class));
    }

    @Test
    void findByNameIgnoreCase_ReturnsRestaurants() {
        // Preparation
        when(restaurantRepository.findByNameIgnoreCase("restaurant one")).thenReturn(Arrays.asList(restaurant1));

        // Action
        List<Restaurant> foundRestaurants = restaurantService.findByNameIgnoreCase("restaurant one");

        // Verification
        assertNotNull(foundRestaurants);
        assertFalse(foundRestaurants.isEmpty());
        assertEquals(1, foundRestaurants.size());
        assertEquals("Restaurant One", foundRestaurants.get(0).getName());
        verify(restaurantRepository).findByNameIgnoreCase("restaurant one");
    }
}
