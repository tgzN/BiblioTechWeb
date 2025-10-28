package com.tiago.BiblioTechWeb; // Ajuste o pacote conforme o local real da sua classe

import org.junit.jupiter.api.Test; // Usaremos o JUnit 5 (jupiter)
import static org.junit.jupiter.api.Assertions.*;

// Supondo que você tenha uma classe estática ou um método que calcula a multa
public class CalculoMultaTest {

    // Simulação: R$ 2,00 por dia
    private static final double VALOR_POR_DIA = 2.0; 

    @Test
    void testCalcularMulta_ZeroDias() {
        // Teste: Atraso de 0 dias deve resultar em 0 multa
        double multa = calcularMulta(0);
        assertEquals(0.0, multa, 0.001, "A multa para 0 dias deve ser zero.");
    }

    @Test
    void testCalcularMulta_CincoDias() {
        // Teste: Atraso de 5 dias (5 * R$ 2,00 = R$ 10,00)
        double multa = calcularMulta(5);
        assertEquals(10.0, multa, 0.001, "A multa para 5 dias deve ser R$ 10,00.");
    }

    @Test
    void testCalcularMulta_UmDia() {
        // Teste: Atraso de 1 dia
        double multa = calcularMulta(1);
        assertEquals(2.0, multa, 0.001, "A multa para 1 dia deve ser R$ 2,00.");
    }

    @Test
    void testCalcularMulta_DiasNegativos() {
        // Teste: Dias negativos (devolução adiantada ou no prazo) deve ser zero
        double multa = calcularMulta(-3);
        assertEquals(0.0, multa, 0.001, "A multa para dias negativos deve ser zero.");
    }
    
    // --- Simulação do Método de Cálculo ---
    
    // IMPORTANTE: Adapte este método para chamar sua classe real de cálculo.
    // Se o seu método estiver em uma classe chamada 'EmprestimoService', adapte a chamada.
    private double calcularMulta(int diasAtraso) {
        if (diasAtraso <= 0) {
            return 0.0;
        }
        return diasAtraso * VALOR_POR_DIA; 
    }
}