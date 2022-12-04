package ee.laus.involvedsectors.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String sessionKey;
    @OneToMany(mappedBy = "user")
    private List<InvolvedSector> sectors;

    public User(String name, String sessionKey) {
        this.name = name;
        this.sessionKey = sessionKey;
    }
}
