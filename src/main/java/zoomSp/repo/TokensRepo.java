package zoomSp.repo;

import zoomSp.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokensRepo extends JpaRepository<Token, Long> {
}
