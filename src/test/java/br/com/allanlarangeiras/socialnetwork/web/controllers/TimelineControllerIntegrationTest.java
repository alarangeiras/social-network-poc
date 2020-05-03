package br.com.allanlarangeiras.socialnetwork.web.controllers;

import br.com.allanlarangeiras.socialnetwork.components.CryptoComponent;
import br.com.allanlarangeiras.socialnetwork.dto.UserTokenDTO;
import br.com.allanlarangeiras.socialnetwork.entities.Post;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import br.com.allanlarangeiras.socialnetwork.repositories.PostRepository;
import br.com.allanlarangeiras.socialnetwork.repositories.UserRepository;
import br.com.allanlarangeiras.socialnetwork.types.AppHeaders;
import br.com.allanlarangeiras.socialnetwork.util.TestUtil;
import br.com.allanlarangeiras.socialnetwork.web.responses.TimelineResponse;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TimelineControllerIntegrationTest extends Assertions {

    public static final String LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @MockBean
    private CryptoComponent cryptoComponent;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private Gson gson;

    private TestUtil testUtil;

    @BeforeEach
    public void beforeEach() {
        testUtil = new TestUtil(LOCALHOST, port);
    }

    @Test
    public void getTimeline_returnHttp200() throws Exception {
        //mock dependencies
        Mockito.doReturn(true).when(cryptoComponent).validateJwt(Mockito.any());

        Mockito.doAnswer(invocation -> {
            UserTokenDTO userTokenDTO = new UserTokenDTO();
            userTokenDTO.setId(1L);
            return gson.toJson(userTokenDTO);
        }).when(cryptoComponent).getJwtString(Mockito.any());

        Mockito.doAnswer(invocation -> {
            User user = new User();
            return Optional.of(user);
        }).when(userRepository).findById(Mockito.anyLong());

        Mockito.doAnswer(invocation -> {
            List<Post> responseList = new ArrayList<>();
            Post post = new Post();
            post.setCreatedAt(new Date());
            post.setContent("teste");

            User user = new User();
            user.setLogin("teste");

            post.setUser(user);

            responseList.add(post);
            return responseList;
        }).when(postRepository).findOrderedPostsByFollow(Mockito.any());

        //http request
        HttpHeaders headers = new HttpHeaders();
        headers.set(AppHeaders.TOKEN.toString(), "teste");

        HttpEntity httpEntity = new HttpEntity(null, headers);

        ResponseEntity<TimelineResponse[]> responseEntity = restTemplate.exchange(testUtil.createUrlWithPort("/timeline"), HttpMethod.GET, httpEntity, TimelineResponse[].class);

        //asserts
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertTrue(responseEntity.getBody().length > 0);

        TimelineResponse timelineResponse = responseEntity.getBody()[0];
        assertTrue(timelineResponse.getAuthor() != null);
        assertTrue(timelineResponse.getContent() != null);
        assertTrue(timelineResponse.getCreatedAt() != null);

    }


}