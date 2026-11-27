package pl.tournaments.team;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.tournaments.security.AuthenticatedUser;
import pl.tournaments.team.dto.AddMemberRequest;
import pl.tournaments.team.dto.CreateTeamRequest;
import pl.tournaments.team.dto.TeamResponse;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService service;

    public TeamController(TeamService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamResponse create(@Valid @RequestBody CreateTeamRequest req,
                               @AuthenticationPrincipal AuthenticatedUser currentUser){
        return service.create(req, currentUser.id());
    }

    @GetMapping
    public List<TeamResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TeamResponse getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PostMapping("/{id}/members")
    public TeamResponse addMember(@PathVariable Long id,@Valid @RequestBody AddMemberRequest req,
    @AuthenticationPrincipal AuthenticatedUser currentUser){
        return service.addMember(id, req.userId(), currentUser.id());
    }
    @DeleteMapping("/{id}/members/{userId}")
    public TeamResponse removeMember(@PathVariable Long id, @PathVariable Long userId,
                                     @AuthenticationPrincipal AuthenticatedUser currentUser){
        return service.removeMember(id, userId, currentUser.id());
    }

}
