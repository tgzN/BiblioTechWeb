package com.tiago.BiblioTechWeb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import com.tiago.BiblioTechWeb.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Configura as regras de Autorização (Quem pode acessar o quê)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/", 
                    "/login", 
                    "/usuarios/novo", // <--- CORREÇÃO CRÍTICA: Rota de cadastro deve ser pública
                    "/css/**", 
                    "/js/**", 
                    "/images/**", 
                    "/teste/criar-usuario"
                ).permitAll() // <-- Estas rotas estão liberadas
                .anyRequest().authenticated() // Todas as outras rotas exigem autenticação
            )
            // 2. Configura a Autenticação por Formulário
            .formLogin(form -> form
                .loginPage("/login") 
                .usernameParameter("email") // Se você usa "email" no HTML, defina aqui. Se usa "username", pode remover esta linha.
                .passwordParameter("senha") // Se você usa "senha" no HTML, defina aqui. Se usa "password", pode remover esta linha.
                .defaultSuccessUrl("/livros", true) // Redireciona para /livros após sucesso
                .failureUrl("/login?error") // Retorna para /login com o parâmetro 'error'
                .permitAll()
            )
            // 3. Configura o Logout
            .logout(logout -> logout
                .logoutUrl("/logout") // O endpoint que dispara o logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        
        return http.build();
    }
    
    // ESSENCIAL: O Bean do PasswordEncoder para o Spring Security validar senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}