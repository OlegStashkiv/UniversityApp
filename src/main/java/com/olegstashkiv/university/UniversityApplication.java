package com.olegstashkiv.university;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class UniversityApplication {
    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }

}
