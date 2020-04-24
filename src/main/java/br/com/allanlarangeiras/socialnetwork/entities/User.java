package br.com.allanlarangeiras.socialnetwork.entities;

import br.com.allanlarangeiras.socialnetwork.types.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "User")
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    @Column
    private String encryptedPassword;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Gender gender;

    @Column
    private Boolean active;

    @ManyToMany
    @JoinTable(
        name = "Follow",
        joinColumns = { @JoinColumn(name = "from") },
        inverseJoinColumns = { @JoinColumn(name = "to") }
    )
    private Set<User> following;

    @ManyToMany
    @JoinTable(
            name = "Follow",
            joinColumns = { @JoinColumn(name = "to") },
            inverseJoinColumns = { @JoinColumn(name = "from") }
    )
    private Set<User> followedBy;

    @OneToMany(mappedBy = "user")
    private Set<Post> myPosts;

}
