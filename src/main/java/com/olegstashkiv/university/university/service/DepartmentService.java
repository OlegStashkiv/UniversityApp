package com.olegstashkiv.university.university.service;

import com.olegstashkiv.university.university.model.Department;
import com.olegstashkiv.university.university.model.Lector;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public interface DepartmentService {
    Optional<Department> getByName(String name);

    Lector getHeadOfDepartment(String name);

    Integer showCountOfEmployees(String name);

    BigDecimal getAverageSalary(String name);

    Map<String, Long> getStatistic(String name);
}
