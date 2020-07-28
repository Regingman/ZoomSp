package service.Impl;

import domain.Faculty;
import service.EntityService;
import service.FacultyService;

import java.util.List;

public class FacultyServiceImpl implements FacultyService, EntityService<Faculty, Long> {
    @Override
    public List<Faculty> getAll() {
        return null;
    }

    @Override
    public Faculty getById(Long id) {
        return null;
    }

    @Override
    public Faculty save(Faculty faculty) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}

