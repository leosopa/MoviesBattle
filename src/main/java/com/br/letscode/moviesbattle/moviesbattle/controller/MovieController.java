package com.br.letscode.moviesbattle.moviesbattle.controller;


import com.br.letscode.moviesbattle.moviesbattle.entity.Movie;
import com.br.letscode.moviesbattle.moviesbattle.entity.Quiz;
import com.br.letscode.moviesbattle.moviesbattle.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("fill")
    public void fillFirstsMovies(){

        ArrayList<String> imdbID = new ArrayList<String>();
        Resource resource = new ClassPathResource("movies_id.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(resource.getFile()))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null && i <1000) {
                Movie movie = new Movie();
                movie.setImdbId(line);
                movieRepository.save(movie);
                i++;
            }
            movieRepository.flush();
        }
        catch (Exception err){
            String msg = err.getMessage();
            msg = msg;
        }

    }

    private void fillMovie(String imdbID) {
        RestTemplate restTemplate = new RestTemplate();
        Movie movie = new Movie();
        int i;

        movie = restTemplate.getForObject("http://www.omdbapi.com/?i="+ imdbID+"&apikey=466fe701", Movie.class);
        if (!movie.getImdbRating().equals("N/A") && !movie.getImdbVotes().equals("N/A")) {
            movieRepository.save(movie);
        }
    }

    @GetMapping
    public List<Movie> get(){
        return movieRepository.findAll();
    }

}
