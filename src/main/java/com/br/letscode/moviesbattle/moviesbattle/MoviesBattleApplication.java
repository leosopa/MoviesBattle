package com.br.letscode.moviesbattle.moviesbattle;

import com.br.letscode.moviesbattle.moviesbattle.controller.MovieController;
import com.br.letscode.moviesbattle.moviesbattle.entity.Movie;
import com.br.letscode.moviesbattle.moviesbattle.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class MoviesBattleApplication {

    @Autowired static MovieRepository movieRepository;

    public static void main(String[] args) {

        SpringApplication.run(MoviesBattleApplication.class, args);

    }

}
