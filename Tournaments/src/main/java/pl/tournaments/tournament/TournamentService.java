package pl.tournaments.tournament;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pl.tournaments.tournament.dto.CreateTournamentRequest;
import pl.tournaments.tournament.dto.TournamentResponse;
import pl.tournaments.user.AppUser;
import pl.tournaments.user.AppUserRepository;
import pl.tournaments.user.Role;

import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournaments;
    private final AppUserRepository users;

    public TournamentService(TournamentRepository tournaments, AppUserRepository users) {
        this.tournaments = tournaments;
        this.users = users;
    }

    @Transactional
    public TournamentResponse create(CreateTournamentRequest req) {
        AppUser organizer = users.findById(req.organizerId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Brak uzytkownika o id " + req.organizerId()));

        if (organizer.getRole() != Role.ORGANIZER && organizer.getRole() != Role.ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Tylko organizator lub admin moze tworzyc turnieje");
        }

        Tournament tournament = new Tournament(
                req.name(), req.startDate(), req.location(),
                organizer, req.type(), req.maxParticipants());

        return TournamentResponse.from(tournaments.save(tournament));
    }

    @Transactional(readOnly = true)
    public List<TournamentResponse> findAll() {
        return tournaments.findAllByOrderByIdDesc().stream()
                .map(TournamentResponse::from)
                .toList();
    }
}
