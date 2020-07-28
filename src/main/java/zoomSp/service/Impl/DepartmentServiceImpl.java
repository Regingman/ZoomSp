package zoomSp.service.Impl;

import zoomSp.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import zoomSp.repo.DepartmentsRepo;
import zoomSp.service.DepartmentService;
import zoomSp.service.EntityService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService, EntityService<Department, Long> {
    @Autowired
    DepartmentsRepo departmentRepo;

    @Override
    public List<Department> getAll() {
        return null;
    }

    @Override
    public Department getById(Long id) {
        return null;
    }

    @Override
    public Department save(Department department) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
