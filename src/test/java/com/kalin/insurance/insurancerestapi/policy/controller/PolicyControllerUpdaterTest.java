package com.kalin.insurance.insurancerestapi.policy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import com.kalin.insurance.insurancerestapi.policy.Policy;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PolicyControllerUpdater.class)
@WithMockUser
class PolicyControllerUpdaterTest {
    private static final Long POLICY_ID = 1L;
    private static final String POLICY_URL = "/policies/";
    private static final String PUT_VALID_POLICY_CONTENT = "{\n" +
            "    \"conclusionDate\": \"2022-12-17\",\n" +
            "    \"insuranceBeginDate\": \"2022-12-31\",\n" +
            "    \"insuranceEndDate\": \"2023-12-30\",\n" +
            "    \"client\": {\n" +
            "        \"firstName\": \"Pawel\",\n" +
            "        \"lastName\": \"Nowak\",\n" +
            "        \"birthDate\": \"1999-10-15\",\n" +
            "        \"gender\": \"MALE\",\n" +
            "        \"address\": {\n" +
            "            \"addressType\": {\n" +
            "                \"type\": \"home\"\n" +
            "            },\n" +
            "            \"country\": \"Poland\",\n" +
            "            \"city\": \"Gdansk\",\n" +
            "            \"postCode\": \"80-180\",\n" +
            "            \"street\": \"Zielona\",\n" +
            "            \"streetNumber\": \"12a\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"cars\": [\n" +
            "        {\n" +
            "            \"registrationNumber\": \"GD123\",\n" +
            "            \"type\": \"sedan\",\n" +
            "            \"make\": \"audi\",\n" +
            "            \"productionYear\": 2016,\n" +
            "            \"insuredValue\": 1000,\n" +
            "            \"covers\": [\n" +
            "                {\n" +
            "                    \"type\": \"OC\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"type\": \"AC\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"type\": \"ASSISTANCE\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";
    private static final String PUT_NOT_VALID_POLICY_CONTENT = "{\n" +
            "    \"conclusionDate\": \"2022-12-17\",\n" +
            "    \"insuranceBeginDate\": \"2022-12-31\",\n" +
            "    \"insuranceEndDate\": \"2023-12-30\",\n" +
            "    \"client\": {\n" +
            "        \"firstName\": \"Pawel\",\n" +
            "        \"lastName\": \"Nowak\",\n" +
            "        \"birthDate\": \"1999-10-15\",\n" +
            "        \"gender\": \"MALEdddd\",\n" +
            "        \"address\": {\n" +
            "            \"addressType\": {\n" +
            "                \"type\": \"home\"\n" +
            "            },\n" +
            "            \"country\": \"Poland\",\n" +
            "            \"city\": \"Gdansk\",\n" +
            "            \"postCode\": \"80-180\",\n" +
            "            \"street\": \"Zielona\",\n" +
            "            \"streetNumber\": \"12a\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"cars\": [\n" +
            "        {\n" +
            "            \"registrationNumber\": \"GD123\",\n" +
            "            \"type\": \"sedan\",\n" +
            "            \"make\": \"audi\",\n" +
            "            \"productionYear\": 2016,\n" +
            "            \"insuredValue\": 1000,\n" +
            "            \"covers\": [\n" +
            "                {\n" +
            "                    \"type\": \"OC\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"type\": \"AC\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"type\": \"ASSISTANCE\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ServiceUpdater<Policy> policyServiceUpdater;

    private static Policy policy;

    @BeforeAll
    static void setup() {
        policy = new Policy();
        policy.setId(POLICY_ID);
    }

    @Test
    void givenExistingIdShouldReturnUpdatedPolicy() throws Exception {
        given(policyServiceUpdater.update(any(), any())).willReturn(policy);

        mockMvc.perform(put(POLICY_URL + POLICY_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_POLICY_CONTENT))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(policy)));
    }

    @Test
    void givenNonExistingIdShouldReturnNotFound() throws Exception {
        doThrow(PolicyNotFoundException.class).when(policyServiceUpdater).update(any(), any());

        mockMvc.perform(put(POLICY_URL + POLICY_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_VALID_POLICY_CONTENT))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }

    @Test
    void givenWrongPolicyPutInputShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put(POLICY_URL + POLICY_ID)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PUT_NOT_VALID_POLICY_CONTENT))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(String.valueOf(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.error").isNotEmpty())
                .andExpect(jsonPath("$.messages").isNotEmpty());
    }
}