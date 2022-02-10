package com.br.letscode.moviesbattle.moviesbattle.controller;


import com.br.letscode.moviesbattle.moviesbattle.config.IMDBService;
import com.br.letscode.moviesbattle.moviesbattle.entity.Battle;
import com.br.letscode.moviesbattle.moviesbattle.entity.Movie;
import com.br.letscode.moviesbattle.moviesbattle.entity.Player;
import com.br.letscode.moviesbattle.moviesbattle.entity.Quiz;
import com.br.letscode.moviesbattle.moviesbattle.repository.BattleRepository;
import com.br.letscode.moviesbattle.moviesbattle.repository.MovieRepository;
import com.br.letscode.moviesbattle.moviesbattle.repository.PlayerRepository;
import com.br.letscode.moviesbattle.moviesbattle.repository.QuizRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping(value = "/battle")
public class BattleController {

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private MovieRepository movieRepository;


    @GetMapping
    public List<Battle> get(){
        return battleRepository.findAll();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Battle> get(@PathVariable(value = "id") Long id){

        Optional<Battle> battle = battleRepository.findById(id);

        if (battle.isPresent()){
            return new ResponseEntity<Battle>(battle.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Battle save(@Validated @RequestBody Battle battle){
        return  battleRepository.save(battle);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Battle> put (@PathVariable(value = "id") Long id, @Validated @RequestBody Battle battle){

        Optional<Battle> oldBattle = battleRepository.findById(id);

        if (oldBattle.isPresent()){
            oldBattle.get().setPlayer(battle.getPlayer());
            battleRepository.save(oldBattle.get());
            return new ResponseEntity<>(oldBattle.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id){
        Optional<Battle> battle = battleRepository.findById(id);

        if (battle.isPresent()){
            battleRepository.delete(battle.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("create/{login}")
    private Battle createBattle(@PathVariable(value = "login") String playerLogin) {

        Battle battle = new Battle();

        Optional<Player> player = playerRepository.findByLogin(playerLogin);

        if (player.isPresent()) {
            battle.setPlayer(player.get());
            battle.setOpen(true);
        }

        battleRepository.save(battle);

        return battle;

    }

    @GetMapping("/{battleid}/nextquiz")
    public ResponseEntity<Quiz> nextQuiz(@PathVariable(value = "battleid") Long battleId){
        Optional<Battle> battle = battleRepository.findById(battleId);

        if (battle.isPresent()
                && battle.get().isOpen()
                && battle.get().getQuiz() == null)
        {
            Movie firstMovie, secondMovie;
            Quiz quiz = new Quiz();
            quiz.setFirstMovie(getRandomMovie());
            quiz.setSecondMovie(getRandomMovie());

            quizRepository.save(quiz);

            if (battle.get().getQuiz() != null)
                battle.get().setPreviousQuiz(battle.get().getQuiz());

            battle.get().setQuiz(quiz);
            battleRepository.save(battle.get());

            return new ResponseEntity<>(quiz, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);

    }

    @GetMapping("answer/{battleid}/{winner}")
    public String answerQuiz(@PathVariable(value = "battleid") Long id, @PathVariable(value = "winner") int winner) {

        Optional<Battle> battle = battleRepository.findById(id);

        if (battle.isPresent()) {
            if (battle.get().isOpen() && battle.get().getQuiz() != null) {
                Battle b = battle.get();
                int winnerQuiz = b.getQuiz().whoIsBetter();
                if (winner == winnerQuiz)
                    b.setHits(b.getHits() + 1);
                else
                    b.setFails();
                b.setPreviousQuiz(b.getQuiz());
                b.setQuiz(null);
                battleRepository.save(b);
                return "Resposta recebida!";
            }
            else
                return "Esta batalha já foi encerrada!";
        }
        return "Este quiz ja foi respondido.";
    }

    @GetMapping("close/{id}")
    public ResponseEntity<Battle> closeBattle(@PathVariable(value = "id") Long id){
        Optional<Battle> battle = battleRepository.findById(id);

        if (battle.isPresent()){
            battle.get().setOpen(false);
            battleRepository.save(battle.get());
            return new ResponseEntity<Battle>(battle.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private Movie getRandomMovie() {
        RestTemplate restTemplate = new RestTemplate();
        Optional<Movie> movie;
        int i;

        while(true) {
            i  = new Random().nextInt(1000);
            movie = movieRepository.findById(i);

            Movie fullMovie = restTemplate.getForObject("http://www.omdbapi.com/?i=" + movie.get().getImdbId() + "&apikey=466fe701", Movie.class);
            fullMovie.setId(i);

            if (!fullMovie.getImdbRating().equals("N/A") && !fullMovie.getImdbVotes().equals("N/A")) {
                movieRepository.save(fullMovie);
                return fullMovie;
            }
        }
    }

}
