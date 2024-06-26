package nl.novi.eindopdrachtbackend.dto;

public class DeliveryAddressInputDTO {
    private String street;
    private int houseNumber;
    private String city;
    private String postcode;
    private String country;

    // Constructors
    public DeliveryAddressInputDTO() {}

    public DeliveryAddressInputDTO(String street, int houseNumber, String city, String postcode, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
    }

    // Getters en setters
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
}