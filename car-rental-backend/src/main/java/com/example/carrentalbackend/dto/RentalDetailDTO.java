package com.example.carrentalbackend.dto;

import com.example.carrentalbackend.model.Car;
import com.example.carrentalbackend.model.Image;
import com.example.carrentalbackend.model.Reservation;

import java.util.List;

public class RentalDetailDTO {

    private Long id;

    private Long carId;

    private Long reservationId;

    private String pickupDate;

    private String returnDate;


    private List<Image> imageList;

    public RentalDetailDTO() {
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}

