package com.example.carrentalbackend.service;

import com.example.carrentalbackend.dto.IRentalDetailDTO;
import com.example.carrentalbackend.model.Car;
import com.example.carrentalbackend.model.Customer;
import com.example.carrentalbackend.model.RentalDetail;
import com.example.carrentalbackend.model.Reservation;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICartService {

    List<IRentalDetailDTO> getIRentalDetailDTO(String username);

    List<Car> getCarsByUsername(String username);

    Reservation getReservationByUsername(String username);

    void addToCart(RentalDetail rentalDetail);

    Customer getCustomerByUsername(String username);

    List<RentalDetail> getRentalDetailByReservation(Reservation reservation);

    List<RentalDetail> findRentalDetailsByCar(Car car);

    List<RentalDetail> findCarSchedule(Car car, String pickupDate, String returnDate);

    void deleteCartItem(Long id);

    RentalDetail findCartItemById(Long id);


}
