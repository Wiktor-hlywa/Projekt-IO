package pl.tournaments.team;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pl.tournaments.team.dto.CreateTeamRequest;
import pl.tournaments.team.dto.TeamResponse;
import pl.tournaments.user.AppUser;
import pl.tournaments.user.AppUserRepository;
import pl.tournaments.user.dto.UserResponse;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepo repoTeam;
    private final AppUserRepository repoUser;

    private AppUser getUser(Long id){
        return repoUser.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sucker"));
    }

    private Team getTeam(Long id){
        return repoTeam.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sucker"));
    }

    public TeamService(TeamRepo repoTeam, AppUserRepository repoUser){
        this.repoTeam = repoTeam;
        this.repoUser = repoUser;
    }

    @Transactional
    public TeamResponse create(CreateTeamRequest req){
        if(repoTeam.existsByName(req.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "suck my dick");

        }
        AppUser captain = getUser(req.captainId());
        Team createdTeam = new Team(req.name(), captain);
        createdTeam.getMembers().add(captain);
        repoTeam.save(createdTeam);
        return TeamResponse.from(createdTeam);
    }

    @Transactional(readOnly = true)
    public TeamResponse getById(Long id){
        return TeamResponse.from(getTeam(id));
    }

    @Transactional
    public TeamResponse addMember(Long teamId, Long userId){
        Team team = getTeam(teamId);
        if(!team.getMembers().add(getUser(userId))){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "piss off");
        }
        return TeamResponse.from(team);

    }

    @Transactional
    public TeamResponse removeMember(Long teamId, Long userId){
        Team team = getTeam(teamId);
        if(team.getCaptain() != null &&team.getCaptain().getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "poop");
        }
        if(!team.getMembers().remove(getUser(userId))){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "crap");

        }
        return TeamResponse.from(team);
    }

    @Transactional(readOnly = true)
    public List<TeamResponse> findAll(){
        return repoTeam.findAll().stream()
                .map(TeamResponse::from)
                .toList();
    }


}
