package com.br.letscode.moviesbattle.moviesbattle.config;

import com.br.letscode.moviesbattle.moviesbattle.entity.Movie;
import com.br.letscode.moviesbattle.moviesbattle.entity.Quiz;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ThreadLocalRandom;

public class IMDBService {

    public static Movie getMovie() {

        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie();
        int i;

        while (!movie.getType().equals("movie") || movie.getImdbRating().equals("N/A") || movie.getImdbVotes().equals("N/A")) {
            i = ThreadLocalRandom.current().nextInt(1000000, 9916000);
            movie = restTemplate.getForObject("http://www.omdbapi.com/?i=tt" + i + "&apikey=466fe701", Movie.class);
        }

        return movie;

    }

}
