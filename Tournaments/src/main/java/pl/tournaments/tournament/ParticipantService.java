package pl.tournaments.tournament;

import java.beans.Transient;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pl.tournaments.team.Team;
import pl.tournaments.team.TeamRepo;
import pl.tournaments.user.AppUser;
import pl.tournaments.user.AppUserRepository;
import pl.tournaments.tournament.TournamentRepository;

@Service
public class ParticipantService {
    private final ParticipantRepo participantRepo;
    private final TournamentRepository tournamentRepo;
    private final AppUserRepository userRepo;
    private final TeamRepo teamRepo;

    public ParticipantService(ParticipantRepo participantRepo, TournamentRepository tournamentRepo, AppUserRepository userRepo, TeamRepo teamRepo) {
        this.participantRepo = participantRepo;
        this.tournamentRepo = tournamentRepo;
        this.userRepo = userRepo;
        this.teamRepo = teamRepo;
    }

    @Transactional 
    public void registerPlayer(Long tournamentId, Long userId) {
        Tournament t = tournamentRepo.findById(tournamentId).orElseThrow(() -> new IllegalArgumentException("Nie znaleziono turnieju"));
        AppUser u = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));

        participantRepo.save(Participant.ofUser(t, u));
    }

    @Transactional
    public void registerTeam(Long tournamentId, Long teamId) {
        Tournament t = tournamentRepo.findById(tournamentId).orElseThrow(() -> new IllegalArgumentException("Nie znaleziono turnieju"));
        Team team = teamRepo.findById(teamId).orElseThrow(() -> new IllegalArgumentException("Nie znaleziono drużyny"));

        participantRepo.save(Participant.ofTeam(t, team));
    }

    @Transactional
    public void withdraw(Long tournamentId, Long userId) {
        Participant p = participantRepo.findByTournamentIdAndUserId(tournamentId, userId)
        .orElseThrow(() -> new IllegalArgumentException("Gracz nie jest zapisany do turnieju"));

        participantRepo.delete(p);  // Tutaj usuniemy uczestnika z turnieju, więc w trakcie generowania drabinki będą problemy, bo nie będzie uczestnika. Lepiej ustawić enuma na withdrawn i filtrować w drabince tylko aktywnych uczestników
    }
}