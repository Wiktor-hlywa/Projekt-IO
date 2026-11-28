package pl.tournaments.tournament;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("api/tournaments/{tournamentId}/participants")
public class ParticipantController {
    private final ParticipantService service;

    public ParticipantController(ParticipantService service) {
        this.service = service;
    }

    @PostMapping("/players")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerPlayer(@PathVariable Long tournamentId, @RequestParam Long userId) {
        service.registerPlayer(tournamentId, userId);
    }

    @PostMapping("/teams")
    @ResponseStatus (HttpStatus.CREATED)
    public void registerTeam(@PathVariable Long tournamentId, @RequestParam Long teamId) {
        service.registerTeam(tournamentId, teamId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdraw(@PathVariable Long tournamentId, @PathVariable Long userId) {
        service.withdraw(tournamentId, userId);
    }
}