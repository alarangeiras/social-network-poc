package br.com.allanlarangeiras.socialnetwork.repositories;

import br.com.allanlarangeiras.socialnetwork.entities.Post;
import br.com.allanlarangeiras.socialnetwork.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("from Post where user = :user order by createdAt desc")
    public List<Post> findOrderedPostsByUser(User user);

    @Query("from Post where :user member of user.followedBy order by createdAt desc")
    public List<Post> findOrderedPostsByFollow(User user);

}
