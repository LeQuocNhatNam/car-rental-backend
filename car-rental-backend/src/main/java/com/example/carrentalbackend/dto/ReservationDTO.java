package com.example.carrentalbackend.dto;

import java.util.List;

public class ReservationDTO {

    private Long id;

    List<RentalDetailDTO> rentalDetailDTOList;

    private String bookingDate;

    private Double totalPrice;

    public ReservationDTO() {
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<RentalDetailDTO> getRentalDetailDTOList() {
        return rentalDetailDTOList;
    }

    public void setRentalDetailDTOList(List<RentalDetailDTO> rentalDetailDTOList) {
        this.rentalDetailDTOList = rentalDetailDTOList;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}
