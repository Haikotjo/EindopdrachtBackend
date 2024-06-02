package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.OrderDTO;
import nl.novi.eindopdrachtbackend.dto.OrderInputDTO;
import nl.novi.eindopdrachtbackend.dto.OrderMapper;
import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.*;
import nl.novi.eindopdrachtbackend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

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

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order1;
    private Order order2;
    private User customer;
    private Restaurant restaurant;
    private DeliveryAddress deliveryAddress;
    private MenuItem menuItem;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
        customer = new User();
        setIdUsingReflection(customer, 1L);
        customer.setName("John Doe");

        restaurant = new Restaurant();
        setIdUsingReflection(restaurant, 1L);
        restaurant.setName("Italian Bistro");

        deliveryAddress = new DeliveryAddress();
        setIdUsingReflection(deliveryAddress, 1L);

        menuItem = new MenuItem();
        setIdUsingReflection(menuItem, 1L);
        menuItem.setName("Pizza");
        menuItem.setPrice(9.99);

        Set<MenuItem> menuItems = new HashSet<>();
        menuItems.add(menuItem);

        order1 = new Order(customer, restaurant, deliveryAddress, true);
        setIdUsingReflection(order1, 1L);
        order1.setMenuItems(menuItems);

        order2 = new Order(customer, restaurant, deliveryAddress, false);
        setIdUsingReflection(order2, 2L);
        order2.setMenuItems(menuItems);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(restaurantRepository.findById(anyLong())).thenReturn(Optional.of(restaurant));
        when(deliveryAddressRepository.findById(anyLong())).thenReturn(Optional.of(deliveryAddress));
        when(menuItemRepository.findById(anyLong())).thenReturn(Optional.of(menuItem));
    }

    private void setIdUsingReflection(Object obj, Long idValue) throws NoSuchFieldException, IllegalAccessException {
        Field idField = obj.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(obj, idValue);
    }

    @Test
    void getAllOrders_ReturnsListOfOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<OrderDTO> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getOrderById_ReturnsOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));

        OrderDTO foundOrder = orderService.getOrderById(1L);

        assertNotNull(foundOrder);
        assertTrue(foundOrder.isFulfilled());
        verify(orderRepository).findById(1L);
    }

    @Test
    void getOrderById_ThrowsResourceNotFoundException() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1L));
        verify(orderRepository).findById(1L);
    }

    @Test
    void createOrder_ReturnsCreatedOrder() {
        OrderInputDTO inputDTO = new OrderInputDTO(true, 1L, 1L, 1L, Arrays.asList(1L));
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        OrderDTO createdOrder = orderService.createOrder(inputDTO);

        assertNotNull(createdOrder);
        assertTrue(createdOrder.isFulfilled());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrder_ReturnsUpdatedOrder() {
        OrderInputDTO inputDTO = new OrderInputDTO(false, 1L, 1L, 1L, Arrays.asList(1L));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(orderRepository.save(any(Order.class))).thenReturn(order1);

        OrderDTO updatedOrder = orderService.updateOrder(1L, inputDTO);

        assertNotNull(updatedOrder);
        assertFalse(updatedOrder.isFulfilled());
        verify(orderRepository).save(any(Order.class));
        verify(orderRepository).findById(1L);
    }

    @Test
    void updateOrder_ThrowsResourceNotFoundException() {
        OrderInputDTO inputDTO = new OrderInputDTO(false, 1L, 1L, 1L, Arrays.asList(1L));
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrder(1L, inputDTO));
        verify(orderRepository).findById(1L);
    }

    @Test
    void deleteOrder_DeletesOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));

        orderService.deleteOrder(1L);

        verify(orderRepository).delete(order1);
    }

    @Test
    void deleteOrder_ThrowsResourceNotFoundException() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(1L));
        verify(orderRepository).findById(1L);
    }

    @Test
    void findOrdersByCustomerId_ReturnsOrders() {
        when(orderRepository.findOrdersByCustomerId(anyLong())).thenReturn(Arrays.asList(order1));

        List<OrderDTO> orders = orderService.findOrdersByCustomerId(1L);

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());
        verify(orderRepository).findOrdersByCustomerId(1L);
    }

    @Test
    void findOrdersByRestaurantId_ReturnsOrders() {
        when(orderRepository.findOrdersByRestaurantId(anyLong())).thenReturn(Arrays.asList(order2));

        List<OrderDTO> orders = orderService.findOrdersByRestaurantId(1L);

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());
        verify(orderRepository).findOrdersByRestaurantId(1L);
    }

    @Test
    void generatePrintableOrder_ReturnsCorrectString() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        String printableOrder = orderService.generatePrintableOrder(1L);

        assertNotNull(printableOrder);
        assertTrue(printableOrder.contains("Beste John Doe"));
        assertTrue(printableOrder.contains("Bedankt voor uw bestelling bij Italian Bistro"));
        assertTrue(printableOrder.contains("Pizza - €9.99"));
        assertTrue(printableOrder.contains("Totaal: €9.99"));
    }
}
