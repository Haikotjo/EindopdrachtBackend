package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.OrderDTO;
import nl.novi.eindopdrachtbackend.dto.OrderInputDTO;
import nl.novi.eindopdrachtbackend.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private OrderDTO order1;
    private OrderDTO order2;
    private OrderInputDTO orderInputDTO;
    private LocalDateTime orderDateTime;

    @BeforeEach
    void setUp() {
        List<Long> menuItemIds = new ArrayList<>();
        menuItemIds.add(1L);

        orderDateTime = LocalDateTime.now();
        order1 = new OrderDTO(1L, true, 1L, 1L, null, new ArrayList<>(), 9.99, orderDateTime);
        order2 = new OrderDTO(2L, false, 1L, 1L, null, new ArrayList<>(), 9.99, orderDateTime);
        orderInputDTO = new OrderInputDTO(true, 1L, 1L, 1L, menuItemIds);
    }

    @Test
    void getAllOrders_ShouldReturnOrders() {
        List<OrderDTO> expectedOrders = new ArrayList<>();
        expectedOrders.add(order1);
        expectedOrders.add(order2);
        when(orderService.getAllOrders()).thenReturn(expectedOrders);

        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getOrderById_ShouldReturnOrder() {
        when(orderService.getOrderById(1L)).thenReturn(order1);

        ResponseEntity<OrderDTO> response = orderController.getOrderById(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void createOrder_ShouldCreateOrder() {
        when(orderService.createOrder(orderInputDTO)).thenReturn(order1);

        ResponseEntity<OrderDTO> response = orderController.createOrder(orderInputDTO);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void updateOrder_ShouldUpdateOrder() {
        when(orderService.updateOrder(1L, orderInputDTO)).thenReturn(order1);

        ResponseEntity<OrderDTO> response = orderController.updateOrder(1L, orderInputDTO);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteOrder_ShouldReturnNoContent() {
        doNothing().when(orderService).deleteOrder(1L);

        ResponseEntity<Void> response = orderController.deleteOrder(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void findOrdersByCustomerId_ShouldReturnOrders() {
        List<OrderDTO> expectedOrders = new ArrayList<>();
        expectedOrders.add(order1);
        when(orderService.findOrdersByCustomerId(1L)).thenReturn(expectedOrders);

        ResponseEntity<List<OrderDTO>> response = orderController.findOrdersByCustomerId(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void findOrdersByRestaurantId_ShouldReturnOrders() {
        List<OrderDTO> expectedOrders = new ArrayList<>();
        expectedOrders.add(order2);
        when(orderService.findOrdersByRestaurantId(1L)).thenReturn(expectedOrders);

        ResponseEntity<List<OrderDTO>> response = orderController.findOrdersByRestaurantId(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void printOrder_ShouldReturnPrintableOrder() {
        String expectedPrint = "Beste John Doe,\n" +
                "Bedankt voor uw bestelling bij Italian Bistro.\n" +
                "Uw bestelling:\n" +
                "Pizza - €9.99\n" +
                "Totaal: €9.99\n" +
                "Besteld op: " + orderDateTime + "\n"; // Include order date and time

        lenient().when(orderService.generatePrintableOrder(eq(1L), any(LocalDateTime.class))).thenReturn(expectedPrint);

        ResponseEntity<String> response = orderController.printOrder(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedPrint, response.getBody());
    }

    @Test
    void printOrdersByDate_ShouldReturnPrintableOrders() {
        List<OrderDTO> expectedOrders = new ArrayList<>();
        expectedOrders.add(order1);
        when(orderService.findOrdersByDate(orderDateTime)).thenReturn(expectedOrders);

        String expectedPrint = "Beste John Doe,\n" +
                "Bedankt voor uw bestelling bij Italian Bistro.\n" +
                "Uw bestelling:\n" +
                "Pizza - €9.99\n" +
                "Totaal: €9.99\n" +
                "Besteld op: " + orderDateTime + "\n"; // Include order date and time

        lenient().when(orderService.generatePrintableOrder(eq(1L), any(LocalDateTime.class))).thenReturn(expectedPrint);

        ResponseEntity<List<String>> response = orderController.printOrdersByDate(orderDateTime);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(expectedPrint, response.getBody().get(0));
    }
}
