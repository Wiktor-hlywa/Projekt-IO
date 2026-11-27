package pl.tournaments.security;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.tournaments.user.AppUser;
import pl.tournaments.user.Role;
import java.util.List;
import java.util.Collection;


public record AuthenticatedUser(Long id, String nickname, String passwordHash, Role role) implements UserDetails {

    public static AuthenticatedUser from(AppUser u ){
        return new AuthenticatedUser(u.getId(), u.getNickname(), u.getPasswordHash(), u.getRole());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword(){
        return this.passwordHash;
    }

    @Override
    public String getUsername(){
        return this.nickname;
    }
}
