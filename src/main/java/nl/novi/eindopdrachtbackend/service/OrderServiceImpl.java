package nl.novi.eindopdrachtbackend.service.impl;

import nl.novi.eindopdrachtbackend.exception.ResourceNotFoundException;
import nl.novi.eindopdrachtbackend.model.Order;
import nl.novi.eindopdrachtbackend.repository.OrderRepository;
import nl.novi.eindopdrachtbackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));

        order.setFulfilled(orderDetails.isFulfilled());
        // Voeg hier meer velden toe als dat nodig is.

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = getOrderById(id); // Gebruik de reeds gedefinieerde methode om herhaling te voorkomen.
        orderRepository.delete(order);
    }

    @Override
    public List<Order> findOrdersByCustomerId(Long customerId) {
        return orderRepository.findOrdersByCustomerId(customerId);
    }

    @Override
    public List<Order> findOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findOrdersByRestaurantId(restaurantId);
    }
}
