package nl.novi.eindopdrachtbackend.dto;

/**
 * Data Transfer Object for Delivery Address input.
 */
public class DeliveryAddressInputDTO {
    private String street;
    private int houseNumber;
    private String city;
    private String postcode;
    private String country;

    // Constructors

    /**
     * Default constructor.
     */
    public DeliveryAddressInputDTO() {}

    /**
     * Constructor for DeliveryAddressInputDTO.
     *
     * @param street the street name of the delivery address
     * @param houseNumber the house number of the delivery address
     * @param city the city of the delivery address
     * @param postcode the postcode of the delivery address
     * @param country the country of the delivery address
     */
    public DeliveryAddressInputDTO(String street, int houseNumber, String city, String postcode, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
    }

    // Getters and Setters

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
}
