package pl.tournaments.team.dto;

import pl.tournaments.team.Team;
import pl.tournaments.user.AppUser;

import java.util.List;

public record TeamResponse(
        Long id,
        String name,
        Long captainId,
        String captainNickname,
        List<String> members
) {
    public static TeamResponse from(Team t){
        return new TeamResponse(
                t.getId(),
                t.getName(),
                t.getCaptain() != null ? t.getCaptain().getId() : null,
                t.getCaptain() != null ? t.getCaptain().getNickname() : null,
                t.getMembers().stream().map(AppUser::getNickname).toList()
        );
    }
}
