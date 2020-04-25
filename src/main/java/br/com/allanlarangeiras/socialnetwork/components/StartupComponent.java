package br.com.allanlarangeiras.socialnetwork.components;

import br.com.allanlarangeiras.socialnetwork.entities.Post;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.repositories.PostRepository;
import br.com.allanlarangeiras.socialnetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Calendar;

@Component
public class StartupComponent {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CryptoComponent cryptoComponent;

    @PostConstruct
    private void init() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setEncryptedPassword(cryptoComponent.encryptPassword("1234"));

        User user2 = new User();
        user2.setLogin("user2");
        user2.setEncryptedPassword(cryptoComponent.encryptPassword("4321"));

        User user3 = new User();
        user3.setLogin("user3");
        user3.setEncryptedPassword(cryptoComponent.encryptPassword("5678"));

        User user4 = new User();
        user4.setLogin("user4");
        user4.setEncryptedPassword(cryptoComponent.encryptPassword("8765"));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        user1.getFollowing().add(user2);
        user1.getFollowing().add(user3);
        user1.getFollowing().add(user4);

        user2.getFollowing().add(user3);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);

        Calendar calendar = Calendar.getInstance();

        calendar.set(2020, 01,01);
        Post post1 = new Post();
        post1.setUser(user1);
        post1.setCreatedAt(calendar.getTime());
        post1.setContent("hello, world");

        calendar.set(2020, 02,14);
        Post post2 = new Post();
        post2.setUser(user2);
        post2.setCreatedAt(calendar.getTime());
        post2.setContent("new post");

        calendar.set(2020, 02,14);
        Post post3 = new Post();
        post3.setUser(user2);
        post3.setCreatedAt(calendar.getTime());
        post3.setContent("new post");

        calendar.set(2020, 02,14);
        Post post4 = new Post();
        post4.setUser(user2);
        post4.setCreatedAt(calendar.getTime());
        post4.setContent("new post");

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);

    }
}
