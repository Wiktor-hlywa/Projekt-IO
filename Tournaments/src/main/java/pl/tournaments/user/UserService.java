package pl.tournaments.user;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import pl.tournaments.user.dto.RegisterRequest;
import pl.tournaments.user.dto.UserResponse;

@Service
public class UserService {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(AppUserRepository repo, PasswordEncoder encoder){
        this.encoder = encoder;
        this.repo = repo;
    }
    private AppUser getUser(Long id){
        return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "sucker"));
    }
    @Transactional
    public UserResponse register(RegisterRequest req) {
        if (repo.existsByNickname(req.nickname())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, req.nickname() + " fuck you " + req.nickname());
        }
        String passwordHash = encoder.encode(req.password());
        AppUser registrated = new AppUser(req.nickname(),
                req.firstName(),
                req.lastName(),
                passwordHash,
                Role.PLAYER);
        AppUser saved = repo.save(registrated);
        return UserResponse.from(saved);
    }
    @Transactional(readOnly = true)
    public UserResponse getById(Long id){
        return UserResponse.from(this.getUser(id));
    }
    @Transactional(readOnly = true)
    public List<UserResponse> findAll(){
        return repo.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }
    @Transactional
    public UserResponse changeRole(Long userId, Role newRole){
        AppUser user = this.getUser(userId);
        user.setRole(newRole);
        return UserResponse.from(user);
    }


}
