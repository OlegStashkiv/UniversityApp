package com.olegstashkiv.university.service;

import com.olegstashkiv.university.model.Lector;
import java.util.List;

public interface LectorsService {
    List<Lector> searchByNameOrLastName(String value);

    List<Lector> findAllByDepartmentName(String name);

}
