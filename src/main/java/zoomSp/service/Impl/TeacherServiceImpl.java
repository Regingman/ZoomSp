package zoomSp.service.Impl;

import zoomSp.repo.TeachersRepo;
import zoomSp.service.EntityService;
import zoomSp.service.TeacherService;

import java.util.List;

public class TeacherServiceImpl implements TeacherService, EntityService<TeachersRepo, Long> {
    @Override
    public List<TeachersRepo> getAll() {
        return null;
    }

    @Override
    public TeachersRepo getById(Long id) {
        return null;
    }

    @Override
    public TeachersRepo save(TeachersRepo teachersRepo) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
