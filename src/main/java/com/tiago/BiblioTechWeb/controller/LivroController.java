package com.tiago.BiblioTechWeb.controller;

import com.tiago.BiblioTechWeb.model.Livro;
import com.tiago.BiblioTechWeb.service.LivroService;
import jakarta.validation.Valid; // NOVO: Para ativar a validação baseada em anotações (ex: @NotBlank no Model)
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // NOVO: Para capturar erros de validação
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // NOVO: Para mensagens flash

@Controller
@RequestMapping("/livros") 
public class LivroController {

    private final LivroService livroService;

    @Autowired
    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    // Rota: /livros (Lista todos)
    @GetMapping
    public String listarLivros(Model model) {
        List<Livro> livros = livroService.listarTodos();
        model.addAttribute("livros", livros);
        // Assumindo que você ajustou o nome do template para 'livros.html'
        // Mas manteremos 'gerenciamento' caso seja seu nome final
        return "livros"; 
    }

    // Rota: /livros/novo (Exibe o formulário de cadastro)
    @GetMapping("/novo")
    public String exibirFormularioDeCadastro(Model model) {
        // Cria um objeto Livro vazio para o formulário
        model.addAttribute("livro", new Livro());
        // Retorna o template do formulário
        return "formulario-livro"; 
    }

    // Rota: /livros/salvar (Recebe os dados do formulário via POST)
    // REGRAS DE NEGÓCIO: Adiciona validação e tratamento de erros
    @PostMapping("/salvar")
    public String salvarLivro(@Valid @ModelAttribute Livro livro, 
                              BindingResult result, 
                              RedirectAttributes attributes) {
        
        // 1. VERIFICAÇÃO DE VALIDAÇÃO
        if (result.hasErrors()) {
            // Se houver erros (ex: campo vazio), retorna para o formulário 
            // O objeto 'livro' com os erros e dados preenchidos será mantido no Model
            return "formulario-livro"; 
        }
        
        // 2. REGRAS DE NEGÓCIO: Lógica de status inicial
        // Se for um novo livro (ID é nulo), garanta que o status inicial seja "Disponível"
        if (livro.getId() == null) {
            livro.setStatus("Disponível");
            attributes.addFlashAttribute("mensagem", "Livro cadastrado com sucesso!");
        } else {
            // Se for edição, o status deve ser mantido ou alterado pelo Service
            attributes.addFlashAttribute("mensagem", "Livro atualizado com sucesso!");
        }
        
        // 3. Persistência
        livroService.salvar(livro); 
        
        // 4. Redireciona para a lista
        return "redirect:/livros";
    }
    
    // Rota: /livros/editar/{id} (Exibe o formulário de edição pré-preenchido)
    @GetMapping("/editar/{id}")
    public String exibirFormularioDeEdicao(@PathVariable("id") Long id, Model model) {
        // Busca o livro
        Livro livro = livroService.buscarPorId(id);
        
        // Adiciona o livro encontrado ao Model
        model.addAttribute("livro", livro);
        
        // Retorna o mesmo template de formulário
        return "formulario-livro"; 
    }

    // Rota: /livros/excluir/{id} (Processa a exclusão)
    // REGRAS DE NEGÓCIO: Adiciona mensagem flash e verifica se o livro pode ser excluído
    @PostMapping("/excluir/{id}")
    public String excluirLivro(@PathVariable("id") Long id, RedirectAttributes attributes) {
        
        // REGRAS DE NEGÓCIO: O Service deve verificar se o livro pode ser excluído.
        // Se o livro estiver emprestado, o Service deve lançar uma exceção ou o Controller
        // deve checar antes de deletar.
        
        try {
            livroService.excluir(id);
            attributes.addFlashAttribute("mensagem", "Livro excluído com sucesso.");
        } catch (Exception e) {
            // Exemplo de tratamento de erro (ex: livro emprestado)
            attributes.addFlashAttribute("erro", "Erro ao excluir: Livro pode estar emprestado ou não existe.");
        }
        
        // Redireciona para a lista após a exclusão
        return "redirect:/livros";
    }
}