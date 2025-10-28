/**
 * =====================================
 * FUNÇÃO PRINCIPAL: FILTRAGEM DE TABELA
 * =====================================
 * Filtra as linhas de uma tabela baseada no valor de um campo de busca,
 * procurando o texto em todas as colunas de dados (td).
 * * @param {string} inputId - ID do campo de texto (input) que contém o filtro.
 * @param {string} containerId - ID do container (geralmente uma div) que envolve a tabela.
 */
function filtrarTabela(inputId, containerId) {
    // 1. Obtém o input e o valor de filtro
    const inputElement = document.getElementById(inputId);
    if (!inputElement) return; // Se o input não existe, não faz nada
    
    const filtro = inputElement.value.toUpperCase();
    
    // 2. Obtém a tabela (busca o elemento 'table' dentro da 'div')
    const container = document.getElementById(containerId);
    const tabela = container ? container.querySelector("table") : null;
    
    if (!tabela) return; 

    // 3. Obtém todas as linhas de dados (TRs), pulando o cabeçalho (THEAD)
    // O querySelectorAll('tbody tr') é mais robusto
    const linhas = tabela.querySelectorAll("tbody tr");

    // 4. Itera sobre as linhas
    linhas.forEach(linha => {
        let textoLinha = '';
        
        // Concatena o texto de todas as células de dados (TDs)
        // Isso é mais eficiente do que iterar sobre TDs no JS, mas manteremos sua lógica para compatibilidade:
        
        const celulas = linha.querySelectorAll("td");
        let encontrado = false;

        for (let i = 0; i < celulas.length; i++) {
            const textoCelula = celulas[i].textContent.toUpperCase();
            // Verifica se o filtro está em qualquer célula
            if (textoCelula.indexOf(filtro) > -1) {
                encontrado = true;
                break;
            }
        }
        
        // 5. Atualiza o estilo da linha
        linha.style.display = encontrado ? "" : "none";
    });
}

/**
 * =====================================
 * FUNÇÃO DE INTERFACE: FLASH MESSAGES
 * =====================================
 * Esconde as mensagens de alerta (erro ou sucesso) após alguns segundos.
 */
function configurarFlashMessages() {
    const alerts = document.querySelectorAll('.alert-error, .alert-success');
    
    alerts.forEach(alert => {
        // Define um timeout para esconder a mensagem após 5 segundos
        setTimeout(() => {
            alert.style.opacity = '0';
            // Remove o elemento após a transição de opacidade (0.5s)
            setTimeout(() => {
                alert.style.display = 'none';
                alert.remove();
            }, 500);
        }, 5000); // 5 segundos
        
        // Adiciona uma pequena transição no CSS para o efeito de fade-out
        alert.style.transition = 'opacity 0.5s ease-out';
    });
}

/**
 * =====================================
 * EVENTOS AUTOMÁTICOS (ROBUSTOS)
 * =====================================
 * Ativa todas as funções após o DOM ser carregado.
 */
document.addEventListener("DOMContentLoaded", function() {

    // 1. Configura a filtragem da tabela de Livros
    const inputLivros = document.getElementById("searchInputLivros"); // ID mais específico
    if (inputLivros) {
        inputLivros.addEventListener("keyup", function() {
            filtrarTabela("searchInputLivros", "livrosTable");
        });
    }

    // 2. Configura a filtragem da tabela de Empréstimos
    const inputEmprestimos = document.getElementById("searchInputEmprestimos"); // ID mais específico
    if (inputEmprestimos) {
        inputEmprestimos.addEventListener("keyup", function() {
            filtrarTabela("searchInputEmprestimos", "emprestimosTable");
        });
    }
    
    // 3. Ativa o fade-out das mensagens de erro/sucesso
    configurarFlashMessages();
});

// Nota: Você pode precisar ajustar os IDs dos inputs no seu HTML (ex: searchInput -> searchInputLivros)