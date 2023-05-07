package com.example.carrentalbackend.service.impl;

import com.example.carrentalbackend.dto.IRentalDetailDTO;
import com.example.carrentalbackend.model.Car;
import com.example.carrentalbackend.model.Customer;
import com.example.carrentalbackend.model.RentalDetail;
import com.example.carrentalbackend.model.Reservation;
import com.example.carrentalbackend.repository.ICartRepository;
import com.example.carrentalbackend.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class CartService implements ICartService {
    @Autowired
    private ICartRepository iCartRepository;

    @Override
    public List<IRentalDetailDTO> getIRentalDetailDTO(String username) {
        return iCartRepository.getIRentalDetailDTO(username);
    }

    @Override
    public List<Car> getCarsByUsername(String username) {
        return iCartRepository.getCarsByUsername(username);
    }

    @Override
    public Reservation getReservationByUsername(String username) {
        return this.iCartRepository.getReservationByUsername(username);
    }

    @Override
    public void addToCart(RentalDetail rentalDetail) {
        iCartRepository.save(rentalDetail);
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return iCartRepository.getCustomerByUsername(username);
    }

    @Override
    public List<RentalDetail> getRentalDetailByReservation(Reservation reservation) {
        return iCartRepository.getRentalDetailByReservation(reservation);
    }

    @Override
    public List<RentalDetail> findRentalDetailsByCar(Car car) {
        return iCartRepository.findRentalDetailByCar(car);
    }

    @Override
    public List<RentalDetail> findCarSchedule(Car car, String pickupDate, String returnDate) {
        return iCartRepository.findCarSchedule(car.getId(), Date.valueOf(pickupDate), Date.valueOf(returnDate));
    }

    @Override
    public void deleteCartItem(Long id) {
        iCartRepository.deleteById(id);
    }

    @Override
    public RentalDetail findCartItemById(Long id) {
        return iCartRepository.findById(id).get();
    }


}
