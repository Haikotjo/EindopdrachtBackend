package nl.novi.eindopdrachtbackend.controller;

import nl.novi.eindopdrachtbackend.dto.*;
import nl.novi.eindopdrachtbackend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderInputDTO orderInputDTO) {
        OrderDTO createdOrder = orderService.createOrder(orderInputDTO);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderInputDTO orderInputDTO) {
        OrderDTO updatedOrder = orderService.updateOrder(id, orderInputDTO);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> findOrdersByCustomerId(@PathVariable Long customerId) {
        List<OrderDTO> orders = orderService.findOrdersByCustomerId(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderDTO>> findOrdersByRestaurantId(@PathVariable Long restaurantId) {
        List<OrderDTO> orders = orderService.findOrdersByRestaurantId(restaurantId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @GetMapping("/print")
    public ResponseEntity<List<String>> printOrdersByDate(@RequestParam LocalDateTime date) {
        List<OrderDTO> orders = orderService.findOrdersByDate(date);
        List<String> printableOrders = orders.stream()
                .map(order -> orderService.generatePrintableOrder(order.getId(), date))
                .collect(Collectors.toList());
        return new ResponseEntity<>(printableOrders, HttpStatus.OK);
    }
    // In OrderController.java
    @GetMapping("/orders/{id}/print")
    public ResponseEntity<String> printOrder(@PathVariable Long id) {
        LocalDateTime now = LocalDateTime.now();
        String printableOrder = orderService.generatePrintableOrder(id, now);
        return new ResponseEntity<>(printableOrder, HttpStatus.OK);
    }


}
