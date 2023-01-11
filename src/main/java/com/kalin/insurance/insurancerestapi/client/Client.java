package com.kalin.insurance.insurancerestapi.client;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.model.PersonEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Entity holding data for a client.
 */
@Entity
@Table(name = "client")
public class Client extends PersonEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_address_client"))
    @NotNull(message = "You have to provide an address")
    @Valid
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
