package pl.tournaments.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.tournaments.security.dto.LoginRequest;
import pl.tournaments.user.UserService;
import pl.tournaments.user.dto.UserResponse;

import org.springframework.security.core.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager manager;
    private final UserService userService;
    private final SecurityContextRepository secRepo =
            new HttpSessionSecurityContextRepository();

    public AuthController(AuthenticationManager manager, UserService service){
        this.manager = manager;
        this.userService = service;
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest req, HttpServletRequest request,
                              HttpServletResponse response){
        Authentication authentication;
        try{
            authentication = manager.authenticate(UsernamePasswordAuthenticationToken.unauthenticated(req.nickname(), req.password()));
        }
        catch(AuthenticationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "wrong nickname or password");
        }
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        this.secRepo.saveContext(context, request, response);
        return userService.getById(((AuthenticatedUser) authentication.getPrincipal()).id());


    }
}
