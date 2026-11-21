package pl.tournaments.tournament;

import jakarta.persistence.*;
import pl.tournaments.team.Team;
import pl.tournaments.user.AppUser;

@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    // dokładnie jedno z dwóch jest ustawione: user albo team
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    protected Participant() {}

    public static Participant ofUser(Tournament tournament, AppUser user) {
        Participant p = new Participant();
        p.tournament = tournament;
        p.user = user;
        return p;
    }

    public static Participant ofTeam(Tournament tournament, Team team) {
        Participant p = new Participant();
        p.tournament = tournament;
        p.team = team;
        return p;
    }

    public Long getId() { return id; }
    public Tournament getTournament() { return tournament; }
    public AppUser getUser() { return user; }
    public Team getTeam() { return team; }
}