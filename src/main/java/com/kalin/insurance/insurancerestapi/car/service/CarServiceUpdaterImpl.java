package com.kalin.insurance.insurancerestapi.car.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.model.checker.ExistenceCheckerById;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import com.kalin.insurance.insurancerestapi.model.service.ServiceUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service used to updating, if present in database, car by ID.
 */
@Service
public class CarServiceUpdaterImpl implements ServiceUpdater<Car> {
    private final ServiceSaver<Car> carServiceSaver;
    @Qualifier("carExistenceCheckerById")
    private final ExistenceCheckerById existenceCheckerById;

    @Autowired
    public CarServiceUpdaterImpl(@Qualifier("carExistenceCheckerById") ExistenceCheckerById existenceCheckerById,
                                 ServiceSaver<Car> carServiceSaver) {
        this.carServiceSaver = carServiceSaver;
        this.existenceCheckerById = existenceCheckerById;
    }

    @Override
    public Car update(Car car, Long id) {
        existenceCheckerById.checkIfAlreadyExists(id);
        car.setId(id);
        return carServiceSaver.save(car);
    }
}
