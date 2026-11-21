package pl.tournaments.tournament.dto;

import pl.tournaments.tournament.Tournament;

import java.time.LocalDate;

public record TournamentResponse(
        Long id,
        String name,
        LocalDate startDate,
        String location,
        String organizer,
        String type,
        String status,
        int maxParticipants
) {
    public static TournamentResponse from(Tournament t) {
        return new TournamentResponse(
                t.getId(),
                t.getName(),
                t.getStartDate(),
                t.getLocation(),
                t.getOrganizer().getNickname(),
                t.getType().name(),
                t.getStatus().name(),
                t.getMaxParticipants()
        );
    }
}