package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a user entity in the system.
 */
@Entity
@Table(name = "users")
public class User {
    /**
     * The unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 3, message = "Password must be at least 3 characters long")
    @Column(nullable = false)
    private String password;

    /**
     * The roles assigned to the user.
     * This establishes a many-to-many relationship with the Role entity.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_rolename")
    )
    private Collection<Role> roles = new HashSet<>();

    /**
     * The phone number of the user.
     */
    private String phoneNumber;

    /**
     * The orders associated with the user.
     * This establishes a one-to-many relationship with the Order entity.
     */
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    /**
     * The delivery address associated with the user.
     * This establishes a one-to-one relationship with the DeliveryAddress entity.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = true)
    private DeliveryAddress deliveryAddress;

    /**
     * The restaurant owned by the user.
     * This establishes a one-to-one relationship with the Restaurant entity.
     */
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Restaurant restaurant;

    /**
     * The ingredients owned by the user.
     * This establishes a one-to-many relationship with the Ingredient entity.
     */
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ingredient> ingredients = new HashSet<>();

    // Constructors

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * General constructor used for creating a user with specified details.
     *
     * @param name        the name of the user
     * @param email       the email of the user
     * @param password    the password of the user
     * @param roles       the roles assigned to the user
     * @param phoneNumber the phone number of the user
     */
    public User(String name, String email, String password, Collection<Role> roles, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
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
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the roles assigned to the user.
     *
     * @return the roles assigned to the user
     */
    public Collection<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the roles assigned to the user.
     *
     * @param roles the roles assigned to the user
     */
    public void setRoles(Collection<Role> roles) {
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
     * Gets the orders associated with the user.
     *
     * @return the orders associated with the user
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Sets the orders associated with the user.
     *
     * @param orders the orders associated with the user
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    /**
     * Gets the delivery address associated with the user.
     *
     * @return the delivery address associated with the user
     */
    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Sets the delivery address associated with the user.
     *
     * @param deliveryAddress the delivery address associated with the user
     */
    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        if (deliveryAddress != null) {
            deliveryAddress.setUser(this);
        }
    }

    /**
     * Gets the restaurant owned by the user.
     *
     * @return the restaurant owned by the user
     */
    public Restaurant getRestaurant() {
        return restaurant;
    }

    /**
     * Sets the restaurant owned by the user.
     *
     * @param restaurant the restaurant owned by the user
     */
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    /**
     * Gets the ingredients owned by the user.
     *
     * @return the ingredients owned by the user
     */
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Sets the ingredients owned by the user.
     *
     * @param ingredients the ingredients owned by the user
     */
    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
