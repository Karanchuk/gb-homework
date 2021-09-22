package ru.gb.springSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.springSecurity.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User u set u.score = u.score + 1 where u.username = :name")
    void incrementScore(String name);

    @Modifying
    @Transactional
    @Query("UPDATE User u set u.score = u.score - 1 where u.score > 0 and u.username = :name")
    void decrementScore(String name);
}
