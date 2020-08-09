package zoomSp.controller;

import zoomSp.domain.TimeTable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoomSp.repo.TimeTablesRepo;

import java.util.List;

@Api(description = "Операции по взаимодействию с парами")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("timeTable")
public class TimeTableController {
    private final TimeTablesRepo timeTableRepo;

    @Autowired
    public TimeTableController(TimeTablesRepo timeTablesRepo)
    {
        this.timeTableRepo = timeTablesRepo;
    }

    @CrossOrigin
    @ApiOperation(value = "Получения списка всех пар")
    @GetMapping
    public ResponseEntity<List<TimeTable>> List(){
        List<TimeTable> timeTables = timeTableRepo.findAll();
        if(timeTables.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(timeTables, HttpStatus.OK);
    }

    @CrossOrigin
    @ApiOperation(value = "Получения списка всех пар одной группы")
    @GetMapping("groups/{id}")
    public ResponseEntity<List<TimeTable>> ListGroups(@PathVariable("id") Long id){
        List<TimeTable> timeTables = timeTableRepo.getGroupsLessons(id);
        if(timeTables.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(timeTables, HttpStatus.OK);
    }


    @CrossOrigin
    @ApiOperation(value = "Получения пар по id")
    @GetMapping("{id}")
    public ResponseEntity<TimeTable> List(@PathVariable("id") Long id){
        TimeTable timeTable = timeTableRepo.getOne(id);
        return new ResponseEntity<>(timeTable, HttpStatus.OK);
    }


    @CrossOrigin
    @ApiOperation(value = "Создание пар")
    @PostMapping
    public ResponseEntity<TimeTable> create(@RequestBody TimeTable  timeTable){
        if(timeTable == null){
            return   new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        timeTableRepo.save(timeTable);
        return  new ResponseEntity<>(timeTable, HttpStatus.CREATED);
    }

    @CrossOrigin
    @ApiOperation(value = "Обновление пар")
    @PutMapping("{id}")
    public ResponseEntity<TimeTable> update(
            @PathVariable("id") Long timeTableId,
            @RequestBody TimeTable timeTable
    )
    {
        TimeTable timeTableFromDb = timeTableRepo.findById(timeTableId).orElse(null);
        if(timeTableFromDb == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(timeTable, timeTableFromDb, "id");
        timeTableRepo.save(timeTableFromDb);
        return new ResponseEntity<>(timeTableFromDb, HttpStatus.OK);
    }

    @CrossOrigin
    @ApiOperation(value = "Удаление пар")
    @DeleteMapping("{id}")
    public  ResponseEntity<TimeTable> delete(@PathVariable("id") Long timeTableId){
        TimeTable timeTable = timeTableRepo.findById(timeTableId).orElse(null);
        if(timeTable == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        timeTableRepo.delete(timeTable);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}