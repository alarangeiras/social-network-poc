package br.com.allanlarangeiras.socialnetwork.web.controllers;

import br.com.allanlarangeiras.socialnetwork.web.requests.AuthRequest;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotAuthorizedException;
import br.com.allanlarangeiras.socialnetwork.exceptions.NotFoundException;
import br.com.allanlarangeiras.socialnetwork.services.AuthenticationService;
import br.com.allanlarangeiras.socialnetwork.types.AppHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody @Valid AuthRequest authRequest) throws NotFoundException, NotAuthorizedException {
        String token = authenticationService.login(authRequest.getLogin(), authRequest.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.set(AppHeaders.TOKEN.toString(), token);

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }


}
