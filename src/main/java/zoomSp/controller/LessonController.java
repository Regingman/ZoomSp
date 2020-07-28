package zoomSp.controller;

import zoomSp.domain.Lesson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoomSp.repo.LessonsRepo;

import java.util.List;

@Api(description = "Операции по взаимодействию с уроками")
@RestController
@RequestMapping("lesson")
public class LessonController {
    private final LessonsRepo lessonRepo;

    @Autowired
    public LessonController(LessonsRepo lessonsRepo)
    {
        this.lessonRepo = lessonsRepo;
    }

    @ApiOperation(value = "Получения списка всех уроков")
    @GetMapping
    public ResponseEntity<List<Lesson>> List(){
        List<Lesson> lessons = lessonRepo.findAll();
        if(lessons.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }


    @ApiOperation(value = "Получения урока по id")
    @GetMapping("{id}")
    public ResponseEntity<Lesson> List(@PathVariable("id") Long id){
        Lesson lesson = lessonRepo.getOne(id);
        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }


    @ApiOperation(value = "Создание урока")
    @PostMapping
    public ResponseEntity<Lesson> create(@RequestBody Lesson  lesson){
        if(lesson == null){
            return   new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        lessonRepo.save(lesson);
        return  new ResponseEntity<>(lesson, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Обновление урока")
    @PutMapping("{id}")
    public ResponseEntity<Lesson> update(
            @PathVariable("id") Long lessonId,
            @RequestBody Lesson lesson
    )
    {
        Lesson lessonFromDb = lessonRepo.findById(lessonId).orElse(null);
        if(lessonFromDb == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BeanUtils.copyProperties(lesson, lessonFromDb, "id");
        lessonRepo.save(lessonFromDb);
        return new ResponseEntity<>(lessonFromDb, HttpStatus.OK);
    }

    @ApiOperation(value = "Удаление урока")
    @DeleteMapping("{id}")
    public  ResponseEntity<Lesson> delete(@PathVariable("id") Long lessonId){
        Lesson lesson = lessonRepo.findById(lessonId).orElse(null);
        if(lesson == null){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        lessonRepo.delete(lesson);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}