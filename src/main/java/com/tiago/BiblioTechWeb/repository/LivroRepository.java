package com.tiago.BiblioTechWeb.repository;

import com.tiago.BiblioTechWeb.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    // Buscar livro por título exato
    Optional<Livro> findByTitulo(String titulo);

    // Buscar livros por autor
    List<Livro> findByAutor(String autor);

    // Buscar livros por gênero
    List<Livro> findByGenero(String genero);

    // Buscar livros por status (Disponível, Emprestado, etc.)
    List<Livro> findByStatus(String status);

    // Exemplo de query customizada para buscar livros pelo título com like
    @Query("SELECT l FROM Livro l WHERE l.titulo LIKE %?1%")
    List<Livro> buscarPorTituloContendo(String titulo);
}
