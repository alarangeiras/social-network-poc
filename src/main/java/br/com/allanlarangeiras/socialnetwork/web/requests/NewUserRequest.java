package br.com.allanlarangeiras.socialnetwork.web.requests;

import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.types.Gender;
import lombok.Data;

@Data
public class NewUserRequest {

    private String name;
    private String lastName;
    private Gender gender;
    private String login;
    private String password;

    public User toEntity() {
        User user = new User();
        user.setName(this.name);
        user.setLastName(this.lastName);
        user.setGender(this.gender);
        user.setLogin(this.login);

        return user;
    }
}
