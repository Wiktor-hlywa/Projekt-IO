package pl.tournaments.user.dto;

import jakarta.validation.constraints.NotNull;
import pl.tournaments.user.Role;

public record ChangeRoleRequest(@NotNull Role role) {
}
