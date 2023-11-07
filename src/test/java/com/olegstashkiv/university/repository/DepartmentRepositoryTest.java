package com.olegstashkiv.university.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.olegstashkiv.university.model.Department;
import com.olegstashkiv.university.model.Lector;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentRepositoryTest {
    @Autowired
    private DepartmentRepository departmentRepository;

    private Department expectedDepartment;

    @BeforeEach
    void setUp() {
        Lector headOfDepartment = new Lector();
        headOfDepartment.setId(4L);
        headOfDepartment.setFirstName("Anna");
        headOfDepartment.setLastName("Wing");
        headOfDepartment.setSalary(BigDecimal.valueOf(2500));
        headOfDepartment.setDegree(Lector.Degree.PROFESSOR);
        headOfDepartment.setDepartments(Set.of(expectedDepartment));

        Lector lector = new Lector();
        lector.setId(4L);
        lector.setFirstName("David");
        lector.setLastName("Davies");
        lector.setSalary(BigDecimal.valueOf(900));
        lector.setDegree(Lector.Degree.ASSISTANT);
        lector.setDepartments(Set.of(expectedDepartment));

        expectedDepartment = new Department();
        expectedDepartment.setId(1L);
        expectedDepartment.setName("History");
        expectedDepartment.setHeadOfDepartment(headOfDepartment);
        expectedDepartment.setLectors(List.of(headOfDepartment, lector));

    }

    @Test
    @Sql(scripts = {
            "classpath:database/add-data-to_departments-lectors-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/delete-data-from-departments-lectors-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Find department by name")
    public void getDepartmentByName_ExistingName_ReturnDepartment() {
        Department actualDepartment = departmentRepository.getByName("History").get();

        assertEquals(expectedDepartment, actualDepartment);
    }
}
