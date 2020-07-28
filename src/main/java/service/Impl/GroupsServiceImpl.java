package service.Impl;

import domain.Groups;
import org.springframework.beans.factory.annotation.Autowired;
import repo.GroupsRepo;
import service.EntityService;
import service.GroupsService;

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
