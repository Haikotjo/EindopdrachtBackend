package nl.novi.eindopdrachtbackend.dto;

import java.util.List;

public class OrderDTO {
    private Long id;
    private boolean fulfilled;
    private Long customerId;
    private Long restaurantId;
    private DeliveryAddressDTO deliveryAddress;
    private List<MenuItemDTO> menuItems;
    private double totalPrice;

    // Constructors
    public OrderDTO() {
    }

    public OrderDTO(Long id, boolean fulfilled, Long customerId, Long restaurantId, DeliveryAddressDTO deliveryAddress, List<MenuItemDTO> menuItems, double totalPrice) {
        this.id = id;
        this.fulfilled = fulfilled;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.deliveryAddress = deliveryAddress;
        this.menuItems = menuItems;
        this.totalPrice = totalPrice;
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

    public DeliveryAddressDTO getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddressDTO deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<MenuItemDTO> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemDTO> menuItems) {
        this.menuItems = menuItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
