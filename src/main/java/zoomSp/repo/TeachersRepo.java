package zoomSp.repo;

import zoomSp.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachersRepo extends JpaRepository<Teacher, Long> {
}

