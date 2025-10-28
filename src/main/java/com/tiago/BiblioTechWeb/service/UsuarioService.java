package com.tiago.BiblioTechWeb.service;

import com.tiago.BiblioTechWeb.model.Usuario;
import com.tiago.BiblioTechWeb.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepo;
    
    // 1. INJEÇÃO POR CAMPO (QUEBRA O CICLO DE DEPENDÊNCIA)
    @Autowired 
    private PasswordEncoder passwordEncoder;

    // 2. CONSTRUTOR LIMPO (SEM PasswordEncoder)
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
        // O campo 'passwordEncoder' é injetado pelo Spring após o construtor ser chamado.
    }

    // ------------------------------------------------------------------
    // OPERAÇÕES CRUD PADRONIZADAS
    // ------------------------------------------------------------------

    /**
     * Padrão: salvar (Usado para criar e atualizar)
     * Regra de Negócio: Impede a criação de usuários com e-mails duplicados e CODIFICA A SENHA.
     */
    public Usuario salvar(Usuario usuario) {
        // Se for um NOVO usuário (ID nulo), checa se o e-mail já existe
        if (usuario.getId() == null) {
            Optional<Usuario> usuarioExistente = usuarioRepo.findByEmail(usuario.getEmail());
            
            if (usuarioExistente.isPresent()) {
                throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Usuário com este e-mail já registrado."
                );
            }
            
            // REQUERIDO PELO SPRING SECURITY: CODIFICAÇÃO DA SENHA!
            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
                String senhaCodificada = passwordEncoder.encode(usuario.getSenha());
                usuario.setSenha(senhaCodificada);
            }
        } else {
            // Se for atualização:
            // A verificação de startsWith("$2a$") impede recodificar um hash já existente.
            if (usuario.getSenha() != null && !usuario.getSenha().isEmpty() && !usuario.getSenha().startsWith("$2a$")) {
                String senhaCodificada = passwordEncoder.encode(usuario.getSenha());
                usuario.setSenha(senhaCodificada);
            }
        }
        
        // Se for atualização, ou novo e-mail único, salva.
        return usuarioRepo.save(usuario);
    }

    /**
     * Padrão: buscarPorId
     */
    public Usuario buscarPorId(Long id) { 
        return usuarioRepo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com ID: " + id)
        );
    }

    /**
     * Padrão: excluir
     */
    public void excluir(Long id) { 
        if (!usuarioRepo.existsById(id)) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado para exclusão.");
        }
        // TODO: Em um sistema real, adicione aqui a checagem se o usuário tem empréstimos ativos.
        usuarioRepo.deleteById(id);
    }

    // ------------------------------------------------------------------
    // REGRAS DE NEGÓCIO ESPECÍFICAS
    // ------------------------------------------------------------------

    /**
     * MÉTODO OBSOLETO: O Spring Security usará o CustomUserDetailsService.
     */
    /*
    public Usuario autenticarUsuario(String email, String senha) {
         return null; 
    }
    */

    /**
     * Busca específica por E-mail. (MANTIDA: Necessária para o CustomUserDetailsService)
     */
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        // O Optional é ideal para serviços usados por UserDetailsService
        // pois evita lançar exceção quando o usuário não existe, retornando um Optional vazio.
        return usuarioRepo.findByEmail(email);
    }

    /**
     * Listar todos.
     */
    public List<Usuario> listarTodos() {
        return usuarioRepo.findAll();
    }
}