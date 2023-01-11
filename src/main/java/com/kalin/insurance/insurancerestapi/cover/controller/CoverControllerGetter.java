package com.kalin.insurance.insurancerestapi.cover.controller;

import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.service.CoverServiceGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller that handles cover GET requests. Used for getting covers.
 */
@RestController
@RequestMapping("/covers")
public class CoverControllerGetter {
    private final CoverServiceGetter coverServiceGetter;

    @Autowired
    public CoverControllerGetter(CoverServiceGetter coverServiceGetter) {
        this.coverServiceGetter = coverServiceGetter;
    }

    @GetMapping()
    public List<Cover> findAll() {
        return coverServiceGetter.findAll();
    }

    @GetMapping("/{id}")
    public Cover findById(@PathVariable("id") Long id) {
        return coverServiceGetter.findById(id);
    }
}
