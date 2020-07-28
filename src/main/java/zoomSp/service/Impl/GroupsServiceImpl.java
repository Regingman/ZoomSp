package zoomSp.service.Impl;

import zoomSp.domain.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import zoomSp.repo.GroupsRepo;
import zoomSp.service.EntityService;
import zoomSp.service.GroupsService;

import java.util.List;

public class GroupsServiceImpl implements GroupsService, EntityService<Groups, Long> {
    @Autowired
    GroupsRepo groupsRepo;

    @Override
    public List<Groups> getAll() {
        return null;
    }

    @Override
    public Groups getById(Long id) {
        return null;
    }

    @Override
    public Groups save(Groups groups) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
