package com.br.letscode.moviesbattle.moviesbattle.repository;

import com.br.letscode.moviesbattle.moviesbattle.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleRepository extends JpaRepository<Battle, Long> {
}