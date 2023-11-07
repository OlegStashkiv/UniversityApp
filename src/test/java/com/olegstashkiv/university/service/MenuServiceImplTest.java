package com.olegstashkiv.university.service;

import com.olegstashkiv.university.model.Department;
import com.olegstashkiv.university.model.Lector;
import com.olegstashkiv.university.service.impl.MenuServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import org.mockito.Spy;

public class MenuServiceImplTest {
    private static final String MAIN_MENU = """
            Welcome to University App. Choose an option by entering the operation number: \n
            1. Show head of department
            2. Show department statistics
            3. Show the average salary for the department
            4. Show count of employee for department
            5. Global search
            6. Exit
            """;

    @Mock
    private UserInputProvider userInputProvider;
    @Mock
    private OutputPrinter outputPrinter;
    @Mock
    private DepartmentService departmentService;
    @Mock
    private LectorsService lectorsService;

    @InjectMocks
    private MenuServiceImpl menuService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShowMenu() {
        when(userInputProvider.getUserInput()).thenReturn("6");
        menuService.showMenu();
        verify(outputPrinter).print(MAIN_MENU);
    }

    @Test
    public void testGetHeadOfDepartment() {
        Department department = new Department();
        department.setName("Test Department");

        Lector headOfDepartment = new Lector();
        headOfDepartment.setFirstName("John");
        headOfDepartment.setLastName("Doe");
        department.setHeadOfDepartment(headOfDepartment);

        when(userInputProvider.getUserInput()).thenReturn("Test Department");
        when(departmentService.getByName("Test Department")).thenReturn(java.util.Optional.of(department));

        menuService.getHeadOfDepartment();

        verify(outputPrinter).print("Head of Test Department  department is John Doe");
    }

    @Test
    public void testShowDepartmentStatistic() {
        Department department = new Department();
        department.setName("Test Department");

        when(userInputProvider.getUserInput()).thenReturn("Test Department");
        when(departmentService.getByName("Test Department")).thenReturn(Optional.of(department));
        when(departmentService.getStatistic("Test Department"))
                .thenReturn(Map.of("Associate Professor", 2L, "Professor", 1L));

        menuService.showDepartmentStatistic();

        verify(outputPrinter).print("Test Department statistics: ");
        verify(outputPrinter).print("Associate Professor - 2");
        verify(outputPrinter).print("Professor - 1");
    }

    @Test
    public void testShowAverageSalaryForDepartment() {
        Department department = new Department();
        department.setName("Test Department");

        when(userInputProvider.getUserInput()).thenReturn("Test Department");
        when(departmentService.getByName("Test Department")).thenReturn(Optional.of(department));
        when(departmentService.getAverageSalary("Test Department")).thenReturn(BigDecimal.valueOf(1500.0));

        menuService.showAverageSalaryForDepartment();

        verify(outputPrinter).print("The average salary of Test Department is 1500.0");
    }

    @Test
    public void testShowCountOfEmployeeForDepartment() {
        Department department = new Department();
        department.setName("Test Department");

        when(userInputProvider.getUserInput()).thenReturn("Test Department");
        when(departmentService.getByName("Test Department")).thenReturn(Optional.of(department));
        when(lectorsService.findAllByDepartmentName("Test Department")).thenReturn(List.of(new Lector(), new Lector()));

        menuService.showCountOfEmployeeForDepartment();

        verify(outputPrinter).print("2");
    }
}
