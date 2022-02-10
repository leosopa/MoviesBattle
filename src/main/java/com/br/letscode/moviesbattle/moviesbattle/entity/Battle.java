package com.br.letscode.moviesbattle.moviesbattle.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.EAGER;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
public class Battle {

    private @Id @GeneratedValue Long id;
    @ManyToOne
    //@JsonBackReference
    @JoinColumn(name = "player_id")
    private Player player;
    @ManyToOne
    @JoinColumn(name = "previous_quiz_id")
    private Quiz previousQuiz;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    private boolean isOpen;
    private int hits;
    private int fails;


    public Battle(Long id, Player player, List<Quiz> quizzes) {
        this.id = id;
        this.player = player;
    }

    public Battle() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Battle{" +
                "id=" + id +
                //", player=" + player +
                //", quizzes=" + quizzes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Battle)) return false;
        Battle battle = (Battle) o;
        return getId().equals(battle.getId()) && getPlayer().equals(battle.getPlayer()) && Objects.equals(getPreviousQuiz(), battle.getPreviousQuiz());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayer(), getPreviousQuiz());
    }

    public int getFails() {
        return fails;
    }

    public void setFails() {
        if (this.fails < 3)
            this.fails++;
        else
            this.isOpen = false;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }


    public Quiz getPreviousQuiz() {
        return previousQuiz;
    }

    public void setPreviousQuiz(Quiz previousQuiz) {
        this.previousQuiz = previousQuiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
