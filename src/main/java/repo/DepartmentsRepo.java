package repo;

import domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepo extends JpaRepository<Department, Long> {
}
