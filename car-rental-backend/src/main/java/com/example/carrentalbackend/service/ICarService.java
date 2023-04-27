package com.example.carrentalbackend.service;

import com.example.carrentalbackend.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICarService {
    Page<Car> findAllCar(String model, Pageable pageable);

    Car findByCarId(Long id);
}
