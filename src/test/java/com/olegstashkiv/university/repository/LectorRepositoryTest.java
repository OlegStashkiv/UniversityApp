package com.olegstashkiv.university.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.olegstashkiv.university.model.Department;
import com.olegstashkiv.university.model.Lector;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LectorRepositoryTest {

    @Autowired
    private LectorRepository lectorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void testGetAllByFirstNameOrLastNameContainsIgnoreCase() {
        Lector lector1 = new Lector();
        lector1.setFirstName("John");
        lector1.setLastName("Doe");

        Lector lector2 = new Lector();
        lector2.setFirstName("Alice");
        lector2.setLastName("Smith");

        lectorRepository.saveAll(List.of(lector1, lector2));

        List<Lector> result = lectorRepository.getAllByFirstNameOrLastNameContainsIgnoreCase("jo");

        assertThat(result).contains(lector1);
        assertThat(result).doesNotContain(lector2);
    }

    @Test
    public void testGetAllByDepartmentName() {
        Lector lector = new Lector();
        lector.setFirstName("Jimy");
        lector.setLastName("Van");
        lectorRepository.save(lector);

        Department department = new Department();
        department.setName("History");
        department.setHeadOfDepartment(lector);
        departmentRepository.save(department);

        Lector lector1 = new Lector();
        lector1.setFirstName("John");
        lector1.setLastName("Doe");
        lector1.setDepartments(Set.of(department));

        Lector lector2 = new Lector();
        lector2.setFirstName("Alice");
        lector2.setLastName("Smith");

        lectorRepository.saveAll(List.of(lector1, lector2));

        List<Lector> result = lectorRepository.getAllByDepartmentName("History");

        assertThat(result).contains(lector1);
        assertThat(result).doesNotContain(lector2);
    }
}
