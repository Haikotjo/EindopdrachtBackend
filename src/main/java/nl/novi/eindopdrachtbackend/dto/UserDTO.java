package nl.novi.eindopdrachtbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Data Transfer Object for User.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<String> roles;
    private String phoneNumber;
    private DeliveryAddressDTO deliveryAddress;
    private RestaurantDTO restaurant;
    private List<OrderDTO> orders;
    private List<IngredientDTO> ingredients;

    // Constructors

    /**
     * Default constructor.
     */
    public UserDTO() {}

    /**
     * Constructor for basic user information.
     *
     * @param id the ID of the user
     * @param name the name of the user
     * @param email the email of the user
     * @param phoneNumber the phone number of the user
     */
    public UserDTO(Long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor for full user information.
     *
     * @param id the ID of the user
     * @param name the name of the user
     * @param email the email of the user
     * @param roles the roles of the user
     * @param phoneNumber the phone number of the user
     * @param deliveryAddress the delivery address of the user
     * @param restaurant the restaurant owned by the user
     * @param orders the orders made by the user
     * @param ingredients the ingredients owned by the user
     */
    public UserDTO(Long id, String name, String email, List<String> roles, String phoneNumber, DeliveryAddressDTO deliveryAddress, RestaurantDTO restaurant, List<OrderDTO> orders, List<IngredientDTO> ingredients) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
        this.deliveryAddress = deliveryAddress;
        this.restaurant = restaurant;
        this.orders = orders;
        this.ingredients = ingredients;
    }

    // Getters and Setters

    /**
     * Gets the ID of the user.
     *
     * @return the ID of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the ID of the user
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     *
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the roles of the user.
     *
     * @return the roles of the user
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Sets the roles of the user.
     *
     * @param roles the roles of the user
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return the phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the delivery address of the user.
     *
     * @return the delivery address of the user
     */
    public DeliveryAddressDTO getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Sets the delivery address of the user.
     *
     * @param deliveryAddress the delivery address of the user
     */
    public void setDeliveryAddress(DeliveryAddressDTO deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Gets the restaurant owned by the user.
     *
     * @return the restaurant owned by the user
     */
    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    /**
     * Sets the restaurant owned by the user.
     *
     * @param restaurant the restaurant owned by the user
     */
    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * Gets the orders made by the user.
     *
     * @return the orders made by the user
     */
    public List<OrderDTO> getOrders() {
        return orders;
    }

    /**
     * Sets the orders made by the user.
     *
     * @param orders the orders made by the user
     */
    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    /**
     * Gets the ingredients owned by the user.
     *
     * @return the ingredients owned by the user
     */
    public List<IngredientDTO> getIngredients() {
        return ingredients;
    }

    /**
     * Sets the ingredients owned by the user.
     *
     * @param ingredients the ingredients owned by the user
     */
    public void setIngredients(List<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
