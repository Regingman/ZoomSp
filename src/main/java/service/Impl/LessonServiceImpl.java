package service.Impl;

import repo.LessonsRepo;
import service.EntityService;
import service.LessonService;

import java.util.List;

public class LessonServiceImpl implements LessonService, EntityService<LessonsRepo, Long> {
    @Override
    public List<LessonsRepo> getAll() {
        return null;
    }

    @Override
    public LessonsRepo getById(Long id) {
        return null;
    }

    @Override
    public LessonsRepo save(LessonsRepo lessonsRepo) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
