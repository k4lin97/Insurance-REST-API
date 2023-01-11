package com.kalin.insurance.insurancerestapi.cover.service;

import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to updating, if present in database, cover by ID.
 */
@Service
public class CoverServiceUpdaterImpl implements ServiceUpdater<Cover> {
    private final ExistenceCheckerById existenceCheckerById;
    private final ServiceSaver<Cover> coverServiceSaver;

    @Autowired
    public CoverServiceUpdaterImpl(@Qualifier("coverExistenceCheckerById") ExistenceCheckerById existenceCheckerById,
                                   ServiceSaver<Cover> coverServiceSaver) {
        this.existenceCheckerById = existenceCheckerById;
        this.coverServiceSaver = coverServiceSaver;
    }

    @Override
    public Cover update(Cover cover, Long id) {
        existenceCheckerById.checkIfAlreadyExists(id);
        cover.setId(id);
        return coverServiceSaver.save(cover);
    }
}
