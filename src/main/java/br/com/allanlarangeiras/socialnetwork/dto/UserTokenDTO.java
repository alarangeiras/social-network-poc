package br.com.allanlarangeiras.socialnetwork.dto;

import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.types.Gender;
import lombok.Data;

import java.util.Optional;

@Data
public class UserTokenDTO {

    private Long id;
    private String name;
    private String lastName;
    private String login;
    private Gender gender;

    public static Optional<UserTokenDTO> fromEntity(User user) {
        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setId(user.getId());
        userTokenDTO.setName(user.getName());
        userTokenDTO.setLastName(user.getLastName());
        userTokenDTO.setLogin(user.getLogin());
        userTokenDTO.setGender(user.getGender());

        return Optional.of(userTokenDTO);
    }
}
