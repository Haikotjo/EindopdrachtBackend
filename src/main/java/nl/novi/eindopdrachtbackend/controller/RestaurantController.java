package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.RestaurantDTO;
import nl.novi.eindopdrachtbackend.dto.RestaurantInputDTO;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import nl.novi.eindopdrachtbackend.security.SecurityUtils;
import nl.novi.eindopdrachtbackend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, UserRepository userRepository) {
        this.restaurantService = restaurantService;
        this.userRepository = userRepository;
    }

    /**
     * Get all restaurants (Admin only)
     *
     * @return ResponseEntity containing a list of RestaurantDTO objects
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<RestaurantDTO> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    /**
     * Get all restaurants open for all.
     *
     * @return ResponseEntity containing a list of RestaurantDTO objects with limited information
     */
    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantsPublic() {
        List<RestaurantDTO> restaurants = restaurantService.getAllRestaurantsPublic();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    /**
     * Get the restaurant for the logged-in owner.
     *
     * @return ResponseEntity containing the RestaurantDTO object
     */
    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    public ResponseEntity<RestaurantDTO> getRestaurantForLoggedInOwner() {
        String currentUserEmail = SecurityUtils.getCurrentAuthenticatedUserEmail();
        try {
            RestaurantDTO restaurant = restaurantService.getRestaurantForLoggedInOwner(currentUserEmail);
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get a specific restaurant by its ID with full details (Admin only).
     *
     * @param id the ID of the restaurant
     * @return ResponseEntity containing the RestaurantDTO object for the specified ID
     */
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestaurantDTO> getRestaurantByIdForAdmin(@PathVariable Long id) {
        try {
            RestaurantDTO restaurant = restaurantService.getRestaurantByIdForAdmin(id);
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint to get a specific restaurant by its ID with simple details (Public).
     *
     * @param id the ID of the restaurant
     * @return ResponseEntity containing the RestaurantDTO object for the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantByIdPublic(@PathVariable Long id) {
        try {
            RestaurantDTO restaurant = restaurantService.getRestaurantByIdPublic(id);
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//
//    @PostMapping
//    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantInputDTO restaurantInputDTO) {
//        RestaurantDTO newRestaurant = restaurantService.createRestaurant(restaurantInputDTO);
//        return new ResponseEntity<>(newRestaurant, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantInputDTO restaurantInputDTO) {
//        RestaurantDTO updatedRestaurant = restaurantService.updateRestaurant(id, restaurantInputDTO);
//        return new ResponseEntity<>(updatedRestaurant, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
//        restaurantService.deleteRestaurant(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping("/search/by-name")
//    public ResponseEntity<List<RestaurantDTO>> getRestaurantsByName(@RequestParam String name) {
//        List<RestaurantDTO> restaurants = restaurantService.findByNameIgnoreCase(name);
//        return new ResponseEntity<>(restaurants, HttpStatus.OK);
//    }


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
