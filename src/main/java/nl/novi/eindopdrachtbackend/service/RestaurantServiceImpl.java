package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.model.UserRole;
import nl.novi.eindopdrachtbackend.repository.NotificationRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, UserRepository userRepository, NotificationRepository notificationRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.notificationRepository = notificationRepository;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public RestaurantDTO updateRestaurantForOwner(RestaurantInputDTO restaurantInputDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        Restaurant restaurant = user.getRestaurant();
        if (restaurant == null) {
            throw new ResourceNotFoundException("No restaurant found for the current user.");
        }

        restaurant.setName(restaurantInputDTO.getName());
        restaurant.setAddress(restaurantInputDTO.getAddress());
        restaurant.setPhoneNumber(restaurantInputDTO.getPhoneNumber());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantMapper.toRestaurantDTO(updatedRestaurant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RestaurantDTO updateRestaurantForAdmin(RestaurantInputDTO restaurantInputDTO, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantId));

        restaurant.setName(restaurantInputDTO.getName());
        restaurant.setAddress(restaurantInputDTO.getAddress());
        restaurant.setPhoneNumber(restaurantInputDTO.getPhoneNumber());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return RestaurantMapper.toRestaurantDTO(updatedRestaurant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRestaurantByAdmin(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + restaurantId));
        User owner = restaurant.getOwner();

        // Remove references to the owner in notifications
        notificationRepository.findByUser(owner).forEach(notification -> {
            notification.setUser(null);
            notificationRepository.save(notification);
        });

        restaurantRepository.delete(restaurant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRestaurantByOwner(User owner) {
        Restaurant restaurant = restaurantRepository.findByOwner(owner)
                .orElseThrow(() -> new ResourceNotFoundException("No restaurant found for the current owner."));

        // Remove references to the owner in notifications
        notificationRepository.findByUser(owner).forEach(notification -> {
            notification.setUser(null);
            notificationRepository.save(notification);
        });

        restaurantRepository.delete(restaurant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RestaurantDTO> findByNameIgnoreCase(String name) {
        return restaurantRepository.findByNameIgnoreCase(name).stream()
                .map(RestaurantMapper::toRestaurantDTO)
                .collect(Collectors.toList());
    }
}
