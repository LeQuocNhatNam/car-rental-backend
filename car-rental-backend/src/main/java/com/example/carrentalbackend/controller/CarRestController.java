package com.example.carrentalbackend.controller;

import com.example.carrentalbackend.dto.CarDTO;
import com.example.carrentalbackend.model.Car;
import com.example.carrentalbackend.service.ICarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/public/car")
public class CarRestController {
    @Autowired
    private ICarService iCarService;

    @GetMapping("/list")
    public ResponseEntity<Object> findAllCar(@RequestParam(required = false, defaultValue = "") String model,
                                             @RequestParam(required = false, defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 6);
        Page<CarDTO> carDTOPage;
        List<CarDTO> carDTOList = new ArrayList<>();
        Page<Car> carPage = iCarService.findAllCar(model, pageable);
        for (Car car : carPage.getContent()) {
            CarDTO carDTO = new CarDTO();
            BeanUtils.copyProperties(car, carDTO);
            carDTO.setImageList(car.getImageList());
            carDTOList.add(carDTO);
        }
        carDTOPage = new PageImpl<>(carDTOList, carPage.getPageable(), carPage.getTotalElements());
        if (carPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(carDTOPage, HttpStatus.OK);
    }

    @GetMapping("/search-car-by-date")
    public ResponseEntity<Object> searchCarsByDate(@RequestParam(required = false, defaultValue = "") String model,
                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                   @RequestParam String pickupDate,
                                                   @RequestParam String returnDate) {
        Pageable pageable = PageRequest.of(page, 6);
        Page<CarDTO> carDTOPage;
        List<CarDTO> carDTOList = new ArrayList<>();
        Page<Car> carPage = iCarService.searchAllCarByDate(model,pickupDate,returnDate,pageable);
        for (Car car : carPage.getContent()) {
            CarDTO carDTO = new CarDTO();
            BeanUtils.copyProperties(car, carDTO);
            carDTO.setImageList(car.getImageList());
            carDTOList.add(carDTO);
        }
        carDTOPage = new PageImpl<>(carDTOList, carPage.getPageable(), carPage.getTotalElements());
        if (carPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(carDTOPage, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Object> findCarById(@PathVariable Long id) {
        Car car = iCarService.findByCarId(id);
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        CarDTO carDTO = new CarDTO();

        BeanUtils.copyProperties(car, carDTO);

        carDTO.setImageList(new ArrayList<>(car.getImageList()));

        return new ResponseEntity<>(carDTO, HttpStatus.OK);
    }
}
