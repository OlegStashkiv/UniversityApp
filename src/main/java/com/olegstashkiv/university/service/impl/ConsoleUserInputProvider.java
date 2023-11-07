package com.olegstashkiv.university.service.impl;

import com.olegstashkiv.university.service.UserInputProvider;
import java.util.Scanner;
import org.springframework.stereotype.Service;

@Service
public class ConsoleUserInputProvider implements UserInputProvider {
    private final Scanner scanner;

    public ConsoleUserInputProvider() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public String getUserInput() {
        return scanner.nextLine();
    }
}
