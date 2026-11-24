package pl.tournaments.team.dto;

import jakarta.validation.constraints.NotNull;

public record AddMemberRequest(
        @NotNull Long userId
) {
}
