package com.olegstashkiv.university.university.service.impl;

import com.olegstashkiv.university.university.model.Department;
import com.olegstashkiv.university.university.model.Lector;
import com.olegstashkiv.university.university.service.DepartmentService;
import com.olegstashkiv.university.university.service.LectorsService;
import com.olegstashkiv.university.university.service.MenuService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private static final String MAIN_MENU = """
            Welcome to University App. Choose an option by entering the operation number: \n
            1. Show head of department
            2. Show department statistics
            3. Show the average salary for the department
            4. Show count of employee for department
            5. Global search
            6. Exit
            """;
    private static final String DEFAULT_OPTION_MESSAGE =
            "You have selected an operation that does not exist."
                + " Please try again";
    private static final String DEPARTMENT_DOES_NOT_EXIST_MESSAGE =
            "Such department does not exist, try again!";

    private static final String DEPARTMENT_NAME_MESSAGE = "Enter the department name:";
    private static final String SEARCH_MESSAGE = "Enter data to search";
    private static final String NOTHING_NOT_FOUND_MESSAGE = "Nothing was found for your request.";
    private static final String CONTINUE_MESSAGE = "Continue work? Yes/No";
    private static final String WRONG_ANSWER = "You have entered incorrect data. Try again";

    private final DepartmentService departmentService;
    private final LectorsService lectorsService;

    @Override
    public void showMenu() {
        System.out.println(MAIN_MENU);
        choseOption();
    }

    @Override
    public void choseOption() {
        String option = readValue();
        switch (option) {
            case "1" -> getHeadOfDepartment();
            case "2" -> showDepartmentStatistic();
            case "3" -> showAverageSalaryForDepartment();
            case "4" -> showCountOfEmployeeForDepartment();
            case "5" -> globalSearch();
            case "6" -> exit();
            default -> {
                System.out.println(DEFAULT_OPTION_MESSAGE);
                choseOption();
            }
        }
    }

    private void getHeadOfDepartment() {
        Department department = getDepartment();
        Lector headOfDepartment = departmentService.getHeadOfDepartment(department.getName());
        System.out.println("Head of " + department.getName() + "  department is "
                    + headOfDepartment.getFirstName() + " "
                    + headOfDepartment.getLastName());
        backToMenu();
    }

    private void showDepartmentStatistic() {
        Department department = getDepartment();
        Map<String, Long> statistic = departmentService.getStatistic(department.getName());
        System.out.println(department.getName() + " statistics: ");
        for (Map.Entry<String, Long> entry : statistic.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        backToMenu();
    }

    private void showAverageSalaryForDepartment() {
        Department department = getDepartment();
        BigDecimal averageSalary = departmentService.getAverageSalary(department.getName());
        System.out.println("The average salary of "
                + department.getName()
                + " is " + averageSalary
        );
        backToMenu();
    }

    private void showCountOfEmployeeForDepartment() {
        Department department = getDepartment();
        Integer countOfEmployees = departmentService.showCountOfEmployees(department.getName());
        System.out.println(countOfEmployees);
        backToMenu();
    }

    private void globalSearch() {
        System.out.println(SEARCH_MESSAGE);
        String value = readValue();
        List<Lector> lectors = lectorsService.searchByNameOrLastName(value);
        StringBuilder searchResult = new StringBuilder();
        if (lectors != null) {
            for (Lector l : lectors) {
                searchResult.append(l.getFirstName());
                searchResult.append(" ");
                searchResult.append(l.getLastName());
                searchResult.append(", ");
            }
            System.out.println(searchResult.substring(0, searchResult.length() - 2));
        } else {
            System.out.println(NOTHING_NOT_FOUND_MESSAGE);
        }
        backToMenu();
    }

    private Department getDepartment() {
        System.out.println(DEPARTMENT_NAME_MESSAGE);
        String departmentName = readValue();
        Optional<Department> department = departmentService.getByName(departmentName);
        if (department.isEmpty()) {
            System.out.println(DEPARTMENT_DOES_NOT_EXIST_MESSAGE);
        }
        return department.orElseGet(this::getDepartment);
    }

    private String readValue() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("There were problems trying to read data: " + e);
        }
    }

    private void backToMenu() {
        System.out.println(CONTINUE_MESSAGE);
        String answer = readValue();
        switch (answer.toLowerCase()) {
            case "yes" -> showMenu();
            case "no" -> exit();
            default -> {
                System.out.println(WRONG_ANSWER);
                backToMenu();
            }
        }
    }

    private void exit() {
        System. exit(0);
    }

}
