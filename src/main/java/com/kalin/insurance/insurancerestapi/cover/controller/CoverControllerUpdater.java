package com.kalin.insurance.insurancerestapi.cover.controller;

import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles cover PUT requests. Used to update covers.
 */
@RestController
@RequestMapping("/covers")
public class CoverControllerUpdater {
    private final ServiceUpdater<Cover> coverServiceUpdater;

    @Autowired
    public CoverControllerUpdater(ServiceUpdater<Cover> coverServiceUpdater) {
        this.coverServiceUpdater = coverServiceUpdater;
    }

    @PutMapping("/{id}")
    public Cover update(@Valid @RequestBody Cover cover, @PathVariable("id") Long id) {
        return coverServiceUpdater.update(cover, id);
    }
}
