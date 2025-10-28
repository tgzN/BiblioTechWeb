package com.tiago.BiblioTechWeb.repository;

import com.tiago.BiblioTechWeb.model.Emprestimo;
import com.tiago.BiblioTechWeb.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    @Transactional
    void deleteByLivroId(Long livroId); // <--- Sim, você precisa criar esta assinatura.
    
    // Buscar empréstimos por CPF do leitor
    List<Emprestimo> findByCpfLeitor(String cpfLeitor);

    // Buscar empréstimos de um livro específico
    List<Emprestimo> findByLivro(Livro livro);

    // Buscar empréstimos ativos (sem devolução real)
    List<Emprestimo> findByDataDevolucaoRealIsNull();

    // Buscar empréstimos atrasados
    @Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucaoPrevista < :hoje AND e.dataDevolucaoReal IS NULL")
    List<Emprestimo> findAtrasados(@Param("hoje") LocalDate hoje);

}
