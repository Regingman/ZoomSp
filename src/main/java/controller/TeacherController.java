package controller;

import domain.Teacher;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.TeachersRepo;

import java.util.List;

@Api(description = "Операции по взаимодействию с учителями")
@RestController
@RequestMapping("teacher")
public class TeacherController {
    private final TeachersRepo teacherRepo;

    @Autowired
    public TeacherController(TeachersRepo teachersRepo)
    {
        this.teacherRepo = teachersRepo;
    }

    @ApiOperation(value = "Получения списка всех учителей")
    @GetMapping
    public ResponseEntity<List<Teacher>> List(){
        List<Teacher> teachers = teacherRepo.findAll();
        if(teachers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }


    @ApiOperation(value = "Получения учителя по id")
    @GetMapping("{id}")
    public ResponseEntity<Teacher> List(@PathVariable("id") Long id){
        Teacher teacher = teacherRepo.getOne(id);
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }


    @ApiOperation(value = "Создание учителя")
    @PostMapping
    public ResponseEntity<Teacher> create(@RequestBody Teacher  teacher){
        if(teacher == null){
            return   new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        teacherRepo.save(teacher);
        return  new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Обновление учителя")
    @PutMapping("{id}")
    public ResponseEntity<Teacher> update(
            @PathVariable("id") Long teacherId,
            @RequestBody Teacher teacher
    )
    {
        Teacher teacherFromDb = teacherRepo.findById(teacherId).orElse(null);
        if(teacherFromDb == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(teacher, teacherFromDb, "id");
        teacherRepo.save(teacherFromDb);
        return new ResponseEntity<>(teacherFromDb, HttpStatus.OK);
    }

    @ApiOperation(value = "Удаление учителя")
    @DeleteMapping("{id}")
    public  ResponseEntity<Teacher> delete(@PathVariable("id") Long teacherId){
        Teacher teacher = teacherRepo.findById(teacherId).orElse(null);
        if(teacher == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        teacherRepo.delete(teacher);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}