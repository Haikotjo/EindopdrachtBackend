package nl.novi.eindopdrachtbackend.dto;

/**
 * Data Transfer Object for Restaurant input.
 */
public class RestaurantInputDTO {
    private String name;
    private String address;
    private String phoneNumber;

    // Getters en Setters

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
}
