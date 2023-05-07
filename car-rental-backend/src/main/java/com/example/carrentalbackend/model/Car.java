package com.example.carrentalbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
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

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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
    private List<Image> imageList;



    public Car() {
    }


    public Car(Long id, Double price, String model, String brand, String numberPlate) {
        this.id = id;
        this.price = price;
        this.model = model;
        this.brand = brand;
        this.numberPlate = numberPlate;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
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
