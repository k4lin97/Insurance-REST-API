package com.kalin.insurance.insurancerestapi.cover;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.cover.validation.CoverTypeConstraint;
import com.kalin.insurance.insurancerestapi.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity holding data for a car. It specified en enum od cover's types - Cover.Type.
 * Cover is equal to another one if the type of one cover is equal to another.
 */
@Entity
@Table(name = "cover")
public class Cover extends BaseEntity {
    /**
     * Cover can only be of the type specified in this enum.
     */
    public enum Type {
        OC("OC insurance is a basic third-party liability insurance.", 1.05d),
        AC("AC insurance will protect you against the financial consequences of theft and vandalism.", 0.95d),
        NWW("NNW is a personal accident insurance that protects health and life of the driver and passengers.", 1.1d),
        ASSISTANCE("Provide yourself with support in case of unexpected situations.", 1.2d);

        private final String description;
        private final double coefficient;

        Type(String description, double coefficient) {
            this.description = description;
            this.coefficient = coefficient;
        }

        /**
         * Checks whether the coverType is valid, meaning if it is specified as enum in Cover.Type.
         *
         * @param coverType cover type to check.
         * @return true if valid, false otherwise.
         */
        public static boolean isValid(Cover.Type coverType) {
            for (Cover.Type enumValue : Cover.Type.values()) {
                if (enumValue.equals(coverType)) {
                    return true;
                }
            }
            return false;
        }


        public String getDescription() {
            return description;
        }

        public double getCoefficient() {
            return coefficient;
        }
    }

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @CoverTypeConstraint
    private Cover.Type type;

    @Column(name = "description")
    private String description;

    @Column(name = "premium")
    private double premium;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    @NotNull(message = "You have to provide car id.")
    private Car car;

    /**
     * Function called before saving and updating entity in database.
     * It calculates premium, based on car's insured value and cover type's coefficient.
     * It also updates cover's type description to be valid one.
     */
    @PreUpdate
    @PrePersist
    public void preUpdatePersist() {
        calculatePremium();
        updateDescription();
    }

    public void calculatePremium() {
        this.premium = car.getInsuredValue() * type.getCoefficient();
    }

    public void updateDescription() {
        this.description = this.type.getDescription();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    @JsonBackReference
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getCarId() {
        return car.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cover)) return false;
        Cover cover = (Cover) o;
        return type == cover.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
