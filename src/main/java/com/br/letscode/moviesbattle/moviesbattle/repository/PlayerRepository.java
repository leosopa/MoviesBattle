package com.br.letscode.moviesbattle.moviesbattle.repository;

import com.br.letscode.moviesbattle.moviesbattle.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT p FROM Player p WHERE p.login = ?1")
    Optional<Player> findByLogin(String login);

}