package com.olegstashkiv.university.terminal;

import com.olegstashkiv.university.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Terminal implements CommandLineRunner {
    private final MenuService menuService;

    @Override
    public void run(String... args) throws Exception {
        menuService.showMenu();
    }
}
