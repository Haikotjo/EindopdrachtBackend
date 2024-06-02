package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.OrderDTO;
import nl.novi.eindopdrachtbackend.dto.OrderInputDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderInputDTO orderInputDTO);
    OrderDTO updateOrder(Long id, OrderInputDTO orderInputDTO);
    List<OrderDTO> getAllOrders();
    OrderDTO getOrderById(Long id);
    void deleteOrder(Long id);
    List<OrderDTO> findOrdersByCustomerId(Long customerId);
    List<OrderDTO> findOrdersByRestaurantId(Long restaurantId);

    String getUserNameById(Long userId);

    String getRestaurantNameById(Long restaurantId);

    String generatePrintableOrder(Long orderId);
}
