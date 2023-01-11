package com.kalin.insurance.insurancerestapi.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for the addresses.
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT COUNT(address.id) FROM Address address WHERE address.addressType.id = :addressTypeId")
    @Transactional(readOnly = true)
    int countAddressesByAddressTypeId(@Param("addressTypeId") Long addressTypeId);
}
