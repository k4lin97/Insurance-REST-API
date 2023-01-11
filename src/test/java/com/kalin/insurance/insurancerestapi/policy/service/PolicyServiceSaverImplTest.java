package com.kalin.insurance.insurancerestapi.policy.service;

import com.kalin.insurance.insurancerestapi.address.Address;
import com.kalin.insurance.insurancerestapi.address.type.AddressType;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.service.AddressTypeServiceGetter;
import com.kalin.insurance.insurancerestapi.client.Client;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.PolicyRepository;
import com.kalin.insurance.insurancerestapi.policy.checker.PolicyConsistencyChecker;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotConsistentException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
class PolicyServiceSaverImplTest {
    private static final Long POLICY_ID = 1L;

    @InjectMocks
    private PolicyServiceSaverImpl policyServiceSaver;
    @Mock
    private PolicyConsistencyChecker policyConsistencyChecker;
    @Mock
    private AddressTypeServiceGetter addressTypeServiceGetter;
    @Mock
    private PolicyRepository policyRepository;

    private static Policy policy;
    private static AddressType addressType;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(POLICY_ID);

        addressType = new AddressType();
        addressType.setType("home");

        Address address = new Address();
        address.setAddressType(addressType);

        Client client = new Client();
        client.setAddress(address);

        policy.setClient(client);
    }

    @Test
    void givenPolicyShouldReturnAndSavePolicy() {
        doNothing().when(policyConsistencyChecker).checkPolicyConsistency(policy);
        given(addressTypeServiceGetter.findAddressTypeByType(addressType.getType())).willReturn(addressType);
        given(policyRepository.save(policy)).willReturn(policy);

        assertEquals(policy, policyServiceSaver.save(policy));
        verify(policyRepository).save(policy);
    }

    @Test
    void givenNotConsistentPolicyShouldThrowPolicyNotConsistentException() {
        doThrow(PolicyNotConsistentException.class).when(policyConsistencyChecker).checkPolicyConsistency(policy);
        assertThrows(PolicyNotConsistentException.class, () -> policyServiceSaver.save(policy));
    }

    @Test
    void givenNotExistingAddressTypeShouldThrowAddressTypeNotFoundException() {
        given(addressTypeServiceGetter.findAddressTypeByType(addressType.getType())).willReturn(addressType);
        doThrow(AddressTypeNotFoundException.class).when(addressTypeServiceGetter).findAddressTypeByType(addressType.getType());
        assertThrows(AddressTypeNotFoundException.class, () -> policyServiceSaver.save(policy));
    }
}