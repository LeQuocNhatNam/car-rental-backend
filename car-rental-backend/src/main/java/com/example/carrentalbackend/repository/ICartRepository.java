package com.example.carrentalbackend.repository;

import com.example.carrentalbackend.dto.IRentalDetailDTO;
import com.example.carrentalbackend.model.Car;
import com.example.carrentalbackend.model.Customer;
import com.example.carrentalbackend.model.RentalDetail;
import com.example.carrentalbackend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface ICartRepository extends JpaRepository<RentalDetail, Long> {
    @Query(value = "        select car.model as carName, car.price, car.id as carId, reservation.id as reservationId,\n" +
            "        rental_detail.quantity as quantity from car join rental_detail on car.id = rental_detail.car_id\n" +
            "        join reservation on reservation.id = rental_detail.reservation_id\n" +
            "        join customer on customer.id = reservation.customer_id \n" +
            "        join account on account.id = customer.account_id\n" +
            "        where username = :username", nativeQuery = true)
    List<IRentalDetailDTO> getIRentalDetailDTO(@Param("username") String username);

    @Query(value = "      select car.* from car join rental_detail on car.id = rental_detail.car_id\n" +
            "        join reservation on reservation.id = rental_detail.reservation_id\n" +
            "        join customer on customer.id = reservation.customer_id \n" +
            "        join account on account.id = customer.account_id\n" +
            "        where username = :username", nativeQuery = true)
    List<Car> getCarsByUsername(@Param("username") String username);

    @Query(value = "select reservation.* from reservation join customer on reservation.customer_id = customer.id\n" +
            "join account on account.id = customer.account_id where account.username = :username", nativeQuery = true)
    Reservation getReservationByUsername(@Param("username") String username);

    @Query(value = "select customer.* from customer join account on account.id = customer.account_id where account.username = :username", nativeQuery = true)
    Customer getCustomerByUsername(@Param("username") String username);

    List<RentalDetail> getRentalDetailByReservation(Reservation reservation);

    List<RentalDetail> findRentalDetailByCar(Car car);

    @Query(value = "select * from rental_detail where car_id = :carId \n" +
            "and (:pickupDate between pickup_date and return_date) \n" +
            "or (:returnDate between pickup_date and return_date) \n" +
            "or (pickup_date between :pickupDate and :returnDate)\n" +
            "or (return_date between :pickupDate and :returnDate); ", nativeQuery = true)
    List<RentalDetail> findCarSchedule (@Param("carId") Long carId,
                                       @Param("pickupDate") Date pickupDate,
                                       @Param("returnDate") Date returnDate);





}
