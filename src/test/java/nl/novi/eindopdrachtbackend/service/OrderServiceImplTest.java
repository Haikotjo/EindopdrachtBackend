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

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private DeliveryAddressRepository deliveryAddressRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order1;
    private Order order2;
    private User customer;
    private Restaurant restaurant;
    private DeliveryAddress deliveryAddress;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new User();
        restaurant = new Restaurant();
        deliveryAddress = new DeliveryAddress();

        order1 = new Order(customer, restaurant, deliveryAddress, true);
        order2 = new Order(customer, restaurant, deliveryAddress, false);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(deliveryAddressRepository.findById(anyLong())).thenReturn(Optional.of(deliveryAddress));
    }

    @Test
    void getAllOrders_ReturnsListOfOrders() {
        // Preparation
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Action
        List<OrderDTO> orders = orderService.getAllOrders();

        // Verification
        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getOrderById_ReturnsOrder() {
        // Preparation
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));

        // Action
        OrderDTO foundOrder = orderService.getOrderById(1L);

        // Verification
        assertNotNull(foundOrder);
        assertTrue(foundOrder.isFulfilled());
        verify(orderRepository).findById(1L);
    }

    @Test
    void getOrderById_ThrowsResourceNotFoundException() {
        // Preparation
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1L));
        verify(orderRepository).findById(1L);
    }

    @Test
    void createOrder_ReturnsCreatedOrder() {
        // Preparation
        OrderInputDTO inputDTO = new OrderInputDTO(true, 1L, 1L, 1L);
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        // Action
        OrderDTO createdOrder = orderService.createOrder(inputDTO);

        // Verification
        assertNotNull(createdOrder);
        assertTrue(createdOrder.isFulfilled());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrder_ReturnsUpdatedOrder() {
        // Preparation
        OrderInputDTO inputDTO = new OrderInputDTO(false, 1L, 1L, 1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        // Action
        OrderDTO updatedOrder = orderService.updateOrder(1L, inputDTO);

        // Verification
        assertNotNull(updatedOrder);
        assertFalse(updatedOrder.isFulfilled());
        verify(orderRepository).save(any(Order.class));
        verify(orderRepository).findById(1L);
    }

    @Test
    void updateOrder_ThrowsResourceNotFoundException() {
        // Preparation
        OrderInputDTO inputDTO = new OrderInputDTO(false, 1L, 1L, 1L);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrder(1L, inputDTO));
        verify(orderRepository).findById(1L);
    }

    @Test
    void deleteOrder_DeletesOrder() {
        // Preparation
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));

        // Action
        orderService.deleteOrder(1L);

        // Verification
        verify(orderRepository).delete(order1);
    }

    @Test
    void deleteOrder_ThrowsResourceNotFoundException() {
        // Preparation
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(1L));
        verify(orderRepository).findById(1L);
    }

    @Test
    void findOrdersByCustomerId_ReturnsOrders() {
        // Preparation
        when(orderRepository.findOrdersByCustomerId(anyLong())).thenReturn(Arrays.asList(order1));

        // Action
        List<OrderDTO> orders = orderService.findOrdersByCustomerId(1L);

        // Verification
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());
        verify(orderRepository).findOrdersByCustomerId(1L);
    }

    @Test
    void findOrdersByRestaurantId_ReturnsOrders() {
        // Preparation
        when(orderRepository.findOrdersByRestaurantId(anyLong())).thenReturn(Arrays.asList(order2));

        // Action
        List<OrderDTO> orders = orderService.findOrdersByRestaurantId(1L);

        // Verification
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());
        verify(orderRepository).findOrdersByRestaurantId(1L);
    }
}
