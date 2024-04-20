package nl.novi.eindopdrachtbackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String address;
    private String phoneNumber;

    //Relation to orders
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }

    //Relation to DeliveryAddress

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private DeliveryAddress deliveryAddress;

    //    constructors

    public enum Role {
        CUSTOMER,
        OWNER
    }

    public User() {
    }

    public User(String name, String email, String password, Role role, String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public User(String name, String email, String password, Role role, String address, String phoneNumber, List<Order> orders, DeliveryAddress deliveryAddress) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orders = orders;
        this.deliveryAddress = deliveryAddress;
    }

// getters and setters


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Order> getOrders() {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}


