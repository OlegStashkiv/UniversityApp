package com.olegstashkiv.university.repository;

import com.olegstashkiv.university.model.Department;
import com.olegstashkiv.university.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TestRepository {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testGetByName() {
        // Arrange
        Department department = new Department();
        department.setName("Test Department");
        entityManager.persist(department);
        entityManager.flush();

        // Act
        Optional<Department> foundDepartment = departmentRepository.getByName("Test Department");

        // Assert
        assertThat(foundDepartment).isPresent();
        assertThat(foundDepartment.get().getName()).isEqualTo("Test Department");
    }
}
