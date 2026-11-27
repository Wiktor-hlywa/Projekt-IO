package pl.tournaments.team.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTeamRequest(
        @Size(min = 2, max = 100) @NotBlank String name
) {
}
