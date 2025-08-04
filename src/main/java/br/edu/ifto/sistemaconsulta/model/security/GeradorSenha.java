package br.edu.ifto.sistemaconsulta.model.security;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author fagno
 */
public class GeradorSenha {

    public static void main(String[] args) {
        //solicitando o encode para 123
        System.out.println(new BCryptPasswordEncoder().encode("123"));
    }

}