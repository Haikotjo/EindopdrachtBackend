package nl.novi.eindopdrachtbackend.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;
    private String address;
    private String phoneNumber;

    // Constructors
    public UserDTO() {}

    public UserDTO(Long id, String name, String email, String role, String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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
}
