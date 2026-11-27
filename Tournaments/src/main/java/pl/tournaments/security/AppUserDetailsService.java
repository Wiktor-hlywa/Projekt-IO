package pl.tournaments.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.tournaments.user.AppUserRepository;


@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository repo;

    public AppUserDetailsService(AppUserRepository repo){
        this.repo = repo;
    }


    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        return repo.findByNickname(nickname)
                .map(AuthenticatedUser::from)
                .orElseThrow(() -> new UsernameNotFoundException("suck my balls"));
    }
}
