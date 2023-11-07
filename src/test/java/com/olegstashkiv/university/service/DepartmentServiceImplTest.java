package com.olegstashkiv.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.olegstashkiv.university.model.Department;
import com.olegstashkiv.university.model.Lector;
import com.olegstashkiv.university.repository.DepartmentRepository;
import com.olegstashkiv.university.service.impl.DepartmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.*;

class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private LectorsService lectorsService;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Get Head Of Department")
    void testGetHeadOfDepartment() {
        String departmentName = "History";
        Lector headOfDepartment = new Lector();
        headOfDepartment.setFirstName("John");
        headOfDepartment.setLastName("Doe");

        Department department = new Department();
        department.setName(departmentName);
        department.setHeadOfDepartment(headOfDepartment);

        Optional<Department> mockDepartment = Optional.of(department);
        when(departmentRepository.getByName(departmentName)).thenReturn(mockDepartment);

        Lector result = departmentService.getHeadOfDepartment(departmentName);

        assertEquals(headOfDepartment, result);
    }

    @Test
    @DisplayName("Test Show Count Of Employees")
    void testShowCountOfEmployees() {
        String departmentName = "History";
        Lector lector1 = new Lector();
        lector1.setFirstName("John");
        lector1.setLastName("Doe");
        Lector lector2 = new Lector();
        lector2.setFirstName("Jane");
        lector2.setLastName("Johnson");

        List<Lector> mockLectors = Arrays.asList(lector1, lector2);

        when(lectorsService.findAllByDepartmentName(departmentName)).thenReturn(mockLectors);

        int result = departmentService.showCountOfEmployees(departmentName);

        assertEquals(mockLectors.size(), result);
    }

    @Test
    @DisplayName("Test Get Average Salary")
    void testGetAverageSalary() {
        String departmentName = "History";

        Lector lector1 = new Lector();
        lector1.setFirstName("John");
        lector1.setLastName("Doe");
        lector1.setSalary(BigDecimal.valueOf(3000));

        Lector lector2 = new Lector();
        lector2.setFirstName("Jane");
        lector2.setLastName("Johnson");
        lector2.setSalary(BigDecimal.valueOf(2500));

        List<Lector> mockLectors = Arrays.asList(lector1, lector2);

        when(lectorsService.findAllByDepartmentName(departmentName)).thenReturn(mockLectors);

        BigDecimal expectedAverageSalary = BigDecimal.valueOf(2750.0);
        BigDecimal result = departmentService.getAverageSalary(departmentName);

        assertEquals(expectedAverageSalary, result);
    }

    @Test
    @DisplayName("Test Get Statistic")
    void testGetStatistic() {
        String departmentName = "History";
        Object[] assistantArray = new Object[]{"ASSISTANT", 2L};
        Object[] professorArray = new Object[]{"PROFESSOR", 1L};
        List<Object[]> mockStatistics = Arrays.asList(assistantArray, professorArray);

        when(departmentRepository.countLectorsByDegreeAndDepartmentName(departmentName)).thenReturn(mockStatistics);

        Map<String, Long> expectedStatistic = new HashMap<>();
        expectedStatistic.put("ASSISTANT", 2L);
        expectedStatistic.put("PROFESSOR", 1L);

        Map<String, Long> result = departmentService.getStatistic(departmentName);

        assertEquals(expectedStatistic, result);
    }
}
