package zoomSp.repo;

import zoomSp.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonsRepo extends JpaRepository<Lesson, Long> {
}

