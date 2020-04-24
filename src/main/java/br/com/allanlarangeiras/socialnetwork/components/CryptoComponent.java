package br.com.allanlarangeiras.socialnetwork.components;

import br.com.allanlarangeiras.socialnetwork.exceptions.NotAuthorizedException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Component
public class CryptoComponent {

    @Autowired
    private Gson gson;

    @Value("socialnetworkpoc.salt")
    private String secret;

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    public <T> String encodeJwt(Optional<T> objectOptional) {
        return objectOptional.map(object -> {
            return Jwts.builder()
                    .setSubject(gson.toJson(object))
                    .signWith(SignatureAlgorithm.HS512, this.secret)
                    .compact();
        }).orElseThrow(IllegalArgumentException::new);

    }

    public <T> T decodeJwt(Optional<String> tokenOptional) throws NotAuthorizedException {
        if (tokenOptional.isPresent()) {
            String json = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(tokenOptional.get())
                    .getBody()
                    .getSubject();

            try {
                T parsedObject = gson.<T>fromJson(json, new ArrayList<T>().getClass().getComponentType());
                return parsedObject;
            } catch (JsonSyntaxException jsonSyntaxException) {
                throw new NotAuthorizedException();
            }
        } else {
            throw new IllegalArgumentException();
        }

    }

    public String encryptPassword(String plainPassword) {
        String returnValue = null;
        byte[] securePassword = hash(plainPassword.toCharArray(), secret.getBytes());
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
            SecretKeyFactory skf = SecretKeyFactory.getInstance(secret);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

}
