package com.example.carrentalbackend.controller;

import com.example.carrentalbackend.dto.RentalDetailDTO;
import com.example.carrentalbackend.dto.ReservationDTO;
import com.example.carrentalbackend.model.*;
import com.example.carrentalbackend.security_authentication.jwt.JwtUtility;
import com.example.carrentalbackend.service.IAccountService;
import com.example.carrentalbackend.service.ICarService;
import com.example.carrentalbackend.service.ICartService;
import com.example.carrentalbackend.service.IReservationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.carrentalbackend.security_authentication.jwt.JwtFilter.parseJwt;

@Controller
@CrossOrigin
@RequestMapping("/api/user/cart")
public class CartRestController {


    @Autowired
    private ICartService iCartService;


    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private ICarService iCarService;

    @Autowired
    private IReservationService iReservationService;

    @GetMapping("")
    public ResponseEntity<Object> getCart(HttpServletRequest request) {
        String jwt = parseJwt(request);
        String username = jwtUtility.getUserNameFromJwtToken(jwt);
        Account account = iAccountService.findAccountByUsername(username);
        List<Reservation> reservationList = account.getCustomer().getReservationList();
        List<RentalDetail> rentalDetailList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            if (!reservation.isPaid()) {
                rentalDetailList = reservation.getRentalDetailList();
                break;
            }
        }
        if (rentalDetailList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<RentalDetailDTO> cart = convertToRentalDetailDTO(rentalDetailList);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addToCart(HttpServletRequest request, @RequestBody RentalDetail rentalDetail) {
        List<RentalDetail> busyCarDateList = iCartService.findCarSchedule(rentalDetail.getCar(), rentalDetail.getPickupDate(), rentalDetail.getReturnDate());
        if (!busyCarDateList.isEmpty()) {
            List<RentalDetailDTO> rentalDetailDTOList = new ArrayList<>();
            for (RentalDetail rentalDetail1 : busyCarDateList) {
                RentalDetailDTO rentalDetailDTO = new RentalDetailDTO();
                rentalDetailDTO.setPickupDate(rentalDetail1.getPickupDate());
                rentalDetailDTO.setReturnDate(rentalDetail1.getReturnDate());
                rentalDetailDTOList.add(rentalDetailDTO);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(rentalDetailDTOList);
        }

        String jwt = parseJwt(request);
        String username = jwtUtility.getUserNameFromJwtToken(jwt);
        Account account = iAccountService.findAccountByUsername(username);

        List<Reservation> reservationList = account.getCustomer().getReservationList().stream().filter(re -> !re.isPaid())
                .collect(Collectors.toList());
        Reservation reservation = null;
        if (reservationList.isEmpty()) {
            Reservation customerNewReservation = new Reservation();
            customerNewReservation.setCustomer(account.getCustomer());
            reservation = iReservationService.saveReservation(customerNewReservation);
        } else {
            for (Reservation reservation1 : reservationList) {
                if (!reservation1.isPaid()) {
                    reservation = reservation1;
                    break;
                }
            }
        }
        rentalDetail.setReservation(reservation);
        iCartService.addToCart(rentalDetail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCartItem(@PathVariable Long id) {
        if (iCartService.findCartItemById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        iCartService.deleteCartItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/pay/{totalPrice}")
    public ResponseEntity<Object> onPayment(@PathVariable Double totalPrice,HttpServletRequest request) {
        String jwt = parseJwt(request);
        String username = jwtUtility.getUserNameFromJwtToken(jwt);
        Account account = iAccountService.findAccountByUsername(username);
        List<Reservation> reservationList = account.getCustomer().getReservationList().stream()
                .filter(re -> (!re.isPaid())).collect(Collectors.toList());
        List<RentalDetail> rentalDetailList = reservationList.get(0).getRentalDetailList();
        List<RentalDetail> rentalDetailListBusyCar = rentalDetailList.stream().filter(RentalDetail::isRented).collect(Collectors.toList());
        if (!rentalDetailListBusyCar.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(rentalDetailListBusyCar);
        }

        reservationList.get(0).setPaid(true);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        reservationList.get(0).setBookingDate(dateTimeFormatter.format(LocalDate.now()));
        reservationList.get(0).setTotalPrice(totalPrice);

        iReservationService.setPaidReservation(reservationList.get(0));
        for (RentalDetail rentalDetail : rentalDetailList) {
            rentalDetail.setRented(true);
            iCartService.updateRentalDetail(rentalDetail);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<Object> showHistory(HttpServletRequest request) {

        String jwt = parseJwt(request);
        String username = jwtUtility.getUserNameFromJwtToken(jwt);
        Account account = iAccountService.findAccountByUsername(username);
        List<Reservation> reservationHistoryList = account.getCustomer().getReservationList()
                .stream().filter(Reservation::isPaid).collect(Collectors.toList());
        List<ReservationDTO> reservationDTOHistoryList = new ArrayList<>(reservationHistoryList.size());
        for (Reservation reservation : reservationHistoryList) {
            ReservationDTO reservationDTO = new ReservationDTO();
            reservationDTO.setId(reservation.getId());
            reservationDTO.setBookingDate(reservation.getBookingDate());
            reservationDTO.setTotalPrice(reservation.getTotalPrice());
            reservationDTO.setRentalDetailDTOList(convertToRentalDetailDTO(reservation.getRentalDetailList()));
            reservationDTOHistoryList.add(reservationDTO);
        }
        if (reservationDTOHistoryList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(reservationDTOHistoryList, HttpStatus.OK);

    }


    public List<RentalDetailDTO> convertToRentalDetailDTO(List<RentalDetail> rentalDetailList) {
        List<RentalDetailDTO> rentalDetailDTOList = new ArrayList<>();
        for (RentalDetail rentalDetail : rentalDetailList) {
            RentalDetailDTO rentalDetailDTO = new RentalDetailDTO();
            BeanUtils.copyProperties(rentalDetail, rentalDetailDTO);
            rentalDetailDTO.setCarId(rentalDetail.getCar().getId());
            rentalDetailDTO.setReservationId(rentalDetail.getReservation().getId());
            rentalDetailDTO.setImageList(rentalDetail.getCar().getImageList());
            rentalDetailDTOList.add(rentalDetailDTO);
        }
        return rentalDetailDTOList;
    }
}




















