package nl.novi.eindopdrachtbackend.dto;

import nl.novi.eindopdrachtbackend.model.UserRole;

import java.util.List;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<String> roles;
    private String phoneNumber;
    private DeliveryAddressDTO deliveryAddress;
    private List<OrderDTO> orders;

    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String name, String email, List<String> roles, String phoneNumber, DeliveryAddressDTO deliveryAddress, List<OrderDTO> orders) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
        this.deliveryAddress = deliveryAddress;
        this.orders = orders;
    }

    // Getters en setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public DeliveryAddressDTO getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddressDTO deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
