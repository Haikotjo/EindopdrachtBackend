package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.OrderDTO;
import nl.novi.eindopdrachtbackend.dto.OrderInputDTO;
import nl.novi.eindopdrachtbackend.dto.OrderMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.DeliveryAddress;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.model.Restaurant;
import nl.novi.eindopdrachtbackend.model.User;
import nl.novi.eindopdrachtbackend.repository.DeliveryAddressRepository;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
import nl.novi.eindopdrachtbackend.repository.RestaurantRepository;
import nl.novi.eindopdrachtbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            RestaurantRepository restaurantRepository, DeliveryAddressRepository deliveryAddressRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.deliveryAddressRepository = deliveryAddressRepository;
    }

    @Override
    public OrderDTO createOrder(OrderInputDTO orderInputDTO) {
        User customer = userRepository.findById(orderInputDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + orderInputDTO.getCustomerId()));

        Restaurant restaurant = restaurantRepository.findById(orderInputDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + orderInputDTO.getRestaurantId()));

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(orderInputDTO.getDeliveryAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("DeliveryAddress not found for this id :: " + orderInputDTO.getDeliveryAddressId()));

        Order order = OrderMapper.fromInputDTO(orderInputDTO, customer, restaurant, deliveryAddress);
        order = orderRepository.save(order);
        return OrderMapper.toDTO(order);
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderInputDTO orderInputDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

        User customer = userRepository.findById(orderInputDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + orderInputDTO.getCustomerId()));

        Restaurant restaurant = restaurantRepository.findById(orderInputDTO.getRestaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found for this id :: " + orderInputDTO.getRestaurantId()));

        DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(orderInputDTO.getDeliveryAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("DeliveryAddress not found for this id :: " + orderInputDTO.getDeliveryAddressId()));

        order.setFulfilled(orderInputDTO.isFulfilled());
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);

        order = orderRepository.save(order);
        return OrderMapper.toDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
        return OrderMapper.toDTO(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDTO> findOrdersByCustomerId(Long customerId) {
        return orderRepository.findOrdersByCustomerId(customerId).stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> findOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findOrdersByRestaurantId(restaurantId).stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
