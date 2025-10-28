package com.tiago.BiblioTechWeb.service;

import com.tiago.BiblioTechWeb.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy; // <-- NOVA IMPORTAÇÃO

import java.util.Collections;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Adicionamos @Lazy aqui. O Spring só inicializará o UsuarioService
    // quando ele for *realmente* necessário (ou seja, quando loadUserByUsername for chamado), 
    // permitindo que o SecurityConfig (que contém o PasswordEncoder) seja criado primeiro.
    @Lazy // <-- CHAVE PARA QUEBRAR O CICLO!
    @Autowired 
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioService.buscarUsuarioPorEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email));

        return new org.springframework.security.core.userdetails.User(
                usuario.getEmail(),
                usuario.getSenha(),
                Collections.emptyList()
        );
    }
}