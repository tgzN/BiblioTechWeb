package com.tiago.BiblioTechWeb.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // CORRIGIDO: Tipo primitivo para wrapper Long
    
    private String nome;
    private String email;
    private String senha;
    private String cargo;
    private String permissoes;

    // Construtor vazio para JPA
    public Usuario() {} 
    
    // Construtores ajustados para Long
    public Usuario(Long id, String nome, String email, String senha, String cargo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
    }
    
    public Usuario(Long id, String nome, String email, String senha, String cargo, String permissoes) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.permissoes = permissoes;
    }

    public Usuario(String nome, String email, String senha, String cargo, String permissoes) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cargo = cargo;
        this.permissoes = permissoes;
    }

    // Getters e Setters ajustados para Long
    public Long getId() {
        return id; // CORRIGIDO: Retorna Long
    }


    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getCargo() {
        return cargo;
    }

    public String getPermissoes() {
        return permissoes;
    }

    public void setId(Long id) {
        this.id = id; // CORRIGIDO: Aceita Long
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setPermissoes(String permissoes) {
        this.permissoes = permissoes;
    }

    public String getPerfil() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
