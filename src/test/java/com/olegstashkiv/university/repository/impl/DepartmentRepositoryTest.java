package com.olegstashkiv.university.repository.impl;

import com.olegstashkiv.university.model.Department;
import com.olegstashkiv.university.model.Lector;
import com.olegstashkiv.university.repository.DepartmentRepository;
import com.olegstashkiv.university.repository.LectorRepository;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LectorRepository lectorRepository;

    @Test
    public void testCountLectorsByDegreeAndDepartmentName() {
        Lector headOfDepartment = new Lector();
        headOfDepartment.setFirstName("Bob");
        headOfDepartment.setLastName("Vilson");
        lectorRepository.save(headOfDepartment);

        Department department = new Department();
        department.setName("Test Department");
        department.setHeadOfDepartment(headOfDepartment);
        departmentRepository.save(department);

        Lector lector1 = new Lector();
        lector1.setFirstName("John");
        lector1.setLastName("Doe");
        lector1.setDegree(Lector.Degree.PROFESSOR);
        lector1.setDepartments(Set.of(department));
        lectorRepository.save(lector1);

        Lector lector2 = new Lector();
        lector2.setFirstName("Alice");
        lector2.setLastName("Smith");
        lector2.setDegree(Lector.Degree.ASSISTANT);
        lector2.setDepartments(Set.of(department));
        lectorRepository.save(lector2);

        List<Object[]> expected = Arrays.asList(
                new Object[]{Lector.Degree.ASSISTANT.name(), 1L},
                new Object[]{Lector.Degree.PROFESSOR.name(), 1L}
        );

        List<Object[]> result = departmentRepository.countLectorsByDegreeAndDepartmentName("Test Department");

        expected = expected.stream()
                .map(objects -> new Object[]{objects[0].toString(), objects[1]})
                .collect(Collectors.toList());

        result = result.stream()
                .map(objects -> new Object[]{objects[0].toString(), objects[1]})
                .collect(Collectors.toList());

        expected.sort(Comparator.comparing(o -> (String) o[0]));
        result.sort(Comparator.comparing(o -> (String) o[0]));

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(expected);
    }
}
