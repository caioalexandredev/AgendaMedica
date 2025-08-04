package br.edu.ifto.sistemaconsulta.model.security;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import br.edu.ifto.sistemaconsulta.model.entity.Usuario;
import br.edu.ifto.sistemaconsulta.model.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author fagno
 */
@Transactional
@Repository
public class UsuarioDetailsConfig implements UserDetailsService{

    @Autowired
    UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = repository.usuario(login);

        if(usuario == null){
            throw new UsernameNotFoundException("usuário não encontrado!");
        }

        return new User(usuario.getLogin(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
    }

}