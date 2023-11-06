package com.olegstashkiv.university.university.repository;

import com.olegstashkiv.university.university.model.Department;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> getByName(String name);

    @Query("SELECT l.degree, COUNT(*) FROM Lector l "
            + "JOIN l.departments d "
            + "WHERE d.name = :departmentName "
            + "GROUP BY l.degree")
    List<Object[]> countLectorsByDegreeAndDepartmentName(String departmentName);
}
