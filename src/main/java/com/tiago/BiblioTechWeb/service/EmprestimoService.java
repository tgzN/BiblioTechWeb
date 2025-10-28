package com.tiago.BiblioTechWeb.service;

import com.tiago.BiblioTechWeb.model.Emprestimo;
import com.tiago.BiblioTechWeb.model.Livro;
import com.tiago.BiblioTechWeb.repository.EmprestimoRepository;
import com.tiago.BiblioTechWeb.repository.LivroRepository;
import java.util.List;
import java.util.Optional; 
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import necessário

@Service
public class EmprestimoService {
    
    private final EmprestimoRepository emprestimoRepo;
    private final LivroRepository livroRepo;

    public EmprestimoService(EmprestimoRepository emprestimoRepo, LivroRepository livroRepo) {
        this.emprestimoRepo = emprestimoRepo;
        this.livroRepo = livroRepo;
    }

    // Método que estava faltando no Controller (já estava no seu código original)
    public List<Emprestimo> listarTodos() {
        return emprestimoRepo.findAll();
    }
    
    // ------------------------------------------------------------------
    // MÉTODOS REQUERIDOS PELO CONTROLLER (Para resolver os erros)
    // ------------------------------------------------------------------

    /**
     * CORREÇÃO 1: Método salvar (Chamado por emprestimoService.salvar(emprestimo))
     * Implementa a lógica de negócio: Muda o status do Livro para 'Emprestado'.
     * 
     */
    @Transactional // Garante que as duas operações (livro e empréstimo) sejam atômicas
    public Emprestimo salvar(Emprestimo emprestimo) {
        
        // 1. Busca o livro associado
        Livro livro = emprestimo.getLivro();
        
        if (livro != null) {
            // Lógica de Novo Empréstimo: Mudar status do livro para 'Emprestado'
            livro.setStatus("Emprestado");
            livroRepo.save(livro); // Salva o livro com o novo status
        } else {
            // Lançar exceção se o livro for nulo (regra de negócio)
            throw new IllegalArgumentException("É obrigatório associar um Livro ao Empréstimo.");
        }
        
        // 2. Salva o registro de empréstimo
        return emprestimoRepo.save(emprestimo);
    }
    
    /**
     * CORREÇÃO 2: Método excluir (Chamado por emprestimoService.excluir(id))
     * Implementa a lógica de negócio: Se o livro não foi devolvido, reverte o status.
     */
    @Transactional
    public void excluir(Long id) {
        Emprestimo emprestimo = emprestimoRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado para exclusão."));

        // 1. Verifica se a devolução real ainda não foi registrada
        if (emprestimo.getDataDevolucaoReal() == null) {
            Livro livro = emprestimo.getLivro();
            if (livro != null) {
                // Se o empréstimo for excluído antes da devolução, o livro volta a ser 'Disponível'
                livro.setStatus("Disponível");
                livroRepo.save(livro);
            }
        }
        
        // 2. Deleta o registro de empréstimo
        emprestimoRepo.delete(emprestimo);
    }

    /**
     * CORREÇÃO 3: Método registrarDevolucao (Ajustado para aceitar Long id)
     * Implementa a lógica de negócio: Define dataDevolucaoReal e muda status do Livro.
     */
    @Transactional
    public void registrarDevolucao(Long id) {
        Emprestimo emprestimo = emprestimoRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado para registro de devolução."));
            
        // 1. Define a data de devolução real (se ainda não foi definida)
        if (emprestimo.getDataDevolucaoReal() == null) {
            emprestimo.setDataDevolucaoReal(LocalDate.now());
            
            // 2. Muda o status do livro
            Livro livro = emprestimo.getLivro();
            if (livro != null) {
                livro.setStatus("Disponível");
                livroRepo.save(livro); 
            }
            
            // 3. Salva o empréstimo atualizado
            emprestimoRepo.save(emprestimo);
        }
    }
    
    // ------------------------------------------------------------------
    // MÉTODOS REMOVIDOS/SIMPLIFICADOS (Para evitar redundância e confusão)
    // ------------------------------------------------------------------

    // Removido: registrarEmprestimo (Use o método 'salvar' acima)
    // Removido: salvarEmprestimo (Use o método 'salvar' acima)
    // Removido: buscarEmprestimo (Controller pode usar findById)
    // Removido: ListarEmprestimos (Use 'listarTodos')
}