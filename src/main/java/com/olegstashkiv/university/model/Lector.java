package com.olegstashkiv.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "lectors")
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Degree degree;

    @ManyToMany
    @JoinTable(
            name = "departments_lectors",
            joinColumns = @JoinColumn(name = "lector_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Department> departments = new HashSet<>();

    public enum Degree {
        ASSISTANT, ASSOCIATE_PROFESSOR, PROFESSOR
    }
}


