package com.olegstashkiv.university.service.impl;

import com.olegstashkiv.university.model.Lector;
import com.olegstashkiv.university.repository.LectorRepository;
import com.olegstashkiv.university.service.LectorsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectorsServiceImpl implements LectorsService {
    private final LectorRepository lectorRepository;

    @Override
    public List<Lector> searchByNameOrLastName(String value) {
        return lectorRepository.getAllByFirstNameOrLastNameContainsIgnoreCase(value);
    }

    @Override
    public List<Lector> findAllByDepartmentName(String name) {
        return lectorRepository.getAllByDepartmentName(name);
    }

}
