package com.kalin.insurance.insurancerestapi.cover.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.service.CarServiceGetter;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.CoverRepository;
import com.kalin.insurance.insurancerestapi.cover.checker.CoverToCarAssignedChecker;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to saving cover to database.
 */
@Service
public class CoverServiceSaverImpl implements ServiceSaver<Cover> {
    private final CoverRepository coverRepository;
    private final CarServiceGetter carServiceGetter;
    private final ExistenceCheckerById carExistenceCheckerById;
    private final CoverToCarAssignedChecker coverToCarAssignedChecker;

    @Autowired
    public CoverServiceSaverImpl(CoverRepository coverRepository,
                                 CarServiceGetter carServiceGetter,
                                 @Qualifier("carExistenceCheckerById") ExistenceCheckerById carExistenceCheckerById,
                                 CoverToCarAssignedChecker coverToCarAssignedChecker) {
        this.coverRepository = coverRepository;
        this.carServiceGetter = carServiceGetter;
        this.carExistenceCheckerById = carExistenceCheckerById;
        this.coverToCarAssignedChecker = coverToCarAssignedChecker;
    }

    @Override
    public Cover save(Cover cover) {
        carExistenceCheckerById.checkIfAlreadyExists(cover.getCar().getId());
        coverToCarAssignedChecker.checkIfCoverToCarAssigned(cover);
        Car car = carServiceGetter.getReferenceById(cover.getCar().getId());
        cover.setCar(car);
        return coverRepository.save(cover);
    }
}
