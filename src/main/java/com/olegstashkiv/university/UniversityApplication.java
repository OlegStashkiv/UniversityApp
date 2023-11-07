package com.olegstashkiv.university;

import com.olegstashkiv.university.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class UniversityApplication {
    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }

}
