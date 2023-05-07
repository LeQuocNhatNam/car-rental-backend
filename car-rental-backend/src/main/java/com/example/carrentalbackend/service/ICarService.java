package com.example.carrentalbackend.service;

import com.example.carrentalbackend.model.Car;
import com.example.carrentalbackend.model.RentalDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICarService {
    Page<Car> findAllCar(String model, Pageable pageable);

    Car findByCarId(Long id);

    void deleteCar(Long id);

    Page<Car> searchAllCarByDate(String model, String pickupDate, String returnDate, Pageable pageable);

}
