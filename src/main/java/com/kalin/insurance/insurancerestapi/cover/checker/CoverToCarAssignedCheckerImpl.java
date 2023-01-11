package com.kalin.insurance.insurancerestapi.cover.checker;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.car.service.CarServiceGetter;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Checks whether specified cover is already assigned to a car.
 */
@Component
public class CoverToCarAssignedCheckerImpl implements CoverToCarAssignedChecker {
    private final CarServiceGetter carServiceGetter;

    @Autowired
    public CoverToCarAssignedCheckerImpl(CarServiceGetter carServiceGetter) {
        this.carServiceGetter = carServiceGetter;
    }

    @Override
    public void checkIfCoverToCarAssigned(Cover cover) throws CoverDuplicatedException, CarNotFoundException {
        Car car = carServiceGetter.getReferenceById(cover.getCar().getId());
        Set<Cover> carCovers = car.getCovers();
        if (carCovers.contains(cover)) {
            throw new CoverDuplicatedException(String.valueOf(cover.getType()), car.getId());
        }
    }
}
