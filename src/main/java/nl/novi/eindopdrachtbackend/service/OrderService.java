package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.model.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order updateOrder(Long id, Order orderDetails);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    void deleteOrder(Long id);
    List<Order> findOrdersByCustomerId(Long customerId);
    List<Order> findOrdersByRestaurantId(Long restaurantId);
}
