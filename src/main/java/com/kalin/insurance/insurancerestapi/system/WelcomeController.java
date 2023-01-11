package com.kalin.insurance.insurancerestapi.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple controller to generate welcome message and check security.
 */
@RestController
public class WelcomeController {
    @GetMapping("/")
    public String welcome() {
        return "Insurance REST API - welcome!";
    }
}
