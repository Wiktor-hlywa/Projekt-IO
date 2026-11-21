package pl.tournaments.tournament.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pl.tournaments.tournament.TournamentType;

import java.time.LocalDate;

public record CreateTournamentRequest(
        @NotBlank String name,
        LocalDate startDate,
        String location,
        @NotNull Long organizerId,
        @NotNull TournamentType type,
        @NotNull @Min(2) Integer maxParticipants
) {
}