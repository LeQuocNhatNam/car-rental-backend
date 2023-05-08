package com.example.carrentalbackend.service.impl;

import com.example.carrentalbackend.model.Reservation;
import com.example.carrentalbackend.repository.IReservationRepository;
import com.example.carrentalbackend.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService implements IReservationService {
    @Autowired
    private IReservationRepository iReservationRepository;


    @Override
    public Reservation saveReservation(Reservation reservation) {
        return this.iReservationRepository.save(reservation);
    }

    @Override
    public void setPaidReservation(Reservation reservation) {
        iReservationRepository.save(reservation);
    }
}
