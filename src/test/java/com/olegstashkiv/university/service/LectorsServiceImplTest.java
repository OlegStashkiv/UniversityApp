package com.olegstashkiv.university.service;

import com.olegstashkiv.university.model.Lector;
import com.olegstashkiv.university.repository.LectorRepository;
import com.olegstashkiv.university.service.impl.LectorsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LectorsServiceImplTest {

    @Mock
    private LectorRepository lectorRepository;

    @InjectMocks
    private LectorsServiceImpl lectorsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Search Lector by Name or Surname")
    void testSearchByNameOrLastName() {
        String searchValue = "John";
        Lector lector1 = new Lector();
        lector1.setFirstName("John");
        lector1.setLastName("Doe");
        Lector lector2 = new Lector();
        lector2.setFirstName("Jane");
        lector2.setLastName("Johnson");
        List<Lector> mockLectors = Arrays.asList(lector1, lector2);

        when(lectorRepository.getAllByFirstNameOrLastNameContainsIgnoreCase(searchValue)).thenReturn(mockLectors);

        List<Lector> lectors = lectorsService.searchByNameOrLastName(searchValue);

        assertEquals(mockLectors, lectors);
    }

    @Test
    @DisplayName("Test Find All Lector by Department Name")
    void testFindAllByDepartmentName() {
        String departmentName = "History";
        Lector lector1 = new Lector();
        lector1.setFirstName("John");
        lector1.setLastName("Doe");
        Lector lector2 = new Lector();
        lector2.setFirstName("Jane");
        lector2.setLastName("Johnson");
        List<Lector> mockLectors = Arrays.asList(lector1, lector2);

        when(lectorRepository.getAllByDepartmentName(departmentName)).thenReturn(mockLectors);

        List<Lector> lectors = lectorsService.findAllByDepartmentName(departmentName);

        assertEquals(mockLectors, lectors);
    }
}
