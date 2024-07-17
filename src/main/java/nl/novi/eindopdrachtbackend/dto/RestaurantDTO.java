package nl.novi.eindopdrachtbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

/**
 * Data Transfer Object for Restaurant.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private UserDTO owner;
    private Set<MenuDTO> menus;
    private Set<OrderDTO> orders;

    // Constructors

    /**
     * Default constructor.
     */
    public RestaurantDTO() {
    }

    /**
     * Constructor for RestaurantDTO with all fields.
     *
     * @param id          the ID of the restaurant
     * @param name        the name of the restaurant
     * @param address     the address of the restaurant
     * @param phoneNumber the phone number of the restaurant
     * @param owner       the owner of the restaurant
     * @param menus       the set of menus offered by the restaurant
     * @param orders      the set of orders associated with the restaurant
     */
    public RestaurantDTO(Long id, String name, String address, String phoneNumber, UserDTO owner, Set<MenuDTO> menus, Set<OrderDTO> orders) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.owner = owner;
        this.menus = menus;
        this.orders = orders;
    }

    /**
     * Constructor for simplified RestaurantDTO.
     *
     * @param name        the name of the restaurant
     * @param address     the address of the restaurant
     * @param phoneNumber the phone number of the restaurant
     * @param menus       the set of menus offered by the restaurant
     */
    public RestaurantDTO(String name, String address, String phoneNumber, Set<MenuDTO> menus) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.menus = menus;
    }

    // Getters and Setters

    /**
     * Gets the ID of the restaurant.
     *
     * @return the ID of the restaurant
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the restaurant.
     *
     * @param id the ID of the restaurant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the restaurant.
     *
     * @return the name of the restaurant
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the restaurant.
     *
     * @param name the name of the restaurant
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address of the restaurant.
     *
     * @return the address of the restaurant
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the restaurant.
     *
     * @param address the address of the restaurant
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the phone number of the restaurant.
     *
     * @return the phone number of the restaurant
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the restaurant.
     *
     * @param phoneNumber the phone number of the restaurant
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the owner of the restaurant.
     *
     * @return the owner of the restaurant
     */
    public UserDTO getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the restaurant.
     *
     * @param owner the owner of the restaurant
     */
    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    /**
     * Gets the set of menus offered by the restaurant.
     *
     * @return the set of menus offered by the restaurant
     */
    public Set<MenuDTO> getMenus() {
        return menus;
    }

    /**
     * Sets the set of menus offered by the restaurant.
     *
     * @param menus the set of menus offered by the restaurant
     */
    public void setMenus(Set<MenuDTO> menus) {
        this.menus = menus;
    }

    /**
     * Gets the set of orders associated with the restaurant.
     *
     * @return the set of orders associated with the restaurant
     */
    public Set<OrderDTO> getOrders() {
        return orders;
    }

    /**
     * Sets the set of orders associated with the restaurant.
     *
     * @param orders the set of orders associated with the restaurant
     */
    public void setOrders(Set<OrderDTO> orders) {
        this.orders = orders;
    }
}
