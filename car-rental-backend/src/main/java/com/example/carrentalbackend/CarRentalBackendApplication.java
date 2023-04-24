package com.example.carrentalbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class CarRentalBackendApplication {

    public static void main(String[] args) {
//        String a = "123456";
//        String b = BCrypt.hashpw(a, BCrypt.gensalt(12));
//        System.out.println(b);
//        boolean c = BCrypt.checkpw(a,b);
//        System.out.println(c);
        SpringApplication.run(CarRentalBackendApplication.class, args);
    }

}
