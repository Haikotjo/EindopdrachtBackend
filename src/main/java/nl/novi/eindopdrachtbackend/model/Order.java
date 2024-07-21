package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an order entity in the system.
 */
@Entity
@Table(name = "orders")
public class Order {

    /**
     * The unique identifier for the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The date and time when the order was placed.
     */
    private LocalDateTime orderDateTime;

    /**
     * Indicates whether the order is fulfilled or not.
     */
    private boolean fulfilled;

    /**
     * The customer who placed the order.
     * Establishes a many-to-one relationship with the User entity.
     */
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    /**
     * The restaurant from which the order was placed.
     * Establishes a many-to-one relationship with the Restaurant entity.
     */
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    /**
     * The delivery address for the order.
     * Establishes a many-to-one relationship with the DeliveryAddress entity.
     */
    @ManyToOne
    @JoinColumn(name = "delivery_address_id", nullable = false)
    private DeliveryAddress deliveryAddress;

    /**
     * The menu items included in the order.
     * Establishes a many-to-many relationship with the MenuItem entity.
     */
    @ManyToMany
    @JoinTable(
            name = "order_menu_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_item_id")
    )
    private Set<MenuItem> menuItems = new HashSet<>();

    /**
     * Default constructor.
     */
    public Order() {
    }

    /**
     * General constructor used for creating an order with specified details.
     *
     * @param customer        the customer who placed the order
     * @param restaurant      the restaurant from which the order was placed
     * @param deliveryAddress the delivery address for the order
     * @param fulfilled       whether the order is fulfilled or not
     */
    public Order(User customer, Restaurant restaurant, DeliveryAddress deliveryAddress, boolean fulfilled) {
        this.customer = customer;
        this.restaurant = restaurant;
        this.deliveryAddress = deliveryAddress;
        this.fulfilled = fulfilled;
        this.orderDateTime = LocalDateTime.now();
    }

    /**
     * Gets the ID of the order.
     *
     * @return the ID of the order
     */
    public Long getId() {
        return id;
    }

    /**
     * Checks if the order is fulfilled.
     *
     * @return true if the order is fulfilled, false otherwise
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
     * Gets the customer who placed the order.
     *
     * @return the customer who placed the order
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * Sets the customer who placed the order.
     *
     * @param customer the customer who placed the order
     */
    public void setCustomer(User customer) {
        this.customer = customer;
    }

    /**
     * Gets the restaurant from which the order was placed.
     *
     * @return the restaurant from which the order was placed
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     * Sets the restaurant from which the order was placed.
     *
     * @param restaurant the restaurant from which the order was placed
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * Gets the delivery address for the order.
     *
     * @return the delivery address for the order
     */
    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Sets the delivery address for the order.
     *
     * @param deliveryAddress the delivery address for the order
     */
    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Gets the menu items included in the order.
     *
     * @return the menu items included in the order
     */
    public Set<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * Sets the menu items included in the order.
     *
     * @param menuItems the menu items included in the order
     */
    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * Gets the total price of the order by summing the prices of all menu items.
     *
     * @return the total price of the order
     */
    public double getTotalPrice() {
        return menuItems.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
    }

    /**
     * Gets the date and time when the order was placed.
     *
     * @return the date and time when the order was placed
     */
    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    /**
     * Sets the date and time when the order was placed.
     *
     * @param orderDateTime the date and time when the order was placed
     */
    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
}
