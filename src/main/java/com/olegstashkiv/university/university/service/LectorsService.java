package com.olegstashkiv.university.university.service;

import com.olegstashkiv.university.university.model.Lector;
import java.util.List;

public interface LectorsService {
    List<Lector> searchByNameOrLastName(String value);

    List<Lector> findAllByDepartmentName(String name);

}
