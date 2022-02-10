package com.br.letscode.moviesbattle.moviesbattle.entity;

import com.sun.istack.NotNull;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Entity
public class Player {

    private @GeneratedValue Long id;
    private @NotNull String name;
    private @Id @NotNull String login;
    private @NotNull String password;
    private boolean isLogged;
    @OneToMany(targetEntity = Battle.class, mappedBy = "player", fetch = FetchType.EAGER)
    private List<Battle> battles;

    public Player(@NotNull String login, @NotNull String name, @NotNull String password, List<Battle> battles) {
        this.login = login;
        this.name = name;
        this.password = Integer.toString(password.hashCode());
        this.battles = battles;
    }

    public Player() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Battle> getBattles() {
        return battles;
    }
    
    public void setBattles(List<Battle> battles) {
        this.battles = battles;
    }

/*    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    } */

    public String getPassword() {
        return password;
    }

/*    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    } */

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", battles=" + battles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getId().equals(player.getId()) && getName().equals(player.getName()) && getPassword() == (player.getPassword()) && Objects.equals(getBattles(), player.getBattles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPassword(), getBattles());
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }
}

