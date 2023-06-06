package study.datajpa4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa4.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
