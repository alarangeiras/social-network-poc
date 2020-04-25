package br.com.allanlarangeiras.socialnetwork.services;

import br.com.allanlarangeiras.socialnetwork.dto.UserTokenDTO;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotFoundException;
import br.com.allanlarangeiras.socialnetwork.entities.Post;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.repositories.PostRepository;
import br.com.allanlarangeiras.socialnetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public List<Post> getTimelineByToken(String token) {
        UserTokenDTO userTokenDTO = authenticationService.getDTOByToken(token);
        Optional<User> user = userRepository.findById(userTokenDTO.getId());
        return postRepository.findOrderedPostsByFollow(user.get());
    }
}
