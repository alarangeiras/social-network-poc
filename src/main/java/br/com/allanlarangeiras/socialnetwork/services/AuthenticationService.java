package br.com.allanlarangeiras.socialnetwork.services;

import br.com.allanlarangeiras.socialnetwork.components.CryptoComponent;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotAuthorizedException;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotFoundException;
import br.com.allanlarangeiras.socialnetwork.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private CryptoComponent cryptoComponent;

    @Autowired
    private UserRepository userRepository;

    public String login(String login, String password) throws NotFoundException, NotAuthorizedException {
        Optional<User> userOptional = userRepository.findByLogin(login);
        if (userOptional.isPresent()) {
            if (cryptoComponent.verifyPassword(password, userOptional.get().getEncryptedPassword())) {
                return cryptoComponent.<User>encodeJwt(userOptional);
            }
            throw new NotAuthorizedException();
        }
        throw new NotFoundException();
    }

    public User authorize(Optional<String> token) throws NotAuthorizedException {
        if (token.isPresent()) {
            return cryptoComponent.<User>decodeJwt(token);
        }
        throw new IllegalArgumentException();
    }

}
