package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a restaurant entity in the system.
 */
@Entity
@Table(name = "restaurants")
public class Restaurant {
    /**
     * The unique identifier for the restaurant.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the restaurant.
     */
    private String name;

    /**
     * The address of the restaurant.
     */
    private String address;

    /**
     * The phone number of the restaurant.
     */
    private String phoneNumber;

    /**
     * The owner of the restaurant.
     */
    @OneToOne(optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    /**
     * The set of menus offered by the restaurant.
     */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Menu> menus = new HashSet<>();

    /**
     * The set of orders associated with the restaurant.
     */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    /**
     * The set of menu items offered by the restaurant.
     */
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MenuItem> menuItems = new HashSet<>();

    // Constructors

    /**
     * Default constructor.
     */
    public Restaurant() {
    }

    /**
     * Constructor used when creating a restaurant with specified name, address, and phone number.
     *
     * @param name        the name of the restaurant
     * @param address     the address of the restaurant
     * @param phoneNumber the phone number of the restaurant
     */
    public Restaurant(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructor used for detailed restaurant creation.
     *
     * @param name        the name of the restaurant
     * @param address     the address of the restaurant
     * @param phoneNumber the phone number of the restaurant
     * @param menus       the set of menus offered by the restaurant
     * @param orders      the set of orders associated with the restaurant
     * @param owner       the owner of the restaurant
     */
    public Restaurant(String name, String address, String phoneNumber, Set<Menu> menus, Set<Order> orders, User owner) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.menus = menus != null ? menus : new HashSet<>();
        this.orders = orders != null ? orders : new HashSet<>();
        this.owner = owner;
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
    public User getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the restaurant.
     *
     * @param owner the owner of the restaurant
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Gets the set of menus offered by the restaurant.
     *
     * @return the set of menus offered by the restaurant
     */
    public Set<Menu> getMenus() {
        if (menus == null) {
            menus = new HashSet<>();
        }
        return menus;
    }

    /**
     * Sets the set of menus offered by the restaurant.
     *
     * @param menus the set of menus offered by the restaurant
     */
    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    /**
     * Adds a menu to the set of menus offered by the restaurant.
     *
     * @param menu the menu to add
     */
    public void addMenu(Menu menu) {
        this.getMenus().add(menu);
        menu.setRestaurant(this);
    }

    /**
     * Gets the set of orders associated with the restaurant.
     *
     * @return the set of orders associated with the restaurant
     */
    public Set<Order> getOrders() {
        if (orders == null) {
            orders = new HashSet<>();
        }
        return orders;
    }

    /**
     * Sets the set of orders associated with the restaurant.
     *
     * @param orders the set of orders associated with the restaurant
     */
    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    /**
     * Adds an order to the set of orders associated with the restaurant.
     *
     * @param order the order to add
     */
    public void addOrder(Order order) {
        orders.add(order);
        order.setRestaurant(this);
    }

    /**
     * Removes an order from the set of orders associated with the restaurant.
     *
     * @param order the order to remove
     */
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setRestaurant(null);
    }

    /**
     * Gets the set of menu items offered by the restaurant.
     *
     * @return the set of menu items offered by the restaurant
     */
    public Set<MenuItem> getMenuItems() {
        if (menuItems == null) {
            menuItems = new HashSet<>();
        }
        return menuItems;
    }

    /**
     * Sets the set of menu items offered by the restaurant.
     *
     * @param menuItems the set of menu items offered by the restaurant
     */
    public void setMenuItems(Set<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }
}
