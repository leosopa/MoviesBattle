package com.br.letscode.moviesbattle.moviesbattle.controller;

import com.br.letscode.moviesbattle.moviesbattle.entity.Quiz;
import com.br.letscode.moviesbattle.moviesbattle.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired private QuizRepository quizRepository;

    @GetMapping
    public List<Quiz> get(){
        return quizRepository.findAll();
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Quiz> get(@PathVariable(value = "id") Long id){

        Optional<Quiz> quiz = quizRepository.findById(id);

        if (quiz.isPresent()){
            return new ResponseEntity<Quiz>(quiz.get(), HttpStatus.OK);
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Quiz save(@Validated @RequestBody Quiz quiz){
        return quizRepository.save(quiz);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Quiz> save(@PathVariable(value = "id") Long id, @Validated @RequestBody Quiz quiz){

        Optional<Quiz> oldQuizz = quizRepository.findById(id);

        if (oldQuizz.isPresent()){

            oldQuizz.get().setFirstMovie(quiz.getFirstMovie());
            oldQuizz.get().setSecondMovie(quiz.getSecondMovie());
            quizRepository.save(oldQuizz.get());
            return new ResponseEntity<Quiz>(oldQuizz.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable(value = "id") Long id){

        Optional<Quiz> quiz = quizRepository.findById(id);

        if (quiz.isPresent()){
            quizRepository.delete(quiz.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
