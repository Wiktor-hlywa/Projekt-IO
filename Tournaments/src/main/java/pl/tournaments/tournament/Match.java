package pl.tournaments.tournament;

import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @Column(nullable = false)
    private Integer round;

    @Column(nullable = false)
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant1_id")
    private Participant participant1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant2_id")
    private Participant participant2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private Participant winner;

    protected Match() {}

    public Match(Tournament tournament, int round, int position) {
        this.tournament = tournament;
        this.round = round;
        this.position = position;
    }

    public Long getId() { return id; }
    public Tournament getTournament() { return tournament; }
    public Integer getRound() { return round; }
    public Integer getPosition() { return position; }
    public Participant getParticipant1() { return participant1; }
    public Participant getParticipant2() { return participant2; }
    public Participant getWinner() { return winner; }

    public void setParticipant1(Participant p) { this.participant1 = p; }
    public void setParticipant2(Participant p) { this.participant2 = p; }
    public void setWinner(Participant p) { this.winner = p; }
}
