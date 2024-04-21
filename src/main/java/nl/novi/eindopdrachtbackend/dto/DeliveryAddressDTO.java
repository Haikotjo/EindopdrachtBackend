package nl.novi.eindopdrachtbackend.dto;

public class DeliveryAddressDTO {
    private Long id;
    private String street;
    private int houseNumber;
    private String city;
    private String postcode;
    private String country;
    private Long userId; // Optioneel, afhankelijk van of je de gekoppelde gebruiker wilt identificeren

    // Constructors
    public DeliveryAddressDTO() {}

    public DeliveryAddressDTO(Long id, String street, int houseNumber, String city, String postcode, String country, Long userId) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.userId = userId;
    }

    // Getters en setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
