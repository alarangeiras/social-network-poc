package br.com.allanlarangeiras.socialnetwork.services;

import br.com.allanlarangeiras.socialnetwork.components.CryptoComponent;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotAuthorizedException;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotFoundException;
import br.com.allanlarangeiras.socialnetwork.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthServiceUnitTest extends Assertions {

    @Mock
    private CryptoComponent cryptoComponent;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testLoginSuccess() {
        Mockito.when(userRepository.findByLogin(Mockito.anyString())).thenAnswer(invocation -> {
            User user = new User();
            user.setEncryptedPassword("");
            return Optional.of(user);
        });
        Mockito.when(cryptoComponent.verifyPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        Mockito.when(cryptoComponent.encodeJwt(Mockito.any())).thenReturn("token-jwt");

        try {
            String token = authService.login("test", "test123");
            assertNotNull(token);
            assertTrue(token.length() > 0);
        } catch (NotFoundException e) {
            fail();
        } catch (NotAuthorizedException e) {
            fail();
        }
    }

    @Test
    public void testLoginNotAuthorizedException() {
        Mockito.when(userRepository.findByLogin(Mockito.anyString())).thenAnswer(invocation -> {
            User user = new User();
            user.setEncryptedPassword("");
            return Optional.of(user);
        });
        Mockito.when(cryptoComponent.verifyPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(false);

        try {
            authService.login("test", "test123");
            fail();
        } catch (Exception e) {
          assertTrue(e instanceof NotAuthorizedException);
        }
    }

    @Test
    public void testLoginNotFoundException() {
        Mockito.when(userRepository.findByLogin(Mockito.anyString())).thenReturn(Optional.ofNullable(null));

        try {
            authService.login("test", "test123");
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof NotFoundException);
        }
    }

}
