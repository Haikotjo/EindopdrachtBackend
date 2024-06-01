package nl.novi.eindopdrachtbackend.dto;

import java.util.List;

public class OrderInputDTO {
    private boolean fulfilled;
    private Long customerId;
    private Long restaurantId;
    private Long deliveryAddressId;
    private List<Long> menuItemIds;

    // Constructors
    public OrderInputDTO() {
    }

    public OrderInputDTO(boolean fulfilled, Long customerId, Long restaurantId, Long deliveryAddressId, List<Long> menuItemIds) {
        this.fulfilled = fulfilled;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddressId = deliveryAddressId;
        this.menuItemIds = menuItemIds;
    }

    // Getters and Setters
    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Long deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public List<Long> getMenuItemIds() {
        return menuItemIds;
    }

    public void setMenuItemIds(List<Long> menuItemIds) {
        this.menuItemIds = menuItemIds;
    }
}
