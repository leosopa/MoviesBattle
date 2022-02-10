package com.br.letscode.moviesbattle.moviesbattle.repository;

import com.br.letscode.moviesbattle.moviesbattle.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
}