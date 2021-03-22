package nl.novi.mockitoexample.payload.request;

import javax.validation.constraints.NotBlank;

public class AddressRequest {

    @NotBlank(message = "Postal code is mandatory")
    private String postalCode;
    @NotBlank(message = "Streetname is mandatory")
    private String streetName;
    @NotBlank(message = "Housenumber is mandatory")
    private String houseNumber;
    private String addition;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }
}
