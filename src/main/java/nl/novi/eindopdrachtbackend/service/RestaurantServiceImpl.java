package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantMapper::toRestaurantDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RestaurantDTO> getAllRestaurantsPublic() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantMapper::toSimpleRestaurantDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestaurantDTO getRestaurantForLoggedInOwner(String email) {
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        Restaurant restaurant = currentUser.getRestaurant();
        if (restaurant == null) {
            throw new ResourceNotFoundException("No restaurant found for the current user.");
        }
        return RestaurantMapper.toRestaurantDTO(restaurant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestaurantDTO getRestaurantByIdForAdmin(Long id) {
        try {
            Restaurant restaurant = restaurantRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + id));
            return RestaurantMapper.toRestaurantDTO(restaurant);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve restaurant with id " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestaurantDTO getRestaurantByIdPublic(Long id) {
        try {
            Restaurant restaurant = restaurantRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + id));
            return RestaurantMapper.toSimpleRestaurantDTO(restaurant);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve restaurant with id " + id, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestaurantDTO createRestaurantForLoggedInOwner(RestaurantInputDTO restaurantInputDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantInputDTO.getName());
        restaurant.setAddress(restaurantInputDTO.getAddress());
        restaurant.setPhoneNumber(restaurantInputDTO.getPhoneNumber());
        restaurant.setOwner(user);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantMapper.toRestaurantDTO(savedRestaurant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestaurantDTO createRestaurantForOwner(RestaurantInputDTO restaurantInputDTO, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found for this id :: " + ownerId));

        // Controleer of de gebruiker de rol OWNER heeft
        if (owner.getRoles().stream().noneMatch(role -> role.getRolename() == UserRole.OWNER)) {
            throw new IllegalArgumentException("The specified user is not an OWNER");
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantInputDTO.getName());
        restaurant.setAddress(restaurantInputDTO.getAddress());
        restaurant.setPhoneNumber(restaurantInputDTO.getPhoneNumber());
        restaurant.setOwner(owner);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantMapper.toRestaurantDTO(savedRestaurant);
    }



//
//    @Override
//    public RestaurantDTO updateRestaurant(Long id, RestaurantInputDTO restaurantInputDTO) {
//        Restaurant existingRestaurant = restaurantRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + id));
//        existingRestaurant.setName(restaurantInputDTO.getName());
//        existingRestaurant.setAddress(restaurantInputDTO.getAddress());
//        existingRestaurant.setPhoneNumber(restaurantInputDTO.getPhoneNumber());
//        existingRestaurant = restaurantRepository.save(existingRestaurant);
//        return RestaurantMapper.toDTO(existingRestaurant);
//    }
//
//    @Override
//    public void deleteRestaurant(Long id) {
//        Restaurant existingRestaurant = restaurantRepository.findById(id)
//                        .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
//        restaurantRepository.delete(existingRestaurant);
//    }
//
//    @Override
//    public List<RestaurantDTO> findByNameIgnoreCase(String name) {
//        return restaurantRepository.findByNameIgnoreCase(name).stream()
//                .map(RestaurantMapper::toDTO)
//                .collect(Collectors.toList());
//    }
}
