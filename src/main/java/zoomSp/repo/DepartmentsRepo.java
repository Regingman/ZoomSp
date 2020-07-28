package zoomSp.repo;

import zoomSp.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepo extends JpaRepository<Department, Long> {
}
