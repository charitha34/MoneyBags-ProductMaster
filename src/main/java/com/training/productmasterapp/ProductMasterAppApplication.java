package com.training.productmasterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication

@RestController

public class ProductMasterAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductMasterAppApplication.class, args);
    }

}
