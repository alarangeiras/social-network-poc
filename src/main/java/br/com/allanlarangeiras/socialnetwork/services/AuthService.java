package br.com.allanlarangeiras.socialnetwork.services;

import br.com.allanlarangeiras.socialnetwork.components.CryptoComponent;
import br.com.allanlarangeiras.socialnetwork.dto.UserTokenDTO;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotAuthorizedException;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotFoundException;
import br.com.allanlarangeiras.socialnetwork.repositories.UserRepository;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private CryptoComponent cryptoComponent;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Gson gson;

    public String login(String login, String password) throws NotFoundException, NotAuthorizedException {
        Optional<User> userOptional = userRepository.findByLogin(login);
        if (userOptional.isPresent()) {
            if (cryptoComponent.verifyPassword(password, userOptional.get().getEncryptedPassword())) {
                return cryptoComponent.<UserTokenDTO>encodeJwt(UserTokenDTO.fromEntity(userOptional.get()));
            }
            throw new NotAuthorizedException();
        }
        throw new NotFoundException();
    }

    public void authorize(Optional<String> token) throws NotAuthorizedException {
        if (!token.isPresent()) {
        	log.info("token não informado");
            throw new IllegalArgumentException();
        }

        try {
        	if (!cryptoComponent.validateJwt(token)) {
        		throw new NotAuthorizedException();
        	}
        } catch (Exception ex) {
    		log.info("o token {} esta invalido", token);
    		throw new NotAuthorizedException();
        }
    }

    public UserTokenDTO getDTOByToken(String token) {
        String jsonString = this.cryptoComponent.getJwtString(Optional.ofNullable(token));
        return gson.fromJson(jsonString, UserTokenDTO.class);
    }
}
