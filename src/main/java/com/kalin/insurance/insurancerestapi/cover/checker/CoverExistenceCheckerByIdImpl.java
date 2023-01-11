package com.kalin.insurance.insurancerestapi.cover.checker;

import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.CoverRepository;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Checks if the cover, specified by ID, exists in a database.
 */
@Component
@Qualifier("coverExistenceCheckerById")
public class CoverExistenceCheckerByIdImpl implements ExistenceCheckerById {
    private final CoverRepository coverRepository;

    @Autowired
    public CoverExistenceCheckerByIdImpl(CoverRepository coverRepository) {
        this.coverRepository = coverRepository;
    }

    @Override
    public void checkIfAlreadyExists(Long id) throws CoverNotFoundException {
        Optional<Cover> optionalCover = coverRepository.findById(id);
        if (optionalCover.isEmpty()) {
            throw new CoverNotFoundException(id);
        }
    }
}
