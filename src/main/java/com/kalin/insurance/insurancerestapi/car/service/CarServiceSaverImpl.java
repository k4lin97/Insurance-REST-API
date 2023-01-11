package com.kalin.insurance.insurancerestapi.car.service;

import com.kalin.insurance.insurancerestapi.car.Car;
import com.kalin.insurance.insurancerestapi.car.CarRepository;
import com.kalin.insurance.insurancerestapi.car.assigner.CarPolicyAssigner;
import com.kalin.insurance.insurancerestapi.model.service.ServiceSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service used to saving car to database.
 */
@Service
public class CarServiceSaverImpl implements ServiceSaver<Car> {
    private final CarRepository carRepository;
    private final CarPolicyAssigner carPolicyAssigner;

    @Autowired
    public CarServiceSaverImpl(CarRepository carRepository, CarPolicyAssigner carPolicyAssigner) {
        this.carRepository = carRepository;
        this.carPolicyAssigner = carPolicyAssigner;
    }

    @Override
    public Car save(Car car) {
        carPolicyAssigner.assignPolicyToCar(car);
        return carRepository.save(car);
    }
}
