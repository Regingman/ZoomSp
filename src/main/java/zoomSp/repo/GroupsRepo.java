package zoomSp.repo;

import zoomSp.domain.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepo extends JpaRepository<Groups, Long> {
}

