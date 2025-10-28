package com.tiago.BiblioTechWeb.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat; // <<--- NOVO IMPORT

@Entity
@Table(name = "emprestimo")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @Column(nullable = false)
    private String cpfLeitor;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Garante que o formato HTML é aceito
    private LocalDate dataEmprestimo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDevolucaoPrevista;

    private LocalDate dataDevolucaoReal;

    public Emprestimo() {
        // Construtor vazio exigido pelo JPA
    }

    public Emprestimo(Livro livro, String cpfLeitor, LocalDate dataEmprestimo,
                      LocalDate dataDevolucaoPrevista, LocalDate dataDevolucaoReal) {
        this.livro = livro;
        this.cpfLeitor = cpfLeitor;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public Long getId() {
        return id;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public String getCpfLeitor() {
        return cpfLeitor;
    }

    public void setCpfLeitor(String cpfLeitor) {
        this.cpfLeitor = cpfLeitor;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }
}
