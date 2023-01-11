package com.kalin.insurance.insurancerestapi.address;

import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Entity holding data for an address.
 */
@Entity
@Table(name = "address")
public class Address extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "address_type_id", nullable = false)
    @NotNull(message = "You have to provide an address type.")
    private AddressType addressType;

    @Column(name = "country")
    @NotBlank(message = "You have to provide a country.")
    private String country;

    @Column(name = "city")
    @NotBlank(message = "You have to provide a city.")
    private String city;

    @Column(name = "post_code")
    @Pattern(regexp = "^\\d{2}-\\d{3}", message = "Invalid post code.")
    @NotNull(message = "You have to provide a post code.")
    private String postCode;

    @Column(name = "street")
    @NotBlank(message = "You have to provide a street.")
    private String street;

    @Column(name = "street_number")
    @NotBlank(message = "You have to provide a street number.")
    private String streetNumber;

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }
}
