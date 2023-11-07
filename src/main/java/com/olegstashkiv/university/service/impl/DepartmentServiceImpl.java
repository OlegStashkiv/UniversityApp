package com.olegstashkiv.university.service.impl;

import com.olegstashkiv.university.service.DepartmentService;
import com.olegstashkiv.university.service.LectorsService;
import com.olegstashkiv.university.model.Department;
import com.olegstashkiv.university.model.Lector;
import com.olegstashkiv.university.repository.DepartmentRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final LectorsService lectorsService;

    @Override
    public Optional<Department> getByName(String name) {
        return departmentRepository.getByName(name);
    }

    @Override
    public Lector getHeadOfDepartment(String name) {
        Optional<Department> department = getByName(name);
        return department.map(Department::getHeadOfDepartment).orElse(null);
    }

    @Override
    public Integer showCountOfEmployees(String name) {
        return lectorsService.findAllByDepartmentName(name).size();
    }

    @Override
    public BigDecimal getAverageSalary(String name) {
        return BigDecimal.valueOf(
                lectorsService.findAllByDepartmentName(name).stream()
                .mapToDouble(l -> l.getSalary().doubleValue())
                .average()
                .getAsDouble()
        );
    }

    @Override
    public Map<String, Long> getStatistic(String name) {
        List<Object[]> statistics =
                departmentRepository.countLectorsByDegreeAndDepartmentName(name);
        return statistics.stream()
                .collect(Collectors.toMap(
                        array -> array[0].toString(),
                        array -> (Long) array[1]
                ));
    }

}
