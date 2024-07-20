package nl.novi.eindopdrachtbackend.dto;

/**
 * Data Transfer Object for DeliveryAddress.
 */
public class DeliveryAddressDTO {
    private Long id;
    private String street;
    private int houseNumber;
    private String city;
    private String postcode;
    private String country;
    private Long userId;

    // Constructors

    /**
     * Default constructor.
     */
    public DeliveryAddressDTO() {}

    /**
     * Constructor for DeliveryAddressDTO.
     *
     * @param id the ID of the delivery address
     * @param street the street name of the delivery address
     * @param houseNumber the house number of the delivery address
     * @param city the city of the delivery address
     * @param postcode the postcode of the delivery address
     * @param country the country of the delivery address
     * @param userId the ID of the user associated with the delivery address
     */
    public DeliveryAddressDTO(Long id, String street, int houseNumber, String city, String postcode, String country, Long userId) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.userId = userId;
    }

    // Getters and Setters

    /**
     * Gets the ID of the delivery address.
     *
     * @return the ID of the delivery address
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the delivery address.
     *
     * @param id the ID of the delivery address
     */
    public void setId(Long id) {
        this.id = id;
    }

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

    /**
     * Gets the user ID associated with the delivery address.
     *
     * @return the user ID associated with the delivery address
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID associated with the delivery address.
     *
     * @param userId the user ID associated with the delivery address
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
