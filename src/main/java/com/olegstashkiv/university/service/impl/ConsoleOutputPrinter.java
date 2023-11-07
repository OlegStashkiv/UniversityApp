package com.olegstashkiv.university.service.impl;

import com.olegstashkiv.university.service.OutputPrinter;
import org.springframework.stereotype.Service;

@Service
public class ConsoleOutputPrinter implements OutputPrinter {

    @Override
    public void print(String message) {
        System.out.println(message);
    }
}
