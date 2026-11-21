package pl.tournaments.team;

import jakarta.persistence.*;
import pl.tournaments.user.AppUser;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "captain_id")
    private AppUser captain;

    // tabela team_members w bazie
    @ManyToMany
    @JoinTable(
            name = "team_members",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<AppUser> members = new HashSet<>();

    protected Team() {}

    public Team(String name, AppUser captain) {
        this.name = name;
        this.captain = captain;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public AppUser getCaptain() { return captain; }
    public Set<AppUser> getMembers() { return members; }
}
