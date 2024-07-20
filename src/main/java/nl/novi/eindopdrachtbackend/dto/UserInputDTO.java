package nl.novi.eindopdrachtbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * Data Transfer Object for User input.
 */
public class UserInputDTO {
    /**
     * The name of the user.
     * This field is mandatory.
     */
    @NotEmpty(message = "Name is required")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    /**
     * The email of the user.
     * This field is mandatory and should be a valid email format.
     */
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;

    /**
     * The password of the user.
     * This field is mandatory and should have at least 4 characters.
     */
    @NotEmpty(message = "Password is required")
    @Size(min = 4, message = "Password should have at least 4 characters")
    private String password;

    /**
     * The roles of the user.
     */
    private List<String> roles;

    /**
     * The phone number of the user.
     * This field should be 10 digits.
     */
    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    private String phoneNumber;

    // Getters en Setters

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
}
