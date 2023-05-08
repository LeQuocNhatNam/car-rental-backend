package com.example.carrentalbackend.repository;

import com.example.carrentalbackend.model.Car;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Transactional
public interface ICarRepository extends JpaRepository<Car, Long> {
    @Query(value = "select * from car where is_deleted = false and model like :model", nativeQuery = true)
    Page<Car> findAllCar(@Param("model") String model, Pageable pageable);

    @Query(value = "select * from car where car.id = :id and is_deleted = false", nativeQuery = true)
    Car findCarById(Long id);

    @Query(value = "update car set car.is_deleted = true where car.id = :id", nativeQuery = true)
    void deleteCar(@Param("id") Long id);

    @Query(value = "select * from car where model like :model and is_deleted = false and car.id not in (\n" +
            "select car.id from car join rental_detail on car.id = rental_detail.car_id \n" +
            "where rental_detail.is_rented = true and ((:pickupDate between pickup_date and return_date) \n" +
            "or (:returnDate between pickup_date and return_date) \n" +
            "or (pickup_date between :pickupDate and :returnDate)\n" +
            "or (return_date between :pickupDate and :returnDate)))",nativeQuery = true)
    Page<Car> searchCarByDate(@Param("model") String model,
                              @Param("pickupDate") Date pickupDate,
                              @Param("returnDate") Date returnDate,
                              Pageable pageable);
}
