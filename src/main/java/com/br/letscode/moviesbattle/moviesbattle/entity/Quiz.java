package com.br.letscode.moviesbattle.moviesbattle.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Quiz {

    private @Id @GeneratedValue Long id;
    @ManyToOne
    @JoinColumn(name = "first_movie_imdb_id")
    private Movie firstMovie;
    @ManyToOne
    @JoinColumn(name = "second_movie_imdb_id")
    private Movie secondMovie;

    public Quiz(Long id, Battle battle, Movie firstMovie, Movie secondMovie) {
        this.id = id;
        this.firstMovie = firstMovie;
        this.secondMovie = secondMovie;
    }

    public Quiz() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getFirstMovie() {
        return firstMovie;
    }

    public void setFirstMovie(Movie firstMovie) {
        this.firstMovie = firstMovie;
    }

    public Movie getSecondMovie() {
        return secondMovie;
    }

    public void setSecondMovie(Movie secondMovie) {
        this.secondMovie = secondMovie;
    }

    @Override
    public String toString() {
        return "QuizController{" +
                "id=" + id +
                ", firstMovie=" + firstMovie +
                ", secondMovie=" + secondMovie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quiz)) return false;
        Quiz quiz = (Quiz) o;
        return getId().equals(quiz.getId()) && getFirstMovie().equals(quiz.getFirstMovie()) && getSecondMovie().equals(quiz.getSecondMovie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstMovie(), getSecondMovie());
    }

    public int whoIsBetter() {

        float rateFirstMovie = Float.parseFloat(this.firstMovie.getImdbRating());
        int votesFirstMovie = Integer.parseInt(this.firstMovie.getImdbVotes().replace("," , ""));

        float rateSecondMovie = Float.parseFloat(this.secondMovie.getImdbRating());
        int votesSecondMovie = Integer.parseInt(this.secondMovie.getImdbVotes().replace("," , ""));

        float totalFirst = rateFirstMovie * votesFirstMovie;

        float totalSecond = rateSecondMovie * votesSecondMovie;

        if (totalFirst > totalSecond)
            return 1;
        else
            return 2;

    }
}
