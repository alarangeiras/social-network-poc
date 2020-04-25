package br.com.allanlarangeiras.socialnetwork.components;

import br.com.allanlarangeiras.socialnetwork.configuration.DefaultConfigurationProperties;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotAuthorizedException;
import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Component
public class CryptoComponent {

    @Autowired
    private Gson gson;

    @Autowired
    private DefaultConfigurationProperties properties;

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public <T> String encodeJwt(Optional<T> objectOptional) {
        return objectOptional.map(object -> {
            return Jwts.builder()
                    .setSubject(gson.toJson(object))
                    .signWith(SignatureAlgorithm.HS512, properties.getSalt())
                    .compact();
        }).orElseThrow(IllegalArgumentException::new);

    }

    public boolean validateJwt(Optional<String> tokenOptional) throws NotAuthorizedException {
        if (tokenOptional.isPresent()) {
            try {
                this.getJwtString(tokenOptional);
                return true;
            } catch (Exception exception) {
                return false;
            }
        } else {
            return false;
        }

    }

    public String getJwtString(Optional<String> tokenOptional) {
        return Jwts.parser()
                .setSigningKey(properties.getSalt())
                .parseClaimsJws(tokenOptional.get())
                .getBody()
                .getSubject();
    }

    public String encryptPassword(String plainPassword) {
        String returnValue = null;
        byte[] securePassword = hash(plainPassword.toCharArray(), properties.getSalt().getBytes());
        returnValue = Base64.getEncoder().encodeToString(securePassword);
        return returnValue;
    }

    public boolean verifyPassword(String providedPassword, String securedPassword) {
        String newSecurePassword = encryptPassword(providedPassword);
        return newSecurePassword.equalsIgnoreCase(securedPassword);
    }

    private byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

}
