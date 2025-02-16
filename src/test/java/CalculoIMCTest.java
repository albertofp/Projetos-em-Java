import net.jqwik.api.*;
import static org.assertj.core.api.Assertions.*;
import net.jqwik.api.constraints.Positive;

class CalculoIMCTest {

    @Property
    void testCalcularIMC(@ForAll double peso, @ForAll @Positive double altura) {
        Assume.that(peso >= 0);
        double imc = CalculoIMC.calcularPeso(peso, altura);
        double expected = peso / (altura * altura);
        assertThat(imc).isCloseTo(expected, within(0.01));
    }

    @Property
    void testClassificarIMC(@ForAll double peso, @ForAll @Positive double altura) {
        Assume.that(peso >= 0);
        double imc = CalculoIMC.calcularPeso(peso, altura);
        String classificacao = CalculoIMC.classificarIMC(imc);
        assertThat(classificacao).isIn(
                "Magreza grave",
                "Magreza moderada",
                "Magreza leve",
                "Saudável",
                "Sobrepeso",
                "Obesidade Grau I",
                "Obesidade Grau II",
                "Obesidade Grau III"
        );
    }
}
