package com.example.carrentalbackend.repository;

import com.example.carrentalbackend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
