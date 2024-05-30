package nl.novi.eindopdrachtbackend.dto;

public class OrderDTO {
    private Long id;
    private boolean fulfilled;
    private Long customerId;
    private Long restaurantId;
    private Long deliveryAddressId;

    // Constructors
    public OrderDTO() {
    }

    public OrderDTO(Long id, boolean fulfilled, Long customerId, Long restaurantId, Long deliveryAddressId) {
        this.id = id;
        this.fulfilled = fulfilled;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddressId = deliveryAddressId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
