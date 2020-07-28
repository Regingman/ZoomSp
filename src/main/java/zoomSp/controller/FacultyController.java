package zoomSp.controller;

import zoomSp.domain.Faculty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoomSp.repo.FacultysRepo;

import java.util.List;

@Api(description = "Операции по взаимодействию с факультетами")
@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultysRepo facultyRepo;

    @Autowired
    public FacultyController(FacultysRepo facultysRepo)
    {
        this.facultyRepo = facultysRepo;
    }

    @ApiOperation(value = "Получения списка всех факультетов")
    @GetMapping
    public ResponseEntity<List<Faculty>> List(){
        List<Faculty> facultys = facultyRepo.findAll();
        if(facultys.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(facultys, HttpStatus.OK);
    }


    @ApiOperation(value = "Получения факультета по id")
    @GetMapping("{id}")
    public ResponseEntity<Faculty> List(@PathVariable("id") Long id){
        Faculty faculty = facultyRepo.getOne(id);
        return new ResponseEntity<>(faculty, HttpStatus.OK);
    }


    @ApiOperation(value = "Создание факультета")
    @PostMapping
    public ResponseEntity<Faculty> create(@RequestBody Faculty  faculty){
        if(faculty == null){
            return   new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        facultyRepo.save(faculty);
        return  new ResponseEntity<>(faculty, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Обновление факультета")
    @PutMapping("{id}")
    public ResponseEntity<Faculty> update(
            @PathVariable("id") Long facultyId,
            @RequestBody Faculty faculty
    )
    {
        Faculty facultyFromDb = facultyRepo.findById(facultyId).orElse(null);
        if(facultyFromDb == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(faculty, facultyFromDb, "id");
        facultyRepo.save(facultyFromDb);
        return new ResponseEntity<>(facultyFromDb, HttpStatus.OK);
    }

    @ApiOperation(value = "Удаление факультета")
    @DeleteMapping("{id}")
    public  ResponseEntity<Faculty> delete(@PathVariable("id") Long facultyId){
        Faculty faculty = facultyRepo.findById(facultyId).orElse(null);
        if(faculty == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        facultyRepo.delete(faculty);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}