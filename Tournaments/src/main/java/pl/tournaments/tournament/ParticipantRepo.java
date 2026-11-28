package pl.tournaments.tournament;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepo extends JpaRepository<Participant, Long> {
    @EntityGraph(attributePaths = {"tournament", "user"})
    public Optional<Participant> findByTournamentIdAndUserId(Long tournamentId, Long userId);
}