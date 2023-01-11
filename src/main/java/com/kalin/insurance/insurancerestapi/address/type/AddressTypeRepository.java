package com.kalin.insurance.insurancerestapi.address.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository for the address types.
 */
public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {
    @Query("SELECT COUNT(address_type.type) FROM AddressType address_type WHERE lower(address_type.type) = lower(:addressType)")
    @Transactional(readOnly = true)
    int countAddressTypesByType(@Param("addressType") String addressType);

    Optional<AddressType> findAddressTypeByType(String type);
}
