package com.tiago.BiblioTechWeb.service;

import com.tiago.BiblioTechWeb.model.Livro;
import com.tiago.BiblioTechWeb.repository.EmprestimoRepository;
import com.tiago.BiblioTechWeb.repository.LivroRepository;
import java.util.List;
import java.util.Optional; 
import java.util.stream.Collectors; // Import para filtragem
import org.springframework.stereotype.Service; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException; // Import para tratar integridade

@Service 
public class LivroService {
    
    private final LivroRepository livroRepo;
    private final EmprestimoRepository emprestimoRepo;

    @Autowired 
    public LivroService(LivroRepository livroRepo, EmprestimoRepository emprestimoRepo) {
        this.livroRepo = livroRepo;
        this.emprestimoRepo = emprestimoRepo;
    }
    
    public void deletarLivro(Long id) {
        // Encontra o livro (para ter certeza que ele existe antes de deletar)
        if (livroRepo.existsById(id)) {
            
            // 1. CRÍTICO: Deleta todos os filhos (Empréstimos)
            emprestimoRepo.deleteByLivroId(id); 

            // 2. Deleta o pai (Livro)
            livroRepo.deleteById(id);
        }
    }

    // ------------------------------------------------------------------
    // MÉTODOS REQUERIDOS PELO LivroController (Padronização)
    // ------------------------------------------------------------------

    /**
     * CORREÇÃO 1: Método 'salvar' (usado para criação e atualização no Controller)
     * Renomeia de salvarLivro para 'salvar'.
     */
    public Livro salvar(Livro livro) {
        return livroRepo.save(livro);
    }

    /**
     * CORREÇÃO 2: Método 'buscarPorId' (usado pelo Controller para Edição)
     * Renomeia de buscarLivroPorId para 'buscarPorId'.
     */
    public Livro buscarPorId(Long id) {
        return livroRepo.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com ID: " + id)
        );
    }
    
    /**
     * CORREÇÃO 3: Método 'excluir' (usado pelo Controller)
     * Implementa lógica para verificar empréstimos antes de deletar.
     */
    public void excluir(Long id) {
        if (!livroRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado para exclusão.");
        }
        
        try {
            emprestimoRepo.deleteByLivroId(id); // Você precisa criar este método no EmprestimoRepository
            // A regra de negócio para verificar se o livro está emprestado 
            // deve idealmente estar no EmprestimoService ou usando uma query específica,
            // mas o Repository lança DataIntegrityViolationException se houver FK.
            livroRepo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            // Captura o erro se o livro estiver associado a um empréstimo (Foreign Key)
            throw new ResponseStatusException(HttpStatus.CONFLICT, 
                "Não é possível excluir o Livro pois ele está associado a um Empréstimo ativo ou histórico."
            );
        }
    }
    
    // ------------------------------------------------------------------
    // MÉTODOS REQUERIDOS PELO EmprestimoController (Lógica de Negócio)
    // ------------------------------------------------------------------
    
    /**
     * CORREÇÃO 4: Método 'listarLivrosDisponiveis' (Requerido pelo EmprestimoController)
     * Retorna apenas os livros com status "Disponível".
     */
    public List<Livro> listarLivrosDisponiveis() {
        return livroRepo.findAll().stream()
                .filter(livro -> "Disponível".equalsIgnoreCase(livro.getStatus()))
                .collect(Collectors.toList());
    }
    
    // ------------------------------------------------------------------
    // MÉTODOS GERAIS (Mantidos/Simplificados)
    // ------------------------------------------------------------------

    public List<Livro> listarTodos() {
        return livroRepo.findAll();
    }
    
    public Livro buscarLivroPorTitulo(String titulo) {
        // Se este método estiver no seu LivroRepository, ele funcionará.
        Optional<Livro> livroOpt = livroRepo.findByTitulo(titulo); 
        return livroOpt.orElseThrow(
             () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado com título: " + titulo)
        );
    }

    // Removido: atualizarLivro (Use o método 'salvar' que já faz isso)
}