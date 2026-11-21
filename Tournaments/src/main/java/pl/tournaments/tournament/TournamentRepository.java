package pl.tournaments.tournament;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    @EntityGraph(attributePaths = "organizer")
    List<Tournament> findAllByOrderByIdDesc();
}
