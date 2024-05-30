package nl.novi.eindopdrachtbackend.dto;

public class OrderInputDTO {
    private boolean fulfilled;
    private Long customerId;
    private Long restaurantId;
    private Long deliveryAddressId;

    // Constructors
    public OrderInputDTO() {
    }

    public OrderInputDTO(boolean fulfilled, Long customerId, Long restaurantId, Long deliveryAddressId) {
        this.fulfilled = fulfilled;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddressId = deliveryAddressId;
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
}
