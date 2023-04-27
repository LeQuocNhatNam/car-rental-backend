package com.example.carrentalbackend.repository;

import com.example.carrentalbackend.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ICarRepository extends JpaRepository<Car, Long> {
    @Query(value = "select * from car where is_deleted = false and model like :model", nativeQuery = true)
    Page<Car> findAllCar(String model, Pageable pageable);

    @Query(value = "select * from car where car.id = :id and is_deleted = false", nativeQuery = true)
    Car findCarById(Long id);
}
