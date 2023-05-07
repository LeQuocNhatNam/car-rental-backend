package com.example.carrentalbackend.service.impl;

import com.example.carrentalbackend.model.Car;
import com.example.carrentalbackend.repository.ICarRepository;
import com.example.carrentalbackend.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class CarService implements ICarService {
    @Autowired
    private ICarRepository iCarRepository;

    @Override
    public Page<Car> findAllCar(String model, Pageable pageable) {
        return iCarRepository.findAllCar("%" + model + "%", pageable);
    }

    @Override
    public Car findByCarId(Long id) {
        return iCarRepository.findCarById(id);
    }

    @Override
    public void deleteCar(Long id) {
        iCarRepository.deleteCar(id);
    }

    @Override
    public Page<Car> searchAllCarByDate(String model, String pickupDate, String returnDate, Pageable pageable) {
        return iCarRepository.searchCarByDate("%" + model + "%", Date.valueOf(pickupDate),
                Date.valueOf(returnDate), pageable);
    }
}
