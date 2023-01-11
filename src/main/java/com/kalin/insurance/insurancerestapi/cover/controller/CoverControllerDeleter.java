package com.kalin.insurance.insurancerestapi.cover.controller;

import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * Rest controller that handles cover DELETE requests. Used for deleting covers.
 */
@RestController
@RequestMapping("/covers")
public class CoverControllerDeleter {
    private final ServiceDeleter coverServiceDeleter;

    @Autowired
    public CoverControllerDeleter(@Qualifier("coverServiceDeleterImpl") ServiceDeleter coverServiceDeleter) {
        this.coverServiceDeleter = coverServiceDeleter;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        coverServiceDeleter.deleteById(id);
    }
}
