package br.com.allanlarangeiras.socialnetwork.services;

import br.com.allanlarangeiras.socialnetwork.exceptions.InnactiveException;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotFoundException;
import br.com.allanlarangeiras.socialnetwork.entities.Post;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Set<Post> findAllPostsById(Long id) throws InnactiveException, NotFoundException {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            if (userOptional.get().getActive() == true) {
                return userOptional.get().getMyPosts();
            } else {
                throw new InnactiveException();
            }
        } else {
            throw new NotFoundException();
        }
    }

}
