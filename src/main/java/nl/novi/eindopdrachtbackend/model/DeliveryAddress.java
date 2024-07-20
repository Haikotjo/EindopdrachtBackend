package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a delivery address entity in the system.
 */
@Entity
@Table(name = "deliveryAddresses")
public class DeliveryAddress {

    /**
     * The unique identifier for the delivery address.
     */
    @Id
    private Long id;

    /**
     * The street name of the delivery address.
     */
    @NotBlank(message = "Street is mandatory")
    private String street;

    /**
     * The house number of the delivery address.
     */
    @NotNull(message = "House number is mandatory")
    private int houseNumber;

    /**
     * The city of the delivery address.
     */
    @NotBlank(message = "City is mandatory")
    private String city;

    /**
     * The postcode of the delivery address.
     */
    @NotBlank(message = "Postcode is mandatory")
    private String postcode;

    /**
     * The country of the delivery address.
     */
    @NotBlank(message = "Country is mandatory")
    private String country;

    /**
     * Default constructor.
     */
    public DeliveryAddress() {
    }

    /**
     * General constructor used for creating a delivery address with specified details.
     *
     * @param street      the street name of the delivery address
     * @param houseNumber the house number of the delivery address
     * @param city        the city of the delivery address
     * @param postcode    the postcode of the delivery address
     * @param country     the country of the delivery address
     */
    public DeliveryAddress(String street, int houseNumber, String city, String postcode, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
    }

    /**
     * The user associated with this delivery address.
     * This establishes a one-to-one relationship with the User entity.
     */
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The orders associated with this delivery address.
     * This establishes a one-to-many relationship with the Order entity.
     */
    @OneToMany(mappedBy = "deliveryAddress", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    /**
     * Gets the orders associated with this delivery address.
     *
     * @return the orders associated with this delivery address
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Adds an order to the list of orders associated with this delivery address.
     *
     * @param order the order to add
     */
    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
        order.setDeliveryAddress(this);
    }

    /**
     * Removes an order from the list of orders associated with this delivery address.
     *
     * @param order the order to remove
     */
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setDeliveryAddress(null);
    }

    /**
     * Gets the ID of the delivery address.
     *
     * @return the ID of the delivery address
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets the street name of the delivery address.
     *
     * @return the street name of the delivery address
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the street name of the delivery address.
     *
     * @param street the street name of the delivery address
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the house number of the delivery address.
     *
     * @return the house number of the delivery address
     */
    public int getHouseNumber() {
        return houseNumber;
    }

    /**
     * Sets the house number of the delivery address.
     *
     * @param houseNumber the house number of the delivery address
     */
    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Gets the city of the delivery address.
     *
     * @return the city of the delivery address
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the delivery address.
     *
     * @param city the city of the delivery address
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the postcode of the delivery address.
     *
     * @return the postcode of the delivery address
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * Sets the postcode of the delivery address.
     *
     * @param postcode the postcode of the delivery address
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Gets the country of the delivery address.
     *
     * @return the country of the delivery address
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the delivery address.
     *
     * @param country the country of the delivery address
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Gets the user associated with this delivery address.
     *
     * @return the user associated with this delivery address
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this delivery address.
     *
     * @param user the user associated with this delivery address
     */
    public void setUser(User user) {
        this.user = user;
    }
}
