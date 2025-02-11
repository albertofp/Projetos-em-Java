import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CalculoIMCTest {
    private final double peso;
    private final double altura;
    private final double expectedIMC;
    private final String expectedClassificacao;

    public CalculoIMCTest(double peso, double altura, double expectedIMC, String expectedClassificacao) {
        this.peso = peso;
        this.altura = altura;
        this.expectedIMC = expectedIMC;
        this.expectedClassificacao = expectedClassificacao;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0.0, 1.75, 0.0, "Magreza grave"},
                {500.0, 1.75, 163.27, "Obesidade Grau III"},
                {70.0, 0.5, 280.0, "Obesidade Grau III"},
                {70.0, 3.5, 5.71, "Magreza grave"},
                {70.0, 1.75, 22.86, "Saudável"},
                {70.0, 0.0, Double.POSITIVE_INFINITY, "Obesidade Grau III"}
        });
    }

    @Test
    public void testCalcularIMC() {
        double imc = CalculoIMC.calcularPeso(peso, altura);
        assertEquals(expectedIMC, imc, 0.01);
    }

    @Test
    public void testClassificarIMC() {
        String classificacao = CalculoIMC.classificarIMC(expectedIMC);
        assertEquals(expectedClassificacao, classificacao);
    }
}