package br.com.allanlarangeiras.socialnetwork.web.controllers;

import br.com.allanlarangeiras.socialnetwork.annotations.Secure;
import br.com.allanlarangeiras.socialnetwork.services.PostService;
import br.com.allanlarangeiras.socialnetwork.types.AppHeaders;
import br.com.allanlarangeiras.socialnetwork.web.responses.TimelineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/timeline")
public class TimelineController {

    @Autowired
    private PostService postService;

    private static final String TOKEN = AppHeaders.TOKEN.toString();

    @Secure
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimelineResponse> get(@RequestHeader(name = "TOKEN", required = true) String token) {
        return this.postService.getTimelineByToken(token);
    }

}
