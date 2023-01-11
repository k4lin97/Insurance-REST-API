package com.kalin.insurance.insurancerestapi.cover.controller;

import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Rest controller that handles cover POST requests. Used for creating new covers.
 */
@RestController
@RequestMapping("/covers")
public class CoverControllerSaver {
    private final ServiceSaver<Cover> coverServiceSaver;

    @Autowired
    public CoverControllerSaver(ServiceSaver<Cover> coverServiceSaver) {
        this.coverServiceSaver = coverServiceSaver;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cover save(@Valid @RequestBody Cover cover) {
        return coverServiceSaver.save(cover);
    }
}
