package com.example.carrentalbackend.controller;

import com.example.carrentalbackend.dto.IRentalDetailDTO;
import com.example.carrentalbackend.dto.RentalDetailDTO;
import com.example.carrentalbackend.model.*;
import com.example.carrentalbackend.repository.IReservationRepository;
import com.example.carrentalbackend.security_authentication.jwt.JwtUtility;
import com.example.carrentalbackend.security_authentication.payload.request.LoginRequest;
import com.example.carrentalbackend.security_authentication.service.AccountDetailService;
import com.example.carrentalbackend.security_authentication.service.AccountDetails;
import com.example.carrentalbackend.service.IAccountService;
import com.example.carrentalbackend.service.ICarService;
import com.example.carrentalbackend.service.ICartService;
import com.example.carrentalbackend.service.IReservationService;
import com.example.carrentalbackend.service.impl.AccountService;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @GetMapping("")
    public ResponseEntity<Object> getCart(HttpServletRequest request) {
        String jwt = parseJwt(request);
        String username = jwtUtility.getUserNameFromJwtToken(jwt);
        Account account = iAccountService.findAccountByUsername(username);
        List<RentalDetail> rentalDetailList = account.getCustomer().getReservation().getRentalDetailList();
        if (rentalDetailList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<RentalDetailDTO> cart = new ArrayList<>();
        for (RentalDetail rentalDetail : rentalDetailList) {
            RentalDetailDTO rentalDetailDTO = new RentalDetailDTO();
            BeanUtils.copyProperties(rentalDetail, rentalDetailDTO);
            rentalDetailDTO.setCarId(rentalDetail.getCar().getId());
            rentalDetailDTO.setReservationId(rentalDetail.getReservation().getId());
            rentalDetailDTO.setImageList(rentalDetail.getCar().getImageList());
            cart.add(rentalDetailDTO);
        }
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addToCart(HttpServletRequest request, @RequestBody RentalDetail rentalDetail) {
        List<RentalDetail> rentalDetailList = iCartService.findCarSchedule(rentalDetail.getCar(), rentalDetail.getPickupDate(), rentalDetail.getReturnDate());
        if (!rentalDetailList.isEmpty()) {
            List<RentalDetailDTO> rentalDetailDTOList = new ArrayList<>();
            for (RentalDetail rentalDetail1 : rentalDetailList) {
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
        Reservation reservation = account.getCustomer().getReservation();
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
}





















