package br.com.allanlarangeiras.socialnetwork.repositories;

import br.com.allanlarangeiras.socialnetwork.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("from User where login = :login")
    public Optional<User> findByLogin(String login);

}
