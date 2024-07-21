package nl.novi.eindopdrachtbackend.service;

import nl.novi.eindopdrachtbackend.dto.OrderDTO;
import nl.novi.eindopdrachtbackend.dto.OrderInputDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
        /**
         * Get all orders (Admin only).
         *
         * @return list of all OrderDTOs
         */
        List<OrderDTO> getAllOrders();

    /**
     * Get a specific order by its ID (Admin only).
     *
     * @param id the ID of the order
     * @return the OrderDTO for the specified ID
     */
    OrderDTO getOrderByIdForAdmin(Long id);

    /**
     * Get orders for the logged-in customer.
     *
     * @param userId user ID of the logged-in customer
     * @return list of OrderDTOs for the specified user
     */
    List<OrderDTO> getOrdersForCustomer(Long userId);

    /**
     * Retrieves all orders for the restaurant of a specific owner.
     *
     * @param ownerId the ID of the restaurant owner
     * @return a list of OrderDTOs for the specified restaurant
     */
    List<OrderDTO> getOrdersForOwner(Long ownerId);

    /**
     * Get a specific order by ID for a customer.
     *
     * @param orderId the ID of the order
     * @param customerId the ID of the customer
     * @return the OrderDTO object for the specified order
     */
    OrderDTO getOrderByIdForCustomer(Long orderId, Long customerId);

    /**
     * Get a specific order by ID for a restaurant owner.
     *
     * @param orderId the ID of the order
     * @param ownerId the ID of the restaurant owner
     * @return the OrderDTO object for the specified order
     */
    OrderDTO getOrderByIdForOwner(Long orderId, Long ownerId);



//    OrderDTO createOrder(OrderInputDTO orderInputDTO);
//    OrderDTO updateOrder(Long id, OrderInputDTO orderInputDTO);
//    void deleteOrder(Long id);
//    List<OrderDTO> findOrdersByCustomerId(Long customerId);
//    List<OrderDTO> findOrdersByRestaurantId(Long restaurantId);
//    List<OrderDTO> findOrdersByDate(LocalDateTime date);
//
//    String getUserNameById(Long userId);
//
//    String getRestaurantNameById(Long restaurantId);
//
//    String generatePrintableOrder(Long orderId, LocalDateTime date);
//    List<OrderDTO> findOrdersByRestaurantAndDate(Long restaurantId, LocalDateTime date);
//
//    String generatePrintableDailySummary(Long restaurantId, LocalDateTime date);
    }

