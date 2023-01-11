package com.kalin.insurance.insurancerestapi.policy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.service.PolicyServiceGetter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PolicyControllerGetter.class)
@WithMockUser
class PolicyControllerGetterTest {
    private static final Long FIRST_POLICY_ID = 1L;
    private static final Long SECOND_POLICY_ID = 2L;
    private static final String POLICY_URL = "/policies/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PolicyServiceGetter policyServiceGetter;

    private static Policy policy;
    private static List<Policy> policies;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(FIRST_POLICY_ID);

        Policy secondPolicy = new Policy();
        secondPolicy.setId(SECOND_POLICY_ID);

        policies = new ArrayList<>();
        policies.add(policy);
        policies.add(secondPolicy);
    }

    @Test
    void givenPoliciesShouldReturnPolicies() throws Exception {
        when(policyServiceGetter.findAll()).thenReturn(policies);

        StringWriter stringWriter = new StringWriter();
        objectMapper.writeValue(stringWriter, policies);
        final String expectedJson = stringWriter.toString();

        mockMvc.perform(get(POLICY_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));

        verify(policyServiceGetter).findAll();
    }

    @Test
    void givenPolicyShouldReturnPolicyById() throws Exception {
        when(policyServiceGetter.findById(FIRST_POLICY_ID)).thenReturn(policy);

        final String expectedJson = objectMapper.writeValueAsString(policy);

        mockMvc.perform(get(POLICY_URL + FIRST_POLICY_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(policyServiceGetter).findById(FIRST_POLICY_ID);
    }

    @Test
    void notGivenPolicyShouldReturnNotFound() throws Exception {
        when(policyServiceGetter.findById(FIRST_POLICY_ID)).thenThrow(PolicyNotFoundException.class);

        mockMvc.perform(get(POLICY_URL + FIRST_POLICY_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(policyServiceGetter).findById(FIRST_POLICY_ID);
    }
}