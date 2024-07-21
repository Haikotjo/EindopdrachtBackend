package nl.novi.eindopdrachtbackend.dto;

import java.util.List;

/**
 * Data Transfer Object for Order input.
 */
public class OrderInputDTO {
    private boolean fulfilled;
    private Long customerId;
    private Long restaurantId;
    private Long deliveryAddressId;
    private List<Long> menuItemIds;

    // Constructors

    /**
     * Default constructor.
     */
    public OrderInputDTO() {
    }

    /**
     * Constructor for OrderInputDTO.
     *
     * @param fulfilled the fulfillment status of the order
     * @param customerId the ID of the customer
     * @param restaurantId the ID of the restaurant
     * @param deliveryAddressId the ID of the delivery address
     * @param menuItemIds the list of menu item IDs in the order
     */
    public OrderInputDTO(boolean fulfilled, Long customerId, Long restaurantId, Long deliveryAddressId, List<Long> menuItemIds) {
        this.fulfilled = fulfilled;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddressId = deliveryAddressId;
        this.menuItemIds = menuItemIds;
    }

    // Getters and Setters

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
     * Gets the ID of the delivery address.
     *
     * @return the ID of the delivery address
     */
    public Long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    /**
     * Sets the ID of the delivery address.
     *
     * @param deliveryAddressId the ID of the delivery address
     */
    public void setDeliveryAddressId(Long deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    /**
     * Gets the list of menu item IDs in the order.
     *
     * @return the list of menu item IDs in the order
     */
    public List<Long> getMenuItemIds() {
        return menuItemIds;
    }

    /**
     * Sets the list of menu item IDs in the order.
     *
     * @param menuItemIds the list of menu item IDs in the order
     */
    public void setMenuItemIds(List<Long> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }
}
