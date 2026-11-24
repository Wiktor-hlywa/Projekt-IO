package pl.tournaments.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
                              @Size(min = 1, max = 100)String firstName,
                              @Size(min = 1, max = 100)String lastName,
                              @NotBlank@Size(min = 1, max = 50)String nickname,
                              @NotBlank @Size(min = 4, max = 30) String password) {

}
