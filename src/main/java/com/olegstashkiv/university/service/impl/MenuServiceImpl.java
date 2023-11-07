package com.olegstashkiv.university.service.impl;

import com.olegstashkiv.university.model.Department;
import com.olegstashkiv.university.model.Lector;
import com.olegstashkiv.university.service.DepartmentService;
import com.olegstashkiv.university.service.LectorsService;
import com.olegstashkiv.university.service.MenuService;
import com.olegstashkiv.university.service.OutputPrinter;
import com.olegstashkiv.university.service.UserInputProvider;
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

    private final UserInputProvider userInputProvider;
    private final OutputPrinter outputPrinter;
    private final DepartmentService departmentService;
    private final LectorsService lectorsService;

    @Override
    public void showMenu() {
        outputPrinter.print(MAIN_MENU);
    }

    @Override
    public void choseOption() {
        String option = userInputProvider.getUserInput();

        switch (option) {
            case "1" -> getHeadOfDepartment();
            case "2" -> showDepartmentStatistic();
            case "3" -> showAverageSalaryForDepartment();
            case "4" -> showCountOfEmployeeForDepartment();
            case "5" -> globalSearch();
            case "6" -> exit();
            default -> {
                outputPrinter.print(DEFAULT_OPTION_MESSAGE);
                choseOption();
            }
        }
    }

    public void getHeadOfDepartment() {
        Department department = getDepartment();
        Lector headOfDepartment = department.getHeadOfDepartment();
        outputPrinter.print("Head of " + department.getName() + "  department is "
                + headOfDepartment.getFirstName() + " "
                + headOfDepartment.getLastName());
        backToMenu();
    }

    public void showDepartmentStatistic() {
        Department department = getDepartment();
        Map<String, Long> statistic = departmentService.getStatistic(department.getName());
        outputPrinter.print(department.getName() + " statistics: ");
        for (Map.Entry<String, Long> entry : statistic.entrySet()) {
            outputPrinter.print(entry.getKey() + " - " + entry.getValue());
        }
        backToMenu();
    }

    public void showAverageSalaryForDepartment() {
        Department department = getDepartment();
        BigDecimal averageSalary = departmentService.getAverageSalary(department.getName());
        outputPrinter.print("The average salary of "
                + department.getName()
                + " is " + averageSalary
        );
        backToMenu();
    }

    public void showCountOfEmployeeForDepartment() {
        Department department = getDepartment();
        Integer countOfEmployees = departmentService.showCountOfEmployees(department.getName());
        outputPrinter.print(countOfEmployees.toString());
        backToMenu();
    }

    public void globalSearch() {
        outputPrinter.print(SEARCH_MESSAGE);
        String value = userInputProvider.getUserInput();

        List<Lector> lectors = lectorsService.searchByNameOrLastName(value);
        StringBuilder searchResult = new StringBuilder();
        if (lectors != null) {
            for (Lector l : lectors) {
                searchResult.append(l.getFirstName());
                searchResult.append(" ");
                searchResult.append(l.getLastName());
                searchResult.append(", ");
            }
        }

        if (!searchResult.isEmpty()) {
            outputPrinter.print(searchResult.substring(0, searchResult.length() - 2));
        } else {
            outputPrinter.print(NOTHING_NOT_FOUND_MESSAGE);
        }

        backToMenu();
    }

    private Department getDepartment() {
        outputPrinter.print(DEPARTMENT_NAME_MESSAGE);
        String departmentName = userInputProvider.getUserInput();
        Optional<Department> department = departmentService.getByName(departmentName);
        if (department.isEmpty()) {
            outputPrinter.print(DEPARTMENT_DOES_NOT_EXIST_MESSAGE);
        }
        return department.orElseGet(this::getDepartment);
    }

    private void backToMenu() {
        outputPrinter.print(CONTINUE_MESSAGE);
        String answer = userInputProvider.getUserInput();
        switch (answer.toLowerCase()) {
            case "yes" -> showMenu();
            case "no" -> exit();
            default -> {
                outputPrinter.print(WRONG_ANSWER);
            }
        }
    }

    private void exit() {
        System. exit(0);
    }

}
