package com.kalin.insurance.insurancerestapi.cover.checker;

import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.cover.Cover;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverDuplicatedException;

/**
 * Defines methods to check if the specified cover is already assigned to a car.
 */
public interface CoverToCarAssignedChecker {
    void checkIfCoverToCarAssigned(Cover cover) throws CoverDuplicatedException, CarNotFoundException;
}
