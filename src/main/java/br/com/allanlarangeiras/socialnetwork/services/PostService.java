package br.com.allanlarangeiras.socialnetwork.services;

import br.com.allanlarangeiras.socialnetwork.dto.UserTokenDTO;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.repositories.PostRepository;
import br.com.allanlarangeiras.socialnetwork.repositories.UserRepository;
import br.com.allanlarangeiras.socialnetwork.web.responses.TimelineResponse;
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
    private AuthService authService;

    public List<TimelineResponse> getTimelineByToken(String token) {
        UserTokenDTO userTokenDTO = authService.getDTOByToken(token);
        Optional<User> user = userRepository.findById(userTokenDTO.getId());
        return TimelineResponse.fromEntityList(postRepository.findOrderedPostsByFollow(user.get()));
    }
}
