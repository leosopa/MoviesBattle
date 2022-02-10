/*
package com.br.letscode.moviesbattle.moviesbattle.config;

import com.br.letscode.moviesbattle.moviesbattle.entity.Player;
import com.br.letscode.moviesbattle.moviesbattle.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class PlayerDetailService implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Player> player = playerRepository.findByLogin(username);
        if (!player.isPresent()) {
            throw new UsernameNotFoundException("Player not found");
        }

        player.get().setLogged(true);
        playerRepository.save(player.get());

        return player.get();
    }

}
*/
