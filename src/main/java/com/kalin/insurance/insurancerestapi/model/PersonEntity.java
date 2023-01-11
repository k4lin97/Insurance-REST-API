package com.kalin.insurance.insurancerestapi.model;

import com.kalin.insurance.insurancerestapi.model.validation.GenderConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entity for a person.
 */
@MappedSuperclass
public class PersonEntity extends BaseEntity {
    /**
     * Available genders for a person entity.
     */
    public enum Gender {
        MALE, FEMALE;

        public static boolean isValid(PersonEntity.Gender gender) {
            for (PersonEntity.Gender enumValue : PersonEntity.Gender.values()) {
                if (enumValue.equals(gender)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Column(name = "first_name")
    @NotBlank(message = "You have to provide a first name.")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "You have to provide a last name.")
    private String lastName;

    @Column(name = "birth_date")
    @NotNull(message = "You have to provide a birth date")
    private Date birthDate;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @GenderConstraint
    private Gender gender;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
