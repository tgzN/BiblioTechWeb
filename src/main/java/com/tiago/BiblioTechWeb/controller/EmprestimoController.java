package com.tiago.BiblioTechWeb.controller;

import com.tiago.BiblioTechWeb.model.Emprestimo;
import com.tiago.BiblioTechWeb.model.Livro;
import com.tiago.BiblioTechWeb.service.EmprestimoService;
import com.tiago.BiblioTechWeb.service.LivroService;
import jakarta.validation.Valid; 
import java.time.LocalDate; 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final LivroService livroService; 

    @Autowired
    public EmprestimoController(EmprestimoService emprestimoService, LivroService livroService) {
        this.emprestimoService = emprestimoService;
        this.livroService = livroService;
    }

    // Rota: /emprestimos (Lista todos)
    @GetMapping
    public String listarEmprestimos(Model model) {
        model.addAttribute("emprestimos", emprestimoService.listarTodos());
        return "emprestimos"; 
    }

    // Rota: /emprestimos/novo (Exibe o formulário de novo empréstimo)
    @GetMapping("/novo")
    public String exibirFormularioNovo(Model model) {
        model.addAttribute("emprestimo", new Emprestimo());
        
        // CORREÇÃO NECESSÁRIA NO SERVICE: O método deve ser 'listarLivrosDisponiveis()'
        List<Livro> livrosDisponiveis = livroService.listarLivrosDisponiveis(); 
        model.addAttribute("livrosDisponiveis", livrosDisponiveis); 
        
        return "formulario-emprestimo"; 
    }
    
    // Rota: /emprestimos/salvar (Processa a criação do novo empréstimo)
    @PostMapping("/salvar")
    public String salvarEmprestimo(@Valid @ModelAttribute Emprestimo emprestimo, BindingResult result, Model model) {

        if (result.hasErrors()) {
            // Recarrega a lista de livros para o formulário
            model.addAttribute("livrosDisponiveis", livroService.listarLivrosDisponiveis()); 
            return "formulario-emprestimo";
        }

        if (emprestimo.getId() == null) {
            emprestimo.setDataEmprestimo(LocalDate.now()); 
        }
        
        // CORREÇÃO NECESSÁRIA NO SERVICE: O método deve ser 'salvar(Emprestimo)'
        emprestimoService.salvar(emprestimo);
        
        return "redirect:/emprestimos";
    }
    
    // Rota: /emprestimos/devolver/{id} (Processa a devolução)
    @PostMapping("/devolver/{id}")
    public String registrarDevolucao(@PathVariable("id") Long id) {
        // CORREÇÃO NECESSÁRIA NO SERVICE: O método deve ser 'registrarDevolucao(Long id)'
        emprestimoService.registrarDevolucao(id);
        return "redirect:/emprestimos";
    }
    
    // Rota: /emprestimos/excluir/{id} (Remoção direta)
    @PostMapping("/excluir/{id}")
    public String excluirEmprestimo(@PathVariable("id") Long id) {
        // CORREÇÃO NECESSÁRIA NO SERVICE: O método deve ser 'excluir(Long id)'
        emprestimoService.excluir(id);
        return "redirect:/emprestimos";
    }
}