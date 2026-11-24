package pl.tournaments.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepo extends JpaRepository<Team, Long> {
    boolean existsByName(String name);
    Optional<Team> findByName(String name);
}
