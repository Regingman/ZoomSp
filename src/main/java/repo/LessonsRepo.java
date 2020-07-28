package repo;

import domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonsRepo extends JpaRepository<Lesson, Long> {
}

