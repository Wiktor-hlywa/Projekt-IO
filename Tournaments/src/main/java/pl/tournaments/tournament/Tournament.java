package pl.tournaments.tournament;

import jakarta.persistence.*;
import pl.tournaments.user.AppUser;

import java.time.LocalDate;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate startDate;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizer_id", nullable = false)
    private AppUser organizer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TournamentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TournamentStatus status = TournamentStatus.DRAFT;

    @Column(nullable = false)
    private Integer maxParticipants;

    protected Tournament() {}

    public Tournament(String name, LocalDate startDate, String location,
                      AppUser organizer, TournamentType type, int maxParticipants) {
        this.name = name;
        this.startDate = startDate;
        this.location = location;
        this.organizer = organizer;
        this.type = type;
        this.maxParticipants = maxParticipants;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public LocalDate getStartDate() { return startDate; }
    public String getLocation() { return location; }
    public AppUser getOrganizer() { return organizer; }
    public TournamentType getType() { return type; }
    public TournamentStatus getStatus() { return status; }
    public Integer getMaxParticipants() { return maxParticipants; }
}
