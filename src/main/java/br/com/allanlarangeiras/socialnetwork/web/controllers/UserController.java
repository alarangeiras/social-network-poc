package br.com.allanlarangeiras.socialnetwork.web.controllers;

import br.com.allanlarangeiras.socialnetwork.services.UserService;
import br.com.allanlarangeiras.socialnetwork.web.requests.NewUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestBody NewUserRequest newUserRequest) {
        this.userService.save(newUserRequest.toEntity(), newUserRequest.getPassword());
    }

}
