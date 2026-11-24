package pl.tournaments.user;
import jakarta.persistence.*;

@Entity
@Table(name = "app_users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String firstName;
    private String lastName;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    protected AppUser() {}
    public AppUser(
                   String nickname,
                   String firstName,
                   String lastName,
                   String passwordHash,
                   Role role){
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public void setRole(Role r){
        this.role = r;
    }
    public Long getId() { return id; }
    public String getNickname() { return nickname; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
}
