package zoomSp.service.Impl;

import zoomSp.repo.TimeTablesRepo;
import zoomSp.service.EntityService;
import zoomSp.service.TimeTableService;

import java.util.List;

public class TimeTableServiceImpl implements TimeTableService, EntityService<TimeTablesRepo, Long> {
    @Override
    public List<TimeTablesRepo> getAll() {
        return null;
    }

    @Override
    public TimeTablesRepo getById(Long id) {
        return null;
    }

    @Override
    public TimeTablesRepo save(TimeTablesRepo timeTablesRepo) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
