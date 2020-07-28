package zoomSp.repo;

import zoomSp.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultysRepo extends JpaRepository<Faculty, Long> {
}

