package com.nuu.virtualjourney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.nuu"})
public class VirtualjourneyApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualjourneyApplication.class, args);
    }

}
