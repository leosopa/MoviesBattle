package com.br.letscode.moviesbattle.moviesbattle.controller;

import com.br.letscode.moviesbattle.moviesbattle.entity.Player;
import com.br.letscode.moviesbattle.moviesbattle.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/player")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping("index")
    public String index(){
        return "index";
    }


    @GetMapping()
    public List<Player> get(){
        return playerRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Player> getById(@PathVariable(value = "id") long id){

        Optional<Player> player = playerRepository.findById(id);

        if (player.isPresent())
            return new ResponseEntity<Player>(player.get(), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public Player save(@Validated Player player){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(player.getPassword());
        player.setPassword(encodedPassword);

        return playerRepository.save(player);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Player> put (@PathVariable(value = "id") Long id, @Validated @RequestBody Player newPlayer){

        Optional<Player> oldPlayer = playerRepository.findById(id);

        if (oldPlayer.isPresent()){

            oldPlayer.get().setName(newPlayer.getName());
            oldPlayer.get().setPassword(newPlayer.getPassword());
            oldPlayer.get().setBattles(newPlayer.getBattles());

            playerRepository.save(oldPlayer.get());

            return new ResponseEntity<Player>(oldPlayer.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id){
        Optional<Player> player = playerRepository.findById(id);

        if (player.isPresent()){
            playerRepository.delete(player.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Player> login (@Validated @RequestBody Player player){

        Optional<Player> oldPlayer = playerRepository.findByLogin(player.getLogin());

            if (oldPlayer.isPresent()) {
                if (oldPlayer.get().getPassword().equals(player.getPassword())){
                    player.setLogged(true);
                    playerRepository.save(player);
                    return new ResponseEntity<Player>(player, HttpStatus.OK);
                }

            }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Player> logout (@Validated @RequestBody Player player){

        List<Player> players = playerRepository.findAll();

        for (Player p : players) {

            if (p.equals(player)) {
                player.setLogged(false);
                playerRepository.save(player);
                return new ResponseEntity<Player>(player, HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    }
