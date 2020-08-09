package zoomSp.repo;

import org.springframework.data.jpa.repository.Query;
import zoomSp.domain.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeTablesRepo extends JpaRepository<TimeTable, Long> {
    @Query("select u from TimeTable u where u.day = ?1 and u.numberLesson=?2")
    TimeTable getNowLesson(int day, int numberLesson);
    @Query("select u from TimeTable u where u.groupsId = ?1")
    List<TimeTable> getGroupsLessons(Long groupId);
}

