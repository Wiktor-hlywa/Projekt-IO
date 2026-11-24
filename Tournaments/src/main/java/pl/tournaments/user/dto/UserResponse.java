package pl.tournaments.user.dto;

import pl.tournaments.user.AppUser;
import pl.tournaments.user.Role;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String nickname,
        Role role
) {
    public static UserResponse from(AppUser u){
        return new UserResponse(
                u.getId(),
                u.getFirstName(),
                u.getLastName(),
                u.getNickname(),
                u.getRole()
        );
    }

}
