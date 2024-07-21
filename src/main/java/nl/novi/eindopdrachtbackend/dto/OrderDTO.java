package nl.novi.eindopdrachtbackend.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for Order.
 */
public class OrderDTO {
    private Long id;
    private boolean fulfilled;
    private Long customerId;
    private Long restaurantId;
    private DeliveryAddressDTO deliveryAddress;
    private List<MenuItemDTO> menuItems;
    private double totalPrice;
    private LocalDateTime orderDateTime;

    // Constructors

    /**
     * Default constructor.
     */
    public OrderDTO() {
    }

    /**
     * Constructor for OrderDTO.
     *
     * @param id the ID of the order
     * @param fulfilled the fulfillment status of the order
     * @param customerId the ID of the customer
     * @param restaurantId the ID of the restaurant
     * @param deliveryAddress the delivery address associated with the order
     * @param menuItems the list of menu items in the order
     * @param totalPrice the total price of the order
     * @param orderDateTime the date and time of the order
     */
    public OrderDTO(Long id, boolean fulfilled, Long customerId, Long restaurantId, DeliveryAddressDTO deliveryAddress, List<MenuItemDTO> menuItems, double totalPrice, LocalDateTime orderDateTime) {
        this.id = id;
        this.fulfilled = fulfilled;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddress = deliveryAddress;
        this.menuItems = menuItems;
        this.totalPrice = totalPrice;
        this.orderDateTime = orderDateTime;
    }

    // Getters and Setters

    /**
     * Gets the ID of the order.
     *
     * @return the ID of the order
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param id the ID of the order
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the fulfillment status of the order.
     *
     * @return the fulfillment status of the order
     */
    public boolean isFulfilled() {
        return fulfilled;
    }

    /**
     * Sets the fulfillment status of the order.
     *
     * @param fulfilled the fulfillment status of the order
     */
    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    /**
     * Gets the ID of the customer.
     *
     * @return the ID of the customer
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the ID of the customer.
     *
     * @param customerId the ID of the customer
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the ID of the restaurant.
     *
     * @return the ID of the restaurant
     */
    public Long getRestaurantId() {
        return restaurantId;
    }

    /**
     * Sets the ID of the restaurant.
     *
     * @param restaurantId the ID of the restaurant
     */
    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    /**
     * Gets the delivery address associated with the order.
     *
     * @return the delivery address associated with the order
     */
    public DeliveryAddressDTO getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Sets the delivery address associated with the order.
     *
     * @param deliveryAddress the delivery address associated with the order
     */
    public void setDeliveryAddress(DeliveryAddressDTO deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Gets the list of menu items in the order.
     *
     * @return the list of menu items in the order
     */
    public List<MenuItemDTO> getMenuItems() {
        return menuItems;
    }

    /**
     * Sets the list of menu items in the order.
     *
     * @param menuItems the list of menu items in the order
     */
    public void setMenuItems(List<MenuItemDTO> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * Gets the total price of the order.
     *
     * @return the total price of the order
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the order.
     *
     * @param totalPrice the total price of the order
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Gets the date and time of the order.
     *
     * @return the date and time of the order
     */
    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    /**
     * Sets the date and time of the order.
     *
     * @param orderDateTime the date and time of the order
     */
    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
}
