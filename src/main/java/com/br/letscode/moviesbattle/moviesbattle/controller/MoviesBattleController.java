package com.br.letscode.moviesbattle.moviesbattle.controller;

import com.br.letscode.moviesbattle.moviesbattle.entity.Player;
import com.br.letscode.moviesbattle.moviesbattle.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller()
@RequestMapping(value = "/home")
public class MoviesBattleController {

    @Autowired
    PlayerRepository repository;

    @GetMapping("")
    public String viewHomePage(Model model) {
        return "index";
    }

    @GetMapping("/register")
    public String createPlyer(Model model) {
        model.addAttribute("player", new Player());
        return "create_player";
    }

    @PostMapping("/process_register")
    public String processRegister(Player player) {
       new PlayerController().save(player);
       return "register_success";
    }

}
