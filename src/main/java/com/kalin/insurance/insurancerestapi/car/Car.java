package com.kalin.insurance.insurancerestapi.car;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kalin.insurance.insurancerestapi.car.validation.CarProductionYearConstraint;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.model.validation.MatchValuesFromDirectoryConstraint;
import com.kalin.insurance.insurancerestapi.model.BaseEntity;
import com.kalin.insurance.insurancerestapi.policy.Policy;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;

/**
 * Entity holding data for a car.
 */
@Entity
@Table(name = "car",
        uniqueConstraints =
                {@UniqueConstraint(name = "Unique_registration_number", columnNames = {"registration_number"})})
public class Car extends BaseEntity {

    @Column(name = "registration_number")
    @Pattern(regexp = "^[A-Z]{2}\\d{3}", message = "Invalid registration number. Should be two uppercase letters and three digits.")
    @NotNull(message = "You have to provide a registration number.")
    private String registrationNumber;

    @Column(name = "type")
    @MatchValuesFromDirectoryConstraint(fileDirectory = "/car/types.txt")
    private String type;

    @Column(name = "make")
    @MatchValuesFromDirectoryConstraint(fileDirectory = "/car/makes.txt")
    private String make;

    @CarProductionYearConstraint
    private int productionYear;

    @Column(name = "insured_value")
    @Min(value = 1, message = "Insured value cannot be less than {value}.")
    @Max(value = 100000, message = "Insured value cannot be greater than {value}.")
    private int insuredValue;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = "You have to provide covers.")
    @Valid
    private Set<Cover> covers;

    @ManyToOne
    @JoinColumn(name = "policy_id", nullable = false)
    @NotNull(message = "You have to provide policy id.")
    private Policy policy;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public int getInsuredValue() {
        return insuredValue;
    }

    public void setInsuredValue(int insuredValue) {
        this.insuredValue = insuredValue;
    }

    @JsonManagedReference
    public Set<Cover> getCovers() {
        return covers;
    }

    public void setCovers(Set<Cover> covers) {
        this.covers = covers;
    }

    @JsonBackReference
    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Long getPolicyId() {
        return policy.getId();
    }
}
