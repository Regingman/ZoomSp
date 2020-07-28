package zoomSp.controller;

import zoomSp.domain.Department;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoomSp.repo.DepartmentsRepo;

import java.util.List;

@Api(description = "Операции по взаимодействию с Кафедрами")
@RestController
@RequestMapping("department")
public class DepartmentController {
    private final DepartmentsRepo departmentRepo;

    @Autowired
    public DepartmentController(DepartmentsRepo departmentsRepo)
    {
        this.departmentRepo = departmentsRepo;
    }

    @ApiOperation(value = "Получения списка всех кафедр")
    @GetMapping
    public ResponseEntity<List<Department>> List(){
        List<Department> departments = departmentRepo.findAll();
        if(departments.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }


    @ApiOperation(value = "Получения кафедры по id")
    @GetMapping("{id}")
    public ResponseEntity<Department> List(@PathVariable("id") Long id){
        Department department = departmentRepo.getOne(id);
        return new ResponseEntity<>(department, HttpStatus.OK);
    }


    @ApiOperation(value = "Создание Кафедры")
    @PostMapping
    public ResponseEntity<Department> create(@RequestBody Department  department){
        if(department == null){
            return   new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        departmentRepo.save(department);
        return  new ResponseEntity<>(department, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Обновление кафедры")
    @PutMapping("{id}")
    public ResponseEntity<Department> update(
            @PathVariable("id") Long departmentId,
            @RequestBody Department department
    )
    {
        Department departmentFromDb = departmentRepo.findById(departmentId).orElse(null);
        if(departmentFromDb == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(department, departmentFromDb, "id");
        departmentRepo.save(departmentFromDb);
        return new ResponseEntity<>(departmentFromDb, HttpStatus.OK);
    }

    @ApiOperation(value = "Удаление кафедры")
    @DeleteMapping("{id}")
    public  ResponseEntity<Department> delete(@PathVariable("id") Long departmentId){
        Department department = departmentRepo.findById(departmentId).orElse(null);
        if(department == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        departmentRepo.delete(department);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}