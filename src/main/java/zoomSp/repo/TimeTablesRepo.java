package zoomSp.repo;

import zoomSp.domain.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTablesRepo extends JpaRepository<TimeTable, Long> {
}

