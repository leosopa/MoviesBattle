package com.br.letscode.moviesbattle.moviesbattle.repository;

import com.br.letscode.moviesbattle.moviesbattle.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}