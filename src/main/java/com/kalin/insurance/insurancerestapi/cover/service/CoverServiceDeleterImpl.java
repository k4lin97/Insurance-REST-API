package com.kalin.insurance.insurancerestapi.cover.service;

import com.kalin.insurance.insurancerestapi.cover.CoverRepository;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to deleting covers from database.
 */
@Service
@Qualifier("coverServiceDeleter")
public class CoverServiceDeleterImpl implements ServiceDeleter {
    private final CoverRepository coverRepository;
    private final ExistenceCheckerById existenceCheckerById;

    @Autowired
    public CoverServiceDeleterImpl(CoverRepository coverRepository,
                                   @Qualifier("coverExistenceCheckerById") ExistenceCheckerById existenceCheckerById) {
        this.coverRepository = coverRepository;
        this.existenceCheckerById = existenceCheckerById;
    }

    @Override
    public void deleteById(Long id) {
        existenceCheckerById.checkIfAlreadyExists(id);
        coverRepository.deleteById(id);
    }
}
