package com.kalin.insurance.insurancerestapi.policy.controller;

import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PolicyControllerDeleter.class)
@WithMockUser
class PolicyControllerDeleterTest {
    private static final Long POLICY_ID = 1L;
    private static final String POLICY_URL = "/policies/";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    @Qualifier("policyServiceDeleterImpl")
    private ServiceDeleter policyServiceDeleter;

    @Test
    void givenPolicyShouldReturnSuccess() throws Exception {
        doNothing().when(policyServiceDeleter).deleteById(POLICY_ID);

        mockMvc.perform(delete(POLICY_URL + POLICY_ID).with(csrf()))
                .andExpect(status().isOk());

        verify(policyServiceDeleter).deleteById(POLICY_ID);
    }

    @Test
    void givenPolicyNotFoundShouldReturnNOtFound() throws Exception {
        doThrow(PolicyNotFoundException.class).when(policyServiceDeleter).deleteById(POLICY_ID);

        mockMvc.perform(MockMvcRequestBuilders.delete(POLICY_URL + POLICY_ID).with(csrf()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());

        verify(policyServiceDeleter).deleteById(POLICY_ID);
    }
}