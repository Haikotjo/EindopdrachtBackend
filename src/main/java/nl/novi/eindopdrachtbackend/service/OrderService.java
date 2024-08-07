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

    /**
     * Create a new order for the logged-in user (customer or admin).
     *
     * @param userId the ID of the logged-in user
     * @param orderInputDTO the order data transfer object
     * @return the created OrderDTO
     */
    OrderDTO createOrder(Long userId, OrderInputDTO orderInputDTO);


    /**
     * Update an order for a customer.
     *
     * @param orderId the ID of the order to update
     * @param orderInputDTO the order data to update
     * @param customerId the ID of the customer
     * @return updated OrderDTO
     */
    OrderDTO updateOrderForCustomer(Long orderId, OrderInputDTO orderInputDTO, Long customerId);

    /**
     * Update an order for an admin.
     *
     * @param orderId the ID of the order to update
     * @param orderInputDTO the order data to update
     * @return updated OrderDTO
     */
    OrderDTO updateOrderForAdmin(Long orderId, OrderInputDTO orderInputDTO);

    /**
     * Delete an order by its ID.
     *
     * @param id the ID of the order to delete
     */
    void deleteOrder(Long id);

    /**
     * Generate a printable version of the order.
     *
     * @param orderId the ID of the order
     * @param date the date and time for the order
     * @return a formatted string representing the printable order
     */
    String generatePrintableOrder(Long orderId, LocalDateTime date);

    /**
     * Generate a printable summary of orders for a specific restaurant on a given date.
     *
     * @param ownerId the ID of the owner
     * @param date the date for which the summary is to be generated
     * @return a formatted string representing the daily summary of orders
     */
    String generatePrintableDailySummary(Long ownerId, LocalDateTime date);
    }

