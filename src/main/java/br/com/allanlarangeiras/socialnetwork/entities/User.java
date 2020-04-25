package br.com.allanlarangeiras.socialnetwork.entities;

import br.com.allanlarangeiras.socialnetwork.types.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "Follow",
        joinColumns = { @JoinColumn(name = "from_user") },
        inverseJoinColumns = { @JoinColumn(name = "to_user") }
    )
    private Set<User> following = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Follow",
            joinColumns = { @JoinColumn(name = "to_user") },
            inverseJoinColumns = { @JoinColumn(name = "from_user") }
    )
    private Set<User> followedBy = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Post> myPosts = new HashSet<>();

}
