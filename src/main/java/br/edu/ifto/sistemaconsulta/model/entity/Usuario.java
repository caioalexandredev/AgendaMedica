package br.edu.ifto.sistemaconsulta.model.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
public class Usuario implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String password;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    @ManyToMany
    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    public String getLogin() {
        return login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    }
}