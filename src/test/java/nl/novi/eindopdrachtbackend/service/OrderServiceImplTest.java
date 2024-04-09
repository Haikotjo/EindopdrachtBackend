package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
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

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order1 = new Order();
        order1.setFulfilled(true);

        order2 = new Order();
        order2.setFulfilled(false);
    }

    @Test
    void getAllOrders_ReturnsListOfOrders() {
        // Preparation
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        // Action
        List<Order> orders = orderService.getAllOrders();

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
        Order foundOrder = orderService.getOrderById(1L);

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
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        // Action
        Order createdOrder = orderService.createOrder(order1);

        // Verification
        assertNotNull(createdOrder);
        assertEquals(order1.getId(), createdOrder.getId());
        verify(orderRepository).save(order1);
    }

    @Test
    void updateOrder_ReturnsUpdatedOrder() {
        // Preparation
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        // Action
        order1.setFulfilled(false);
        Order updatedOrder = orderService.updateOrder(1L, order1);

        // Verification
        assertNotNull(updatedOrder);
        assertFalse(updatedOrder.isFulfilled());
        verify(orderRepository).save(order1);
        verify(orderRepository).findById(1L);
    }

    @Test
    void updateOrder_ThrowsResourceNotFoundException() {
        // Preparation
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Action & Verification
        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrder(1L, new Order()));
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
        List<Order> orders = orderService.findOrdersByCustomerId(1L);

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
        List<Order> orders = orderService.findOrdersByRestaurantId(1L);

        // Verification
        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());
        verify(orderRepository).findOrdersByRestaurantId(1L);
    }
}
