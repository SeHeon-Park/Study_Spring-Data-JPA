package study.datajpa.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.datajpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
