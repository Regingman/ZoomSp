package repo;

import domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultysRepo extends JpaRepository<Faculty, Long> {
}

