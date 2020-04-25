package br.com.allanlarangeiras.socialnetwork.web.responses;

import br.com.allanlarangeiras.socialnetwork.entities.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TimelineResponse {

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    private String author;
    private String content;

    public static List<TimelineResponse> fromEntityList(List<Post> orderedPostsByFollow) {
        return orderedPostsByFollow
                .stream()
                .map(post -> {
                    TimelineResponse timelineResponse = new TimelineResponse();
                    timelineResponse.setAuthor(post.getUser().getLogin());
                    timelineResponse.setCreatedAt(post.getCreatedAt());
                    timelineResponse.setContent(post.getContent());
                    return timelineResponse;
                })
                .collect(Collectors.toList());
    }
}
