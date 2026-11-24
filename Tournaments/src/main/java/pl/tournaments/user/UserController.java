package pl.tournaments.user;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.tournaments.user.dto.ChangeRoleRequest;
import pl.tournaments.user.dto.RegisterRequest;
import pl.tournaments.user.dto.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody RegisterRequest req){
        return service.register(req);
    }

    @GetMapping
    public List<UserResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable Long id){
        return service.getById(id);
    }
    @PatchMapping("/{id}/role")
    public UserResponse changeRole(@PathVariable Long id, @Valid @RequestBody ChangeRoleRequest req){
        return service.changeRole(id, req.role());
    }




}
