package com.example.carrentalbackend.service.impl;

import com.example.carrentalbackend.model.Car;
import com.example.carrentalbackend.repository.ICarRepository;
import com.example.carrentalbackend.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
