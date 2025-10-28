package com.tiago.BiblioTechWeb.controller;

import com.tiago.BiblioTechWeb.model.Usuario;
import com.tiago.BiblioTechWeb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Para adicionar o objeto Usuario ao formulário
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Para lidar com o POST do formulário
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ----------------------------------------------------------------------
    // 1. CORREÇÃO DO LOOP: HOME ( / )
    // ----------------------------------------------------------------------
    @GetMapping("/")
    public String home() {
        // Redirecionar para a página de login se não houver uma página inicial pública
        // Se o usuário já estiver logado, o Spring Security o enviará para /livros.
        return "redirect:/login"; 
    }

    // ----------------------------------------------------------------------
    // 2. LOGIN
    // ----------------------------------------------------------------------
    @GetMapping("/login")
    public String exibirLogin() {
        return "login"; // Exibe o template login.html
    }

    @GetMapping("/principal")
    public String principal() {
        return "principal"; // Dashboard após login (Protegido pelo Spring Security)
    }

    // ----------------------------------------------------------------------
    // 3. REGISTRO (Cadastro de Novo Usuário)
    // ----------------------------------------------------------------------
    
    // Rota GET: Exibe o formulário de cadastro
    @GetMapping("/usuarios/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro"; // Assumindo que você tem um template chamado 'cadastro.html'
    }

    // Rota POST: Processa o envio do formulário de cadastro
    @PostMapping("/usuarios/novo")
    public String cadastrarUsuario(Usuario usuario, RedirectAttributes attributes) {
        try {
            // O UsuarioService DEVE Criptografar a Senha aqui antes de salvar no DB
            usuarioService.salvar(usuario); 
            attributes.addFlashAttribute("mensagem", "Cadastro realizado com sucesso! Faça login.");
            return "redirect:/login";
        } catch (Exception e) {
            // Se o e-mail já existir, ou outro erro ocorrer
            attributes.addFlashAttribute("error", "Erro ao cadastrar: " + e.getMessage());
            // Retorna ao formulário para corrigir (usando PRG Pattern)
            return "redirect:/usuarios/novo"; 
        }
    }
    
    // ----------------------------------------------------------------------
    // 4. TESTE TEMPORÁRIO
    // ----------------------------------------------------------------------

    @GetMapping("/teste/criar-usuario")
    public String criarUsuarioDeTeste(RedirectAttributes attributes) {
        try {
            Usuario usuarioAdmin = new Usuario();
            usuarioAdmin.setNome("Admin Teste");
            usuarioAdmin.setEmail("admin@bibliotech.com");
            
            // ATENÇÃO: A SENHA DEVE SER CRIPTOGRAFADA NO SERVICE.
            // Se o seu service não criptografa, o login falhará.
            usuarioAdmin.setSenha("12345"); 
            
            usuarioService.salvar(usuarioAdmin);
            
            attributes.addFlashAttribute("mensagem", "Usuário de teste 'admin@bibliotech.com' (Senha: 12345) criado com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Erro ao criar usuário de teste: " + e.getMessage());
        }
        
        // Redireciona para login após a tentativa de criação
        return "redirect:/login"; 
    }

}