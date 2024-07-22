//package nl.novi.eindopdrachtbackend.controller;
//
//import nl.novi.eindopdrachtbackend.dto.RestaurantDTO;
//import nl.novi.eindopdrachtbackend.dto.RestaurantInputDTO;
//import nl.novi.eindopdrachtbackend.service.RestaurantService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class RestaurantControllerTest {
//
//    @Mock
//    private RestaurantService restaurantService;
//
//    @InjectMocks
//    private RestaurantController restaurantController;
//
//    @BeforeEach
//    void setUp() {
//        // Hier kun je initialisatie doen indien nodig
//    }
//
//    @Test
//    void getAllRestaurants_ShouldReturnRestaurants() {
//        List<RestaurantDTO> expectedRestaurants = new ArrayList<>();
//        expectedRestaurants.add(new RestaurantDTO());
//        when(restaurantService.getAllRestaurants()).thenReturn(expectedRestaurants);
//
//        ResponseEntity<List<RestaurantDTO>> response = restaurantController.getAllRestaurants();
//
//        assertNotNull(response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, response.getBody().size());
//    }
//
//    @Test
//    void getRestaurantById_ShouldReturnRestaurant() {
//        RestaurantDTO expectedRestaurant = new RestaurantDTO();
//        when(restaurantService.getRestaurantById(1L)).thenReturn(expectedRestaurant);
//
//        ResponseEntity<RestaurantDTO> response = restaurantController.getRestaurantById(1L);
//
//        assertNotNull(response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void createRestaurant_ShouldCreateRestaurant() {
//        RestaurantInputDTO inputDTO = new RestaurantInputDTO();
//        RestaurantDTO expectedRestaurant = new RestaurantDTO();
//        when(restaurantService.createRestaurant(inputDTO)).thenReturn(expectedRestaurant);
//
//        ResponseEntity<RestaurantDTO> response = restaurantController.createRestaurant(inputDTO);
//
//        assertNotNull(response.getBody());
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//    }
//
//    @Test
//    void updateRestaurant_ShouldUpdateRestaurant() {
//        RestaurantInputDTO inputDTO = new RestaurantInputDTO();
//        RestaurantDTO expectedRestaurant = new RestaurantDTO();
//        when(restaurantService.updateRestaurant(1L, inputDTO)).thenReturn(expectedRestaurant);
//
//        ResponseEntity<RestaurantDTO> response = restaurantController.updateRestaurant(1L, inputDTO);
//
//        assertNotNull(response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void deleteRestaurant_ShouldReturnNoContent() {
//        doNothing().when(restaurantService).deleteRestaurant(1L);
//
//        ResponseEntity<Void> response = restaurantController.deleteRestaurant(1L);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }
//
//    @Test
//    void getRestaurantsByName_ShouldReturnRestaurants() {
//        List<RestaurantDTO> expectedRestaurants = new ArrayList<>();
//        expectedRestaurants.add(new RestaurantDTO());
//        when(restaurantService.findByNameIgnoreCase("Italian Bistro")).thenReturn(expectedRestaurants);
//
//        ResponseEntity<List<RestaurantDTO>> response = restaurantController.getRestaurantsByName("Italian Bistro");
//
//        assertNotNull(response.getBody());
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(1, response.getBody().size());
//    }
//}
