package br.com.allanlarangeiras.socialnetwork.services;

import br.com.allanlarangeiras.socialnetwork.exceptions.NotFoundException;
import br.com.allanlarangeiras.socialnetwork.entities.Post;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getTimelineByUser(Optional<User> userOptional) throws NotFoundException {
        return userOptional
            .map(user -> this.postRepository.findOrderedPostsByFollow(user))
            .orElseThrow(NotFoundException::new);

    }

}
