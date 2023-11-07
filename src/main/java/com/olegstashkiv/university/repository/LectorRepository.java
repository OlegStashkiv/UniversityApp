package com.olegstashkiv.university.repository;

import com.olegstashkiv.university.model.Lector;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
    @Query("SELECT l FROM Lector l "
            + "WHERE UPPER(l.firstName) LIKE UPPER(concat('%', :value, '%')) "
            + "OR UPPER(l.lastName) LIKE UPPER(concat('%', :value, '%'))")
    List<Lector> getAllByFirstNameOrLastNameContainsIgnoreCase(String value);

    @Query("SELECT l FROM Lector l LEFT JOIN FETCH l.departments d where d.name = :departmentName")
    List<Lector> getAllByDepartmentName(String departmentName);

}
