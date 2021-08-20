package com.soft.cr.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Profile {
    
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final LocalDate dob;
    private final String nationality;
    private final String countryOfResidence;
    private final String city;
    private final String address1;
    private final String address2;
    private final String postalCode;
    private final String mobile;
    private final String avarta;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * @param id
     * @param firstName
     * @param lastName
     * @param gender
     * @param dob
     * @param nationality
     * @param countryOfResidence
     * @param city
     * @param address1
     * @param address2
     * @param postalCode
     * @param mobile
     * @param avarta
     * @param createdAt
     * @param updatedAt
     */
    public Profile(@JsonProperty("id") UUID id, @JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, @JsonProperty("gender") String gender, @JsonProperty("dob") LocalDate dob, @JsonProperty("nationality") String nationality, @JsonProperty("country_of_residence") String countryOfResidence, @JsonProperty("city") String city, @JsonProperty("address_1") String address1, @JsonProperty("address_2") String address2, @JsonProperty("postal_code") String postalCode, @JsonProperty("mobile") String mobile, @JsonProperty("avarta") String avarta, @JsonProperty("created_at") LocalDateTime createdAt, @JsonProperty("updated_at") LocalDateTime updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.nationality = nationality;
        this.countryOfResidence = countryOfResidence;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.mobile = mobile;
        this.avarta = avarta;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return the dob
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @return the countryOfResidence
     */
    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @return the avarta
     */
    public String getAvarta() {
        return avarta;
    }

    /**
     * @return the createdAt
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the updatedAt
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    

}
