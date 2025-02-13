import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProdutoPerecivelTest {
    
    private ProdutoPerecivel produtoValido;
    private ProdutoPerecivel produtoComDesconto;

    @BeforeEach
    void setUp() {
        // Produto com validade maior que 7 dias (sem desconto)
        produtoValido = new ProdutoPerecivel("Leite", 10.0, 0.5, LocalDate.now().plusDays(10));

        // Produto com validade menor ou igual a 7 dias (com desconto)
        produtoComDesconto = new ProdutoPerecivel("Queijo", 20.0, 0.5, LocalDate.now().plusDays(5));
    }

    @Test
    void criaProdutoComValidadeValida() {
        assertDoesNotThrow(() -> new ProdutoPerecivel("Iogurte", 15.0, 0.3, LocalDate.now().plusDays(20)));
    }

    @Test
    void naoCriaProdutoComValidadeExpirada() {
        assertThrows(IllegalArgumentException.class, 
            () -> new ProdutoPerecivel("Manteiga", 8.0, 0.4, LocalDate.now().minusDays(1)));
    }

    @Test
    void calculaValorDeVendaSemDesconto() {
        double esperado = 10.0 * (1 + 0.5); // Preço de custo * (1 + margem de lucro)
        assertEquals(esperado, produtoValido.valorDeVenda(), 0.01);
    }

    @Test
    void calculaValorDeVendaComDesconto() {
        double precoBase = 20.0 * (1 + 0.5); // Preço de custo * (1 + margem de lucro)
        double esperado = precoBase * (1 - 0.25); // Aplicando desconto de 25%
        assertEquals(esperado, produtoComDesconto.valorDeVenda(), 0.01);
    }

    @Test
    void toStringExibeDataCorretamente() {
        String resultado = produtoValido.toString();
        assertTrue(resultado.contains("Leite"));
        assertTrue(resultado.contains(LocalDate.now().plusDays(10).format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }
}
