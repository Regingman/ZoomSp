package zoomSp.controller;

import zoomSp.domain.Groups;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoomSp.repo.GroupsRepo;

import java.util.List;

@Api(description = "Операции по взаимодействию с группами")
@RestController
@RequestMapping("groups")
public class GroupsController {
    private final GroupsRepo groupsRepo;

    @Autowired
    public GroupsController(GroupsRepo groupssRepo)
    {
        this.groupsRepo = groupssRepo;
    }

    @ApiOperation(value = "Получения списка всех групп")
    @GetMapping
    public ResponseEntity<List<Groups>> List(){
        List<Groups> groupss = groupsRepo.findAll();
        if(groupss.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(groupss, HttpStatus.OK);
    }


    @ApiOperation(value = "Получения группы по id")
    @GetMapping("{id}")
    public ResponseEntity<Groups> List(@PathVariable("id") Long id){
        Groups groups = groupsRepo.getOne(id);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }


    @ApiOperation(value = "Создание группы")
    @PostMapping
    public ResponseEntity<Groups> create(@RequestBody Groups  groups){
        if(groups == null){
            return   new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        groupsRepo.save(groups);
        return  new ResponseEntity<>(groups, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Обновление группы")
    @PutMapping("{id}")
    public ResponseEntity<Groups> update(
            @PathVariable("id") Long groupsId,
            @RequestBody Groups groups
    )
    {
        Groups groupsFromDb = groupsRepo.findById(groupsId).orElse(null);
        if(groupsFromDb == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(groups, groupsFromDb, "id");
        groupsRepo.save(groupsFromDb);
        return new ResponseEntity<>(groupsFromDb, HttpStatus.OK);
    }

    @ApiOperation(value = "Удаление группы")
    @DeleteMapping("{id}")
    public  ResponseEntity<Groups> delete(@PathVariable("id") Long groupsId){
        Groups groups = groupsRepo.findById(groupsId).orElse(null);
        if(groups == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        groupsRepo.delete(groups);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}