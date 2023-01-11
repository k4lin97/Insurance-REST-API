package com.kalin.insurance.insurancerestapi.address.type;

import com.kalin.insurance.insurancerestapi.model.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

/**
 * Entity holding data for address type. Two address types are considered same if their type is equal.
 */
@Entity
@Table(name = "address_type")
public class AddressType extends BaseEntity {
    @Column(name = "type")
    @NotBlank(message = "You have to provide a type.")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressType that = (AddressType) o;
        return this.type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
