package com.example.carrentalbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickUpDate;

    private String returnDate;

    private Double totalPrice;

    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;


    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Set<RentalDetail> getRentalDetailSet() {
        return rentalDetailSet;
    }

    public void setRentalDetailSet(Set<RentalDetail> rentalDetailSet) {
        this.rentalDetailSet = rentalDetailSet;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    @OneToMany(mappedBy = "reservation")
    @JsonIgnore
    private Set<RentalDetail> rentalDetailSet;


    public Reservation(Long id, String pickUpDate, String returnDate, Double totalPrice, Customer customer) {
        this.id = id;
        this.pickUpDate = pickUpDate;
        this.returnDate = returnDate;
        this.totalPrice = totalPrice;
        this.customer = customer;
    }

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
