package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
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

//    @Override
//    public RestaurantDTO getRestaurantById(Long id) {
//        Restaurant restaurant = restaurantRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + id));
//        return RestaurantMapper.toDTO(restaurant);
//    }
//
//    @Override
//    public RestaurantDTO createRestaurant(RestaurantInputDTO restaurantInputDTO) {
//        Restaurant restaurant = RestaurantMapper.fromInputDTO(restaurantInputDTO);
//        restaurant = restaurantRepository.save(restaurant);
//        return RestaurantMapper.toDTO(restaurant);
//    }
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
