package com.example.carrentalbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    private String model;

    private String brand;

    private String numberPlate;


    private boolean isDeleted;

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

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    private Set<RentalDetail> rentalDetailSet;

    @OneToMany(mappedBy = "car")
    @JsonIgnore
    private Set<Image> imageSet;



    public Car() {
    }


    public Car(Long id, Double price, String model, String brand, String numberPlate) {
        this.id = id;
        this.price = price;
        this.model = model;
        this.brand = brand;
        this.numberPlate = numberPlate;
    }

    public Set<Image> getImageSet() {
        return imageSet;
    }

    public void setImageSet(Set<Image> imageSet) {
        this.imageSet = imageSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }


}
