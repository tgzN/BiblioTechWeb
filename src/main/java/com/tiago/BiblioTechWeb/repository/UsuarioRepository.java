package com.tiago.BiblioTechWeb.repository;

import com.tiago.BiblioTechWeb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuário por e-mail
    Optional<Usuario> findByEmail(String email);

    // Buscar usuários por cargo
    List<Usuario> findByCargo(String cargo);

    // Buscar usuários por permissões
    List<Usuario> findByPermissoes(String permissoes);

    // Autenticação simples (email + senha)
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}
