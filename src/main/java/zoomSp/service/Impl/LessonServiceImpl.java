package zoomSp.service.Impl;

import zoomSp.repo.LessonsRepo;
import zoomSp.service.EntityService;
import zoomSp.service.LessonService;

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
