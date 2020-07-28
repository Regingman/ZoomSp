package repo;

import domain.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTablesRepo extends JpaRepository<TimeTable, Long> {
}

