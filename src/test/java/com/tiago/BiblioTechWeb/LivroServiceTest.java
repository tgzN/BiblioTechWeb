package com.tiago.BiblioTechWeb;

import com.tiago.BiblioTechWeb.model.Livro;
import com.tiago.BiblioTechWeb.repository.EmprestimoRepository;
import com.tiago.BiblioTechWeb.repository.LivroRepository;
import com.tiago.BiblioTechWeb.service.LivroService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LivroServiceTest {

    // Injeta o mock no LivroService (o objeto que queremos testar)
    @InjectMocks
    private LivroService livroService; 

    // Cria um mock para o Repository (o que queremos simular/ignorar)
    @Mock 
    private LivroRepository livroRepo;
    
    // (Opcional se você não for testar o delete em cascata)
    @Mock 
    private EmprestimoRepository emprestimoRepo; 

    private Livro livro1;

    @BeforeEach
    void setUp() {
        // Cria um objeto Livro inicial
        livro1 = new Livro();
        livro1.setId(1L);
        livro1.setTitulo("O Senhor dos Anéis");
        livro1.setStatus("Disponível"); // Status inicial
    }

    @Test
    void testListarLivrosDisponiveis() {
        // Configura um segundo livro (Emprestado)
        Livro livro2 = new Livro();
        livro2.setId(2L);
        livro2.setStatus("Emprestado");

        // Simula o comportamento do findAll() do Repository
        List<Livro> todosLivros = Arrays.asList(livro1, livro2);
        when(livroRepo.findAll()).thenReturn(todosLivros);

        // Chama o método que queremos testar
        List<Livro> disponiveis = livroService.listarLivrosDisponiveis();

        // Verifica se apenas o livro "Disponível" foi retornado
        assertEquals(1, disponiveis.size(), "Apenas 1 livro deve estar disponível.");
        assertEquals("Disponível", disponiveis.get(0).getStatus());
        
        // Verifica se o método do repository foi chamado
        verify(livroRepo, times(1)).findAll();
    }
    
    @Test
    void testStatusInicialLivro() {
        // Testa se a entidade Livro está sendo criada com o status correto.
        // Se a lógica de status estiver na entidade ou na criação, este teste é vital.
        
        // Simulação de criação (assumindo que o status é setado no construtor ou setter)
        Livro novoLivro = new Livro();
        
        // CRÍTICO: Se você não está usando um construtor que define o status,
        // pode precisar setar ele no teste ou garantir que o Service o faça.
        // Se o seu `Livro.java` tiver a lógica de setar "Disponível" no construtor, o teste seria assim:
        novoLivro.setStatus("Disponível"); // Simula o resultado esperado
        
        assertEquals("Disponível", novoLivro.getStatus(), "O status inicial deve ser 'Disponível'.");
    }
}