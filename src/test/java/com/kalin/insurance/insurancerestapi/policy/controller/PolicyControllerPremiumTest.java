package com.kalin.insurance.insurancerestapi.policy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import com.kalin.insurance.insurancerestapi.policy.service.PolicyServicePremium;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PolicyControllerPremium.class)
@WithMockUser
class PolicyControllerPremiumTest {
    private static final Long POLICY_ID = 1L;
    private static final String POLICY_URL = "/policies/" + POLICY_ID + "/premium";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PolicyServicePremium policyServicePremium;

    private static Policy policy;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(POLICY_ID);
    }

    @Test
    void givenPolicyShouldReturnPolicyById() throws Exception {
        when(policyServicePremium.calculatePolicyPremium(POLICY_ID)).thenReturn(policy);

        final String expectedJson = objectMapper.writeValueAsString(policy);

        mockMvc.perform(get(POLICY_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
        verify(policyServicePremium).calculatePolicyPremium(POLICY_ID);
    }

    @Test
    void notGivenPolicyShouldReturnNotFound() throws Exception {
        when(policyServicePremium.calculatePolicyPremium(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

        mockMvc.perform(get(POLICY_URL))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
        verify(policyServicePremium).calculatePolicyPremium(POLICY_ID);
    }
}